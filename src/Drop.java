
/**
 * Created by Dany on 22.05.14
 */
public class Drop implements FiniteDifference {
    private int dropSize;
    private double[][] heat;
    private double[][] dropHeat;
    private int X;
    private int Y;
    double speedX;
    double speedY;

    int steps = 20;
    public int [][] dropPath = new int[steps][2];
    private double [][] forceHistory = new double[steps][2];
    int currentStep = 0;

    public Drop(int dropSize, int x, int y) {
        X = x;
        Y = y;
        this.dropSize = dropSize;
    }

    public Drop() {
        this(3, 1, 1);
    }

    double[][] pressure;

    @Override
    public void calculate() {

        int pressureSteps = 10;
        for (int i = 0; i < pressureSteps; i++) {
            nextPressureIteration();
        }
        getForce();
    }

    private double nextPressureIteration() {
        pressure = getInitialPressure();
        double[][] newPressure = new double[pressure.length][pressure[1].length];
        double diff = 0;
        double deltaTime = 0.01; //dT
        double deltaR = 0.1; //dX
        double deltaY = 0.1; //dX
        double height = getHeightDrop();

        double alpha = 0.1; // * Math.pow(height, 4)  // ( m * lambda ) / ( r * p * h^4 )
        // вязкость * тепл проводность / ( уд тепл парообраз * плотность пара * высота капли )
        double delta = deltaTime / (deltaR * deltaR); // TODO check deltaR
        double t0 = 100;

        for (int i = 1; i < dropHeat.length - 1; i++) {
            for (int j = 1; j < dropHeat[1].length - 1; j++) {
                newPressure[i][j] = pressure[i][j] + deltaTime * alpha * dropHeat[i][j] - delta * (
                                    pressure[i + 1][j] + pressure[i - 1][j] +
                                    pressure[i][j + 1] + pressure[i][j - 1]
                                    - 4 * pressure[i][j])
                ; //dropHeat[i][j] - t0
                diff += newPressure[i][j] - pressure[i][j];
            }
        }

        pressure = newPressure;
        return diff;
    }

    private double getHeightDrop() {
        double radius = 0.001; //dropSize; //Drop newSize in m
        double lambda = 1; //Теплопроводность
        double boilingPoint = 100;
        double meanDropTemperature = 100; //getMeanTemperature() - boilingPoint;
        double viscosity = 0.001; //Вязкость
        double density = 1; //
        double latentHeatOfVaporization = 100000;
        double g = 10;
        double alpha = 1;
        return
                Math.pow(

                9 * radius * lambda * meanDropTemperature * viscosity /
                ( density * density * latentHeatOfVaporization * g)

                , 1.0 / 4)
                ;
    }

    double allForceX = 0;
    double allForceY = 0;

    private void getForce(){
        double force; //
        int directionX; //
        int directionY; //
        double[][] border = getBorder(pressure);

        speedX = getSummarize(border[0]) - getSummarize(border[1]);
        speedY = getSummarize(border[2]) - getSummarize(border[3]);

        double coefficient = speedX / speedY;

        System.out.println("SpeedX " + speedX + " SpeedY " + speedY + " K " + coefficient);

        // h * p * V ^ 2 * n * R
        double p = 0.1;
        force = Math.pow(getHeightDrop(), 2) * p;

        double forceX = force * speedX * speedX;
        double forceY = force * speedY * speedY;
        System.out.println("ForceX " + forceX + " ForceY " + forceY + " K " + coefficient);

        forceX = normalise(forceX);
        forceY = normalise(forceY);
        System.out.println("ForceX " + forceX + " ForceY " + forceY + " K " + coefficient);

        System.out.println("Force " + force + " MeanTemp " + getMeanTemperature() + " h " + getHeightDrop());

        allForceX =+ Math.abs(forceX);
        allForceY =+ Math.abs(forceY);

        int stepLengthX = getStepLength(allForceX);
        int stepLengthY = getStepLength(allForceY);

        allForceX -= stepLengthX;
        allForceY -= stepLengthY;
        System.out.println("StepX " + stepLengthX + " StepY " + stepLengthY);

        X = checkBorder(X, getDir(border[0], border[1]) * stepLengthX);
        Y = checkBorder(Y, getDir(border[2], border[3]) * stepLengthY);
    }

    private double normalise(double force){
        if (force < 1){
            while (force < 1)
            force *= 10;
        } else if (force > 10){
            while (force > 10)
            force /= 10;
        }
        if (1 < force && force < 10){
            return force;
        } else {
            normalise(force);
        }
        return force;
    }

    private int getStepLength(double allForce){
        int stepLength = 0;
        double stepCost = 1;
        while (allForce > stepCost){
            stepLength++;
            allForce =- stepCost;
        }
        return stepLength > 4 ? 4 : stepLength; //TODO remove this shit 4
    }

