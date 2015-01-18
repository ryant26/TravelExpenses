package cmput301.thornhil_dataClasses;

import java.util.*;

public class Expense extends DataItem {
	private Integer ammount;
	private Currency currency;
	private Integer claimId;
	
	public Expense(String name, Integer amt, Currency currency, Integer claim, Integer id){
		super(name, id);
		setAmmount(amt);
		setCurrency(currency);
		setClaimId(claim);
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
	public Integer getClaimId() {
		return claimId;
	}
	public void setClaimId(Integer claim) {
		this.claimId = claim;
	}
	
}
