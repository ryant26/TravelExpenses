package cmput301.thornhil_dataClasses;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import cmput301.thornhil_helpers.StorageAdapter;
import cmput301.thornhil_helpers.StorageHelper;


public class Cache
{
	
	private interface DataPaser{
		public DataItem parseData(BufferedReader reader);
	}
	
	private Hashtable<Integer, Trip> trips;
	private Hashtable<Integer, Expense> expenses;
	private StorageAdapter storageAdapter;

	
	
	public Cache(Context context) throws IOException
	{
		storageAdapter = new StorageAdapter(context);
		getAllDataFromStorage();
	}

	public void addNewTrip(Trip trip) throws IOException{
		addTrip(trip);
		writeTrips();
	}
	
	public void addNewExpense(Expense expense) throws IOException{
		addExpense(expense);
		writeExpenses();
	}
	
	public Iterable<Trip> getAllTrips(){
		return trips.values();
	}
	
	public Iterable<Expense> getAllExpenses(){
		return expenses.values();
	}
	
	public void deleteTrip(Integer ID) throws IOException{
		trips.remove(ID);
		for (Expense e : getAllExpenses()){
			if (e.getTripId() == ID){
				expenses.remove(e.getId());
			}
		}
		writeAllData();
	}
	
	public void deleteExpense(Integer ID) throws IOException{
		expenses.remove(ID);
		writeExpenses();
	}
	
	public void editTrip(Trip t) throws IOException{
		trips.remove(t.getId());
		addTrip(t);
		writeTrips();
	}
	
	public void editExpense(Expense e) throws IOException{
		expenses.remove(e.getId());
		addExpense(e);
		writeExpenses();
	}
	
	private void addExpense(Expense e){
		expenses.put(e.getId(), e);
	}

	private void addTrip (Trip t){
		trips.put(t.getId(), t);
	}
	
	private void getAllDataFromStorage() throws IOException{
		getTripsFromStorage();
		getExpensesFromStorage();
	}
	
	private void getTripsFromStorage() throws IOException{
		for (Trip t : storageAdapter.getAllTrips()){
			addTrip(t);
		}
	}
	
	private void getExpensesFromStorage() throws IOException{
		for (Expense e : storageAdapter.getAllExpenses()){
			addExpense(e);
		}
	}
	
	private void writeTrips() throws IOException{
		storageAdapter.storeAllTrips(getAllTrips());
	}
	
	private void writeExpenses() throws IOException{
		storageAdapter.storeAllExpenses(getAllExpenses());
	}
	
	private void writeAllData() throws IOException{
		writeTrips();
		writeExpenses();
	}
	
}
