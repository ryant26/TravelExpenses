package cmput301.thornhil_dataClasses;

import com.google.gson.Gson;

public class DataItem {
	private String name;
	private Integer id;
	
	public DataItem(String name, Integer id){
		setId(id);
		setName(name);
	}
	
	public Integer getId() {
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
	
	
}
