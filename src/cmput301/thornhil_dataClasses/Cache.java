package cmput301.thornhil_dataClasses;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import android.util.Log;
import cmput301.thornhil_helpers.StorageAdapter;
import cmput301.thornhil_helpers.StorageHelper;


public class Cache
{
	
	private interface DataPaser{
		public DataItem parseData(BufferedReader reader);
	}
	
	private Hashtable<Integer, Claim> claims = new Hashtable<Integer, Claim>();
	private Hashtable<Integer, Expense> expenses = new Hashtable<Integer, Expense>();
	private StorageAdapter storageAdapter;
	
	public Cache(Context context)
	{
		storageAdapter = new StorageAdapter(context);
		try {
			getAllDataFromStorage();
		} catch (FileNotFoundException e){
			Log.d("Error", "Opening cache, could not find storage File");
		} catch (IOException e){
			Log.d("Error", "Opening cache, IO Exception");
		}
	}

	public void addNewClaim(Claim claim) throws IOException{
		addClaim(claim);
		writeClaims();
	}
	
	public void addNewExpense(Expense expense) throws IOException{
		addExpense(expense);
		writeExpenses();
	}
	
	public Collection<Claim> getAllClaims(){
		return claims.values();
	}
	
	public Iterable<Expense> getAllExpenses(){
		return expenses.values();
	}
	
	public void deleteClaim(Integer ID) throws IOException{
		claims.remove(ID);
		for (Expense e : getAllExpenses()){
			if (e.getClaimId() == ID){
				expenses.remove(e.getId());
			}
		}
		writeAllData();
	}
	
	public void deleteExpense(Integer ID) throws IOException{
		expenses.remove(ID);
		writeExpenses();
	}
	
	public void editClaim(Claim t) throws IOException{
		claims.remove(t.getId());
		addClaim(t);
		writeClaims();
	}
	
	public void editExpense(Expense e) throws IOException{
		expenses.remove(e.getId());
		addExpense(e);
		writeExpenses();
	}
	
	private void addExpense(Expense e){
		expenses.put(e.getId(), e);
	}

	private void addClaim (Claim t){
		claims.put(t.getId(), t);
	}
	
	private void getAllDataFromStorage() throws IOException{
		getClaimsFromStorage();
		getExpensesFromStorage();
	}
	
	private void getClaimsFromStorage() throws IOException{
		for (Claim t : storageAdapter.getAllClaims()){
			addClaim(t);
		}
	}
	
	private void getExpensesFromStorage() throws IOException{
		for (Expense e : storageAdapter.getAllExpenses()){
			addExpense(e);
		}
	}
	
	private void writeClaims() throws IOException{
		storageAdapter.storeAllClaims(getAllClaims());
	}
	
	private void writeExpenses() throws IOException{
		storageAdapter.storeAllExpenses(getAllExpenses());
	}
	
	private void writeAllData() throws IOException{
		writeClaims();
		writeExpenses();
	}
	
}
