package controller;

import common.exception.NoBikeRentedException;
import entity.rental.RentInfo;

import java.util.List;

public class BaseController {

    protected RentInfo currentRentInfo = null;

    public RentInfo getCurrentRentInfo() throws NoBikeRentedException {
        if(currentRentInfo == null){
            List<RentInfo> rentInfos = RentInfo.getNotCompleteRentInfo();
            if(rentInfos.size() == 0){
                throw new NoBikeRentedException();
            }
            else currentRentInfo = rentInfos.get(0);
        }
        return currentRentInfo;
    }
}