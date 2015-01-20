package cmput301.thornhil_dataClasses;


public class Claim extends DataItem{
	private ClaimStatus status = ClaimStatus.open;
	
	public Claim(String name, Integer id){
		super(name, id);
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
