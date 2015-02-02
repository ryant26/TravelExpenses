//	Copyright 2015 Ryan Thornhill
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package cmput301.thornhil_dataClasses;

import java.util.*;

public class Expense extends DataItem {
	private Float ammount;
	private Currency currency;
	private Integer claimId;
	private ExpenseCategories category;
	
	public Expense(){}
	
	public Expense(String name, Float amt, Currency currency, Integer claim, Integer id, Date date, ExpenseCategories eCategories){
		super(name, date);
		setAmmount(amt);
		setCurrency(currency);
		setClaimId(claim);
		setCategory(eCategories);
	}
	
	public Float getAmmount() {
		return ammount;
	}
	public void setAmmount(Float ammount) {
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
	
	public String toEmailString(){
		return "Expense\n" + 
				"Description: " + getName() +"\n" +
				"Cost: " + getAmmount().toString() + getCurrency().toString() + "\n";
	}
	
}
