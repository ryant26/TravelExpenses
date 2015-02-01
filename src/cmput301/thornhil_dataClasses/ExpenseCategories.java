package cmput301.thornhil_dataClasses;

public enum ExpenseCategories {
	airFair("Air Fare"),
	groundTransport("Ground Transport"),
	vehicleRental("Vehicle Rental"),
	fuel("Fuel"),
	parking("Parking"),
	registration("Registration"),
	accommodation("Accommodation"),
	meal("meal");
	
	private String name;
	
	private ExpenseCategories(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public static ExpenseCategories fromString(String name){
		if (name != null){
			for (ExpenseCategories e : ExpenseCategories.values()){
				if (name.equalsIgnoreCase(e.name)){
					return e;
				}
			}
		}
		return null;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
}
