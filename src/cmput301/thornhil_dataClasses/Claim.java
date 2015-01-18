package cmput301.thornhil_dataClasses;


public class Claim extends DataItem{
	private ClaimStatus status = ClaimStatus.open;
	
	public Claim(String name, Integer id){
		super(name, id);
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}
}
