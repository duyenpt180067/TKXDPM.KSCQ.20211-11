package common.exception;

public class NoBikeRentedException extends Exception{
    public NoBikeRentedException(){
        super("Không có xe nào đang được thuê");
    }
}
