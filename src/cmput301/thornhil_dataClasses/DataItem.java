package cmput301.thornhil_dataClasses;

import java.util.Date;

import com.google.gson.Gson;

public class DataItem {
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
	
	
}
