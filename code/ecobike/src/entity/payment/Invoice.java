package entity.payment;

import java.util.Hashtable;
import java.util.Map;

import entity.rental.RentInfo;

public class Invoice {

	private String content;

	private int amount;

	private RentInfo rentInfo;

	public Invoice(String content, int amount, RentInfo rentInfo) {
		super();
		this.content = content;
		this.amount = amount;
		this.rentInfo = rentInfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public RentInfo getRentInfo() {
		return rentInfo;
	}

	public void setRentInfo(RentInfo rentInfo) {
		this.rentInfo = rentInfo;
	}
	
	public Map<String, String> getReturnInfo() {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("TYPE", rentInfo.getBike().getName());
		result.put("AMOUNT", this.getAmount()+"VND");
		result.put("TIME", timeIntToString(rentInfo.getRentedPeriod()) );
		result.put("MULTI","x" + rentInfo.getBikeMulti());
		return result;
	}
	
	public  String timeIntToString(int time) {
		int hours = time / 60;
		int minutes = time%60;
		return hours + "h"+minutes+"m";
	}

	
}
