package cmput301.thornhil_dataClasses;

public enum ClaimStatus {
	
	submitted("Submitted"),
	returned("Returned"),
	approved("Approved"),
	open("Open");
	
	private String name;
	
	
	private ClaimStatus(String name)
	{
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public static ClaimStatus fromString(String name){
		if (name != null) {
		      for (ClaimStatus b : ClaimStatus.values()) {
		        if (name.equalsIgnoreCase(b.name)) {
		          return b;
		        }
		      }
		    }
		    return null;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	
}