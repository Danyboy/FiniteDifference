
/**
 * Created by Dany on 22.05.14
 */
public class Drop {
    private int dropSize;
    private double[][] heat;
    private double[][] dropHeat;
    private int X;
    private int Y;

    public Drop(int dropSize, int x, int y){
        X = x;
        Y = y;
        this.dropSize = dropSize;
    }

    public Drop(){
        this(3, 1, 1);
    }

    void setDropHeat(int x, int y){
        dropHeat = new double[dropSize][dropSize];
        for (int i = 0; i < dropSize; i++) {
            for (int j = 0; j < dropSize ; j++) {
                if (i + x > heat.length - 1 || j + x > heat[1].length - 1 ){
                    dropHeat[i][j] = Double.MAX_VALUE; //TODO very hot border, maybe change
                } else {
                    dropHeat[i][j] = heat[x + i][y + j];
                }
            }
        }
    }

    double[][] pressure;
    private double nextPressureIteration() {
        pressure = getInitialPressure();
        double[][] newPressure = new double[X][Y];
        double diff = 0;
        double deltaTime = 0.01; //dT
        double deltaR = 0.1; //dX
        double deltaY = 0.1; //dX
        double height = getHeightDrop();

        double alpha = 0.1;  // ( m * lambda ) / ( r * p )
                            // вязкость * тепл проводность / ( уд тепл парообраз * плотность пара )
        double delta = deltaTime * alpha / ( deltaR * height * height * height * height); // TODO check deltaR
        double t0 = 100;

        for (int i = 1; i < dropHeat.length; i++) {
            for (int j = 1; j < dropHeat[1].length; j++) {
                newPressure[i][j] = pressure[i][j] - delta * dropHeat[i][j]; //dropHeat[i][j] - t0
                diff += newPressure[i][j] - pressure[i][j];
            }
        }

        pressure = newPressure;
        return diff;
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
        return 0;
    }

    public void nextStep(){
        setDropHeat(X, Y); //how many heat go to water
        getDirection();
    }

    private void getDirection() {
        int[] dir = getBorder(dropHeat);
        X = dir[0];
        Y = dir[1];
    }
    private int[] getBorder(double[][] array) {

        if (dropSize < 2 || array.length < 2 || X == 0 || Y == 0 ||
                X == heat.length - 1 || Y == heat[1].length - 1){
            System.out.println("drop size must be > 2 and doesn't stay at border " + dropSize + " " + X );
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

        //TODO return 4 arrays and rewrite get dir

        int upDown = getDir(X, up, down);
        int leftRight = getDir(Y, left, right);

        return new int[] {upDown, leftRight};
    }

    private int getDir(int var, double [] first, double [] second){
        int border = heat.length - dropSize - 1; //TODO check
        return getAverage(first) < getAverage(second) ? (var > 0 ? --var : 0) : (var < border ? ++var : border);
    }

    private double getAverage(double [] array){
        double v = 0;
        for (int i = 0; i < array.length; i++) {
            v += array[i];
        }

        return v / array.length;
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

class MyPair <T>{
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
