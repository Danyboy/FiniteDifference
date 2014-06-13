/**
 * Created by danil on 16.04.14.
 */
public class Main {

    public static void main(String args[]) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Heater heater = new Heater(100, 100);
                heater.calculate();

                ArrayViewImpl arrayView = new ArrayViewImpl(heater.getHeat());
                arrayView.nextStep();

            }
        });
    }
}
