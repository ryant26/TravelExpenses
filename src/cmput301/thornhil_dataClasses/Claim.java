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
