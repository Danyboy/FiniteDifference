
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

    public void nextStep(){
        setDropHeat(X, Y); //how many heat go to water
        getDirection();
    }

//    private void setDropHeat(int x, int y) {
//        setDropHeat(x, y);
//    }

    private void getDirection() { //TODO drop size must be > 2 and doesn't stay at border

        if (dropSize < 2 || dropHeat.length < 2 || X == 0 || Y == 0 ||
                X == heat.length - 1 || Y == heat[1].length - 1){
            System.out.println("drop size must be > 2 and doesn't stay at border " + dropSize + " " + X );
        }

        int dropHeatXLength = dropHeat.length;
        int dropHeatYLength = dropHeat[1].length;

        double [] up = new double[dropHeatXLength];
        double [] down = new double[dropHeatXLength];
        double[] left = new double[dropHeatYLength];
        double[] right = new double[dropHeatYLength];

        for (int i = 0; i < dropHeatXLength; i++) {
            up[i] = dropHeat[i][1] - dropHeat[i][0];
            down[i] = dropHeat[i][dropHeatXLength - 2] - dropHeat[i][dropHeatXLength - 1];
        }

        for (int j = 0; j < dropHeatYLength; j++) {
            left[j] = dropHeat[1][j] - dropHeat[0][j];
            right[j] = dropHeat[dropHeatYLength - 2][j] - dropHeat[dropHeatYLength - 1][j];
        }

        X = getDir(X, up, down);
        Y = getDir(Y, left, right);
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