    private void getSpeed(double force){
    }

    private int getDir(double[] first, double[] second) {
        double myFirst = getAverage(first);
        double mySecond = getAverage(second);
        double d = myFirst / mySecond;
        double myCoefficient = 0.05; //TODO remove this shit
        if ( d < myCoefficient || d > myCoefficient * 100){
            return 0;
        }
        return myFirst < mySecond ? -1 : 1;
    }

    private int checkBorder(int position, int path){
        int border = heat.length - dropSize - 1; //TODO check
        int currentPosition = position + path;
        if ( currentPosition > 0 && currentPosition < border){
            return currentPosition;
        }
        else if (currentPosition < 0){
            return 0;
        }
        else if (currentPosition > border){
            return border;
        }
        return currentPosition;
//                (var > 0 ? --var : 0) : (var < border ? ++var : border);
    }

    private void getDirection() {
        double[][] border = getBorder(dropHeat);

        X = X + getDir(border[0], border[1]);
        Y = Y + getDir(border[2], border[3]);
    }

    /**
     * Returns four border elements of array
     *
     * @param  array array for extract border
     * @return       array of array: up, down, left, right
     */
    private double[][] getBorder(double[][] array) {

        if (dropSize < 2 || array.length < 2 || X == 0 || Y == 0 ||
                X == heat.length - 1 || Y == heat[1].length - 1) {
            System.out.println("drop size must be > 2 and doesn't stay at border " + dropSize + " " + X);
        }

        int dropHeatXLength = array.length;
        int dropHeatYLength = array[1].length;

        double[] up = new double[dropHeatXLength];
        double[] down = new double[dropHeatXLength];
        double[] left = new double[dropHeatYLength];
        double[] right = new double[dropHeatYLength];

        for (int i = 0; i < dropHeatXLength; i++) {
            up[i] = array[i][1] - array[i][0];
            down[i] = array[i][dropHeatXLength - 2] - array[i][dropHeatXLength - 1];
        }

        for (int j = 0; j < dropHeatYLength; j++) {
            left[j] = array[1][j] - array[0][j];
            right[j] = array[dropHeatYLength - 2][j] - array[dropHeatYLength - 1][j];
        }

        return new double[][]{up, down, left, right};
    }

    private double getAverage(double[][] array) {
        double result = 0;
        for (double[] doubles : array) {
            result += getAverage(doubles);
        }
        return result / array.length;
    }

    private double getAverage(double[] array) {
        double v = 0;
        for (int i = 0; i < array.length; i++) {
            v += array[i];
        }

        return v / array.length;
    }

    private double getSummarize(double[] array) {
        double result = 0;
        for (double doubles : array) {
            result += doubles;
        }
        return result;
    }

    void setDropHeat(int x, int y) {
        dropHeat = new double[dropSize][dropSize];
        for (int i = 0; i < dropSize; i++) {
            for (int j = 0; j < dropSize; j++) {
                if (i + x > heat.length - 1 || j + x > heat[1].length - 1) {
                    dropHeat[i][j] = Double.MAX_VALUE; //TODO very hot border, maybe change
                } else {
                    dropHeat[i][j] = heat[x + i][y + j];
                }
            }
        }
    }

    private double[][] getInitialPressure() {
        pressure = new double[dropSize][dropSize];
        double atmospherePressure = 600;

        for (int i = 0; i < pressure.length; i++) {
            double[] doubles = pressure[i];
            for (int i1 = 0; i1 < doubles.length; i1++) {
                doubles[i1] = atmospherePressure;
            }
        }

        return pressure;
    }

    private double getMeanTemperature() {
        return getAverage(dropHeat);
    }

    public void nextSteps(){
        for (int i = 0; i < steps; i++) {
            nextStep();
        }
    }

    public void nextStep() {
        setDropHeat(X, Y); //how many heat go to water
//        getDirection(); //my primitive calc
        calculate();
        saveStep();
    }

    private void saveStep(){
        dropPath[currentStep] = new int[]{X, Y};
        forceHistory[currentStep] = new double[]{speedX, speedY};
        ++currentStep;
    }

    public void setHeat(double[][] heat) {
        this.heat = heat;
    }

    public void setDropSize(int dropSize) {
        this.dropSize = dropSize;
    }

    public int getDropSize() {
        return dropSize;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

}

class MyPair<T> {
    T X, Y;

    public MyPair(T x, T y) {
        X = x;
        Y = y;
    }

    public void setX(T x) {
        X = x;
    }

    public void setY(T y) {
        Y = y;
    }

    public T getX() {
        return X;
    }

    public T getY() {
        return Y;
    }
}
