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

import com.google.gson.Gson;

public class DataItem implements Comparable<DataItem>{
	private String name;
	private Integer id;
	private Date date;
	
	public DataItem(String name, Date date){
		setName(name);
		setDate(date);
		id = this.hashCode();
	}
	
	public DataItem(){};
	
	public Integer getId() {
		if (id == null){
			id = hashCode();
		}
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String serialize(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public int compareTo(DataItem another) {
		return getDate().compareTo(another.getDate());
	}
	
}
