/**
 * Created by danil on 16.04.14.
 */
public class Main {

    public static void main(String args[]){
            Heater finiteDifference = new Heater(50, 50);

            finiteDifference.calculate();
            ArrayViewImpl arrayView = new ArrayViewImpl(finiteDifference.getHeat());
    }
}
