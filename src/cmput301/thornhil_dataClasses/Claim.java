package cmput301.thornhil_dataClasses;

import java.util.Date;


public class Claim extends DataItem{
	private ClaimStatus status = ClaimStatus.open;
	private Date endDate;
	
	public Claim(String name, Date startDate, Date endDate){
		super(name,	startDate);
		this.endDate = endDate;
	}
	
	public Claim(){}

	public ClaimStatus getStatus() {
		return status;
	}
	
	public String getStatusString(){
		switch (status) {
		case submitted:
			return "submitted";
		case accepted:
			return "Accepted";
		case approved:
			return "Approved";
		case open:
			return "open";
		default:
			return "";
		}
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
}
