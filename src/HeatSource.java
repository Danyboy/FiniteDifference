/**
* Created by Dany on 22.04.14.
*/
class HeatSource {
    int x;
    int y;
    double energy;

    HeatSource(int x, int y){
        this.x = x;
        this.y = y;
//            this.energy = e;
    }

    HeatSource(int x, int y, double e){
        this.x = x;
        this.y = y;
        this.energy = e;
    }

    HeatSource(){
        this.x = (int) FiniteDifference.getRandom();
        this.y = (int) FiniteDifference.getRandom();
        this.energy = 1;
    }
}
