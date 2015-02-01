package cmput301.thornhil_dataClasses;

import java.util.*;

public class Expense extends DataItem {
	private Integer ammount;
	private Currency currency;
	private Integer claimId;
	private ExpenseCategories category;
	
	public Expense(){}
	
	public Expense(String name, Integer amt, Currency currency, Integer claim, Integer id, Date date, ExpenseCategories eCategories){
		super(name, date);
		setAmmount(amt);
		setCurrency(currency);
		setClaimId(claim);
		setCategory(eCategories);
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

	public ExpenseCategories getCategory() {
		return category;
	}

	public void setCategory(ExpenseCategories category) {
		this.category = category;
	}
	
}
