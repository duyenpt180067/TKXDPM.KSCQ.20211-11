package entity.rental;

import entity.dockbike.Bike;

import java.time.LocalDateTime;

public class OneDayRentInfo extends RentInfo{
    public static final String TYPE_NAME = "ONE_DAY";

    public OneDayRentInfo(LocalDateTime startTime, Bike bike) {
        super(startTime, bike);
    }


    public OneDayRentInfo(int id, LocalDateTime startTime, int rentedPeriod, int depositAmount, Bike bike, boolean isComplete, int returnDockId, int returnCellId) {
        super(id, startTime, TYPE_NAME, rentedPeriod, depositAmount, bike, isComplete, returnDockId, returnCellId);
    }

    @Override
    public int getCurrentTime(LocalDateTime nowDateTime) {
        return 0;
    }

    @Override
    public void updateReturnInfo(LocalDateTime nowDateTime, int returnDockId, int returnCellId) {

    }

}
