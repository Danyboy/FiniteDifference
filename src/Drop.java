
/**
 * Created by Dany on 22.05.14
 */
public class Drop {
    private int dropSize;
    private double[][] heat;
    private double[][] dropHeat;
    private int X;
    private int Y;
    private MyPair <Integer> location;

    public Drop(int dropSize, int x, int y){
        X = x;
        Y = y;
        this.dropSize = dropSize;
    }

    public Drop(){
        this(2, 1, 1);
    }

    void setDropHeat(int x, int y){
        dropHeat = new double[dropSize][dropSize];
        for (int i = x; i < x + dropSize; i++) {
            for (int j = y; j < y + dropSize ; j++) {
                dropHeat[i][j] = heat[i][j];
            }
        }
    }

    public void getNextStep(){
        setDropHeat(location); //how many heat go to water
        getDirection();
    }

    private void setDropHeat(MyPair<Integer> location) {
        setDropHeat(location.getX(), location.getY());
    }

    private MyPair <Integer> getDirection() { //TODO drop size must be > 2 and doesn't stay at border
        int heatXLength = heat.length;
        int heatYLength = heat[1].length;

        double [] up = new double[heatXLength];
        double [] down = new double[heatXLength];
        double[] left = new double[heatYLength];
        double[] right = new double[heatYLength];

        for (int i = 0; i < heatXLength; i++) {
            up[i] = dropHeat[i][1] - dropHeat[i][0];
            down[i] = dropHeat[i][heatXLength - 2] - dropHeat[i][heatXLength - 1];
        }

        for (int j = 0; j < heatYLength; j++) {
            left[j] = dropHeat[1][j] - dropHeat[0][j];
            right[j] = dropHeat[heatYLength - 2][j] - dropHeat[heatYLength - 1][j];
        }

        X = getDir(X, up, down);
        Y = getDir(Y, left, right);

        setLocation(new MyPair<Integer>(X, Y));

        return location;
    }

    private int getDir(int var, double [] first, double [] second){
        int border = heat.length - dropSize - 1; //TODO check
        return getAverage(first) < getAverage(second) ? (var > 0 ? var-- : 0) : (var < border ? var++ : border);
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

    public MyPair<Integer> getLocation() {
        return location;
    }

    public void setLocation(MyPair<Integer> location) {
        this.location = location;
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
