package cmput301.thornhil_dataClasses;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;

import android.content.Context;

import cmput301.thornhil_helpers.StorageHelper;


public class Cache
{
	
	private interface DataPaser{
		public DataItem parseData(BufferedReader reader);
	}
	
	private Hashtable<Integer, Trip> trips;
	private Hashtable<Integer, Expense> expenses;
	private StorageHelper storageHelper;

	
	
	public Cache(Context context)
	{
		storageHelper = StorageHelper.getInstance(context);
		
	}
	
	public void getAllTrips(){
		try{
			BufferedReader reader = storageHelper.openTripsStorageForReading();
			while (reader.ready()){
				Trip trip = unpackData(reader.readLine(), Trip.class);
				//TODO now add to the hashtable!
			}
		} catch (FileNotFoundException e){
			//TODO add some logic here (Error to screen maybe?)
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getAllExpenses(){
		
	}
	
	public void getAllData(){
		
	}
	
	public void writeTrips(){
		
	}
	
	public void writeExpenses(){
		
	}
	
	public void writeAllData(){
		
	}
	
	private <T extends DataItem> T unpackData(String json, Class<T> myClass){
		return gson.fromJson(json, myClass);
	}
	
}
