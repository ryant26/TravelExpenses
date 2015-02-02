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
