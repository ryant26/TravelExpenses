package cmput301.thornhil_dataClasses;

import java.util.Date;

import cmput301.thornhil_helpers.Formatter;



public class Claim extends DataItem {
	private ClaimStatus status = ClaimStatus.open;
	private Date endDate;
	private String description;
	
	public Claim(String name, Date startDate, Date endDate){
		super(name,	startDate);
		this.endDate = endDate;
	}
	
	public Claim(){}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toEmailString(){
		 return "Claim: " + getName() + "\n" +
				 "Description: " + getDescription() + "\n" +
				 "Start Date: " + Formatter.formatDate(getDate()) + "\n" +
				 "End Date: " + Formatter.formatDate(getEndDate()) + "\n";
	}

}
