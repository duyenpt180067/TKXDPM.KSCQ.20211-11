package entity.rental;

import entity.dockbike.Bike;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NormalRentInfo extends RentInfo{
    public static final String TYPE_NAME = "NORMAL";

    public NormalRentInfo(LocalDateTime startTime, Bike bike) {
        super(startTime, bike);
    }

    public NormalRentInfo(int id, LocalDateTime startTime, int rentedPeriod, int depositAmount, Bike bike, boolean isComplete, int returnDockId, int returnCellId) {
        super(id, startTime, TYPE_NAME, rentedPeriod, depositAmount, bike, isComplete, returnDockId, returnCellId);
    }

    public int getCurrentAmount(int minutes) {
        if(minutes < 10) return 0;
        long amount = 10000;
        minutes -= 30;
        while (minutes > 0 ) {
            minutes -= 15;
            amount += 3000;
        }
        return  (int) (amount*getBikeMulti());
    }

    public int getCurrentTime(LocalDateTime nowDateTime) {
        LocalDateTime startTime = this.startTime;
        long tmpminutes = ChronoUnit.MINUTES.between(startTime, nowDateTime);
        int minutes =(int) (this.rentedPeriod + tmpminutes);
        return minutes;
    }

    public void updateReturnInfo(LocalDateTime endTime, int returnDockId , int returnCellId) {
        this.endTime = endTime;
        this.returnDockId = returnDockId;
        this.returnCellId = returnCellId;
        this.rentedPeriod = getCurrentTime(endTime);
        this.rentAmount = getCurrentAmount(this.rentedPeriod);
    }
}
