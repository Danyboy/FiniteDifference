/**
 * Created by danil on 16.04.14.
 */
public class Main {

    public static void main(String args[]){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Heater heater = new Heater(50, 50);
                heater.calculate();

                ArrayViewImpl arrayView = new ArrayViewImpl(heater.getHeat());

                for (int i = 0; i < 100; i++) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch(InterruptedException ex) {
//                        Thread.currentThread().interrupt();
//                    }
                    arrayView.nextStep();
                }

            }
        });
    }
}
