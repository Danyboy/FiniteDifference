
/**
 * Created by Dany on 22.05.14
 */
public class Drop implements FiniteDifference {
    private int dropSize;
    private double[][] heat;
    private double[][] dropHeat;
    private int X;
    private int Y;

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

    private void getForce(){
        double force; //
        int directionX; //
        int directionY; //
        double[][] border = getBorder(pressure);

        // h * p * V ^ 2 * n * R
//        double p = 0.1;
//        force = getHeightDrop() * p;

        X = getDir(X, border[0], border[1]);
        Y = getDir(Y, border[2], border[3]);
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

    private double getHeightDrop() {
        double radius = 0.001; //dropSize; //Drop size in m
        double lambda = 1; //Теплопроводность
        double boilingPoint = 100;
        double meanDropTemperature = 100; //getMeanTemperature() - boilingPoint;
        double viscosity = 0.001; //Вязкость
        double density = 1; //
        double latentHeatOfVaporization = 100000;
        double g = 10;
        double alpha = 1;
        return 9 * radius * lambda * meanDropTemperature * viscosity /
                ( density * density * latentHeatOfVaporization * g);
    }

    private double getMeanTemperature() {
        return getAverage(dropHeat);
    }

    int steps = 50;
    public int [][] dropPath = new int[steps][2];
    int currentStep = 0;

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
        ++currentStep;
    }

    private void getDirection() {
        double[][] border = getBorder(dropHeat);

        X = getDir(X, border[0], border[1]);
        Y = getDir(Y, border[2], border[3]);
    }

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

    private int getDir(int var, double[] first, double[] second) {
        int border = heat.length - dropSize - 1; //TODO check
        return getAverage(first) < getAverage(second) ? (var > 0 ? --var : 0) : (var < border ? ++var : border);
    }

    private double getAverage(double[][] array) {
        double result = 0;
        for (double[] doubles : array) {
            result += getAverage(doubles);
        }
        return result;
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
