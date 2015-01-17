package cmput301.thornhil_helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_dataClasses.Expense;
import cmput301.thornhil_dataClasses.Trip;

import com.google.gson.*;

public class StorageAdapter {
	private Gson gson = new Gson();
	private StorageHelper storageHelper;
	
	public StorageAdapter(Context context){
		storageHelper = storageHelper.getInstance(context);
	}
	
	public void storeAllTrips(Iterable<Trip> trips) throws IOException{
		storageHelper.storeAllTrips(toJson(trips));
	}
	
	public void storeAllExpenses(Iterable<Expense> expenses) throws IOException{
		storageHelper.storeAllExpenses(toJson(expenses));
	}
	
	public List<Trip> getAllTrips() throws IOException{
		return toDataItem(storageHelper.getAllTrips(), Trip.class);
	}
	
	public List <Expense> getAllExpenses() throws IOException{
		return toDataItem(storageHelper.getAllExpenses(), Expense.class);
	}
	
	private <T extends DataItem> List<T> toDataItem(Iterable<String> data, Class<T> type){
		ArrayList<T> output = new ArrayList<T>();
		for (String d : data){
			output.add(unpackData(d, type));
		}
		return output;
	}
	
	private <T extends DataItem> List<String> toJson(Iterable<T> data){
		ArrayList<String> output = new ArrayList<String>();
		for (T d : data){
			output.add(packData(d));
		}
		return output;
	}
	
	private <T extends DataItem> String packData(T data){
		return gson.toJson(data);
	}
	
	private <T extends DataItem> T unpackData(String json, Class<T> myClass){
		return gson.fromJson(json, myClass);
	}
}
