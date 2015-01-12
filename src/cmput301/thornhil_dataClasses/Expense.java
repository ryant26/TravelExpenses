package cmput301.thornhil_dataClasses;

import java.util.*;

public class Expense extends DataItem {
	private Integer ammount;
	private Currency currency;
	private Integer tripId;
	
	public Expense(String name, Integer amt, Currency currency, Integer trip, Integer id){
		super(name, id);
		setAmmount(amt);
		setCurrency(currency);
		setTripId(trip);
	}
	
	public Integer getAmmount() {
		return ammount;
	}
	public void setAmmount(Integer ammount) {
		this.ammount = ammount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer trip) {
		this.tripId = trip;
	}
	
}
