import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Created by Dany on 08.03.14.
 */
public class Heater implements FiniteDifference {

    //    http://en.wikipedia.org/wiki/Finite_difference_method
    // some change
    private static int X;
    private static int Y;

    private Logger log = Logger.getLogger(Heater.class.getName());

    private double[][] heat; //распределение тепла со временем
    private double[][] heatCofficient; //распределение тепла от источника, постоянное
    int radius;

    public LinkedList<HeatSource> heatSources;

    private double initialHeat; //start heat

    public Heater(int x, int y) {
        X = x;
        Y = y;
        radius = X / 2; //radius of heater
        heat = new double[X][Y];
        heatCofficient = new double[X][Y];
        initialHeat = 1;
        heatSources = new LinkedList<HeatSource>();
    }

    public void calculate() {
//        oneSource();
        fourSource();

        calculateHeatCofficient();
        setInitialHeat();

        for (int i = 0; i < 80; i++) {
//            System.out.println();
//            System.out.println("New iteration " + i);
            nextThermalIteration();
        }
    }

    private void fourSource(){
        radius = X / 4;
        addHeatSource(radius, radius);
        addHeatSource(X - radius, radius);
        addHeatSource(X - radius, Y - radius);
        addHeatSource(radius, Y - radius);
    }

    private void oneSource(){
        addHeatSource(X / 2, Y / 2);
    }

    private void copyBorder(double[][] oldArray, double[][] newArray) {
        for (int i = 0; i < X - 1; i++) {
            newArray[i][0] = oldArray[i][1];
            newArray[i][Y - 1] = oldArray[i][Y - 2];
        }
        for (int j = 0; j < Y - 1; j++) {
            newArray[0][j] = oldArray[1][j];
            newArray[X - 1][j] = oldArray[X - 2][j];
        }
    }

    private void setInitialHeat() {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                heat[i][j] = initialHeat;
            }
        }
    }

    private void addHeatSource(int x, int y) {
        heatSources.add(new HeatSource(x, y));
    }

    private void addHeatSource() {
        heatSources.add(new HeatSource());
    }

    private void calculateHeatCofficient() {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                heatCofficient[i][j] = calculateHeatCofficient(i, j);
//                myLog("heatCofficient[i][j] " + heatCofficient[i][j]);
            }
        }
    }

    private double calculateHeatCofficient(double x, double y) {
        double defaultHeat = 0;
        if (heatSources.isEmpty()) {
            return defaultHeat; //default heat
        }
        double min = radius;
//        double min = 0;
        for (HeatSource heatSource : heatSources) {
            double current = distance(x, y, heatSource);
            if (current < min) {
                min = current;
            }
        }
        return defaultHeat + (radius - min);
//                1 + p0 * (1 - min/l);
    }

    private double nextThermalIteration() {
        double[][] newHeat = new double[X][Y];
        double diff = 0;
        double deltaTime = 0.01; //dT
        double deltaX = 0.1; //dX
        double deltaY = 0.1; //dX
        double alpha = 0.1;  // \ / (c * r) - lambd / heat * density
        double delta = deltaTime * alpha / (deltaX * deltaX);
        for (int i = 1; i < X - 2; i++) {
            for (int j = 1; j < Y - 2; j++) {
                newHeat[i][j] =
                        heat[i][j]
                                + delta *
                                (heat[i + 1][j] + heat[i - 1][j] +
                                 heat[i][j + 1] + heat[i][j - 1]
                                 - 4 * heat[i][j])
                                + heatCofficient[i][j]
                ;
                diff += newHeat[i][j] - heat[i][j];
            }
            myLog("diff " + diff);
        }

        copyBorder(heat, newHeat);

        heat = newHeat;
        return diff;
    }

    public void myLog(String s) {
        boolean logOn = false;
        if (logOn) {
            System.out.println(s);
        }

        boolean systemLog = false;
        if (systemLog) {
            log.info(s);
        }

    }

    public static int getRandom() {
        return (X < Y ? getRandom(0, X) : getRandom(0, Y));
    }

    private static int getRandom(int min, int max) {
        return (int) getRandom((double) min, (double) max);
    }

    private static double getRandom(double min, double max) {
        return (min + (max - min) * Math.random());
    }

    private static double distance(double x, double y, HeatSource hs) {
        return Math.sqrt((hs.y - y) * (hs.y - y) + (hs.x - x) * (hs.x - x));
    }

    public double[][] getHeat() {
        return heat;
    }
}