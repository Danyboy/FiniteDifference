/**
 * Created by danil on 16.04.14.
 */
public class Main {

    public static void main(String args[]){
            FiniteDifference finiteDifference = new FiniteDifference(5, 5);
            finiteDifference.calculate();
            ArrayViewImpl arrayView = new ArrayViewImpl(finiteDifference.getHeat());
    }
}
