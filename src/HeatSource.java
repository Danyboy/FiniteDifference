/**
* Created by Dany on 22.04.14.
*/
class HeatSource {
    private FiniteDifference finiteDifference;
    int x;
    int y;
    double energy;

    HeatSource(FiniteDifference finiteDifference, int x, int y){
        this.finiteDifference = finiteDifference;
        this.x = x;
        this.y = y;
//            this.energy = e;
    }

    HeatSource(FiniteDifference finiteDifference, int x, int y, double e){
        this.finiteDifference = finiteDifference;
        this.x = x;
        this.y = y;
        this.energy = e;
    }

    HeatSource(FiniteDifference finiteDifference){
        this.finiteDifference = finiteDifference;
        this.x = (int) finiteDifference.getRandom();
        this.y = (int) finiteDifference.getRandom();
        this.energy = 1;
    }
}
