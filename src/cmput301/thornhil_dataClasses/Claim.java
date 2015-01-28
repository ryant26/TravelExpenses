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
	
	public static ClaimStatus getClaimStatusFromString(String in){
		if (in.equalsIgnoreCase("submitted")){
			return ClaimStatus.submitted;
		} else if (in.equalsIgnoreCase("accepted")) {
			return ClaimStatus.accepted;
		} else if (in.equalsIgnoreCase("approved")) {
			return ClaimStatus.approved;
		} else if (in.equalsIgnoreCase("open")) {
			return ClaimStatus.open;
		}else {
			throw new RuntimeException("Cannot convert string to claim status!");
		}
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
