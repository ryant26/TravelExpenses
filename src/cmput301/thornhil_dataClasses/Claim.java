package cmput301.thornhil_dataClasses;

import java.util.Date;


public class Claim extends DataItem{
	private ClaimStatus status = ClaimStatus.open;
	
	public Claim(String name, Integer id, Date date){
		super(name, id, date);
	}

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
}
