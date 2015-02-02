package cmput301.thornhil_dataClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.util.Log;
import cmput301.thornhil_helpers.Observer;
import cmput301.thornhil_helpers.StorageAdapter;
import cmput301.thornhil_travelexpenses.ExpenseListActivity;


public class Cache
{
	
	private Hashtable<Integer, Claim> claims = new Hashtable<Integer, Claim>();
	private Hashtable<Integer, Expense> expenses = new Hashtable<Integer, Expense>();
	private ArrayList<Observer<Cache>> views = new ArrayList<Observer<Cache>>();
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
	
	public ArrayList<Claim> getAllClaims(){
		return new ArrayList<Claim>(claims.values());
	}
	
	public Claim getClaim(Integer id){
		return claims.get(id);
	}
	
	public Expense getExpense(Integer id){
		return expenses.get(id);
	}
	
	public ArrayList<Expense> getAllExpenses(){
		return new ArrayList<Expense>(expenses.values());
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
	
	public void deleteClaimNoWrite(Claim claim){
		for (Expense e : getExpensesForClaim(claim)){
			deleteExpenseNoWrite(e.getId());
		}
		claims.remove(claim.getId());
	}
	
	public void deleteExpenseNoWrite(Integer ID){
		expenses.remove(ID);
	}
	
	public void deleteExpense(Integer ID) throws IOException{
		deleteExpenseNoWrite(ID);
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
	
	public void writeAllData() throws IOException{
		writeClaims();
		writeExpenses();
	}
	
	public void addView(Observer<Cache> o){
		views.add(o);
	}
	
	public void removeView(Observer<Cache> o){
		views.remove(o);
	}
	
	public void notifyDataChanged(){
		try{
			writeAllData();
		} catch (IOException e){
			Log.d("error", "error writing all data");
		}
		updateViews();
	}
	
	private void updateViews(){
		for (Observer<Cache> o : views){
			if (o != null) {
				o.update(this); // Were we garbage collected?
			} else {
				removeView(o);
			}
		}
	}
	
	public ArrayList<Expense> getExpensesForClaim(Claim claim){
		ArrayList<Expense> out = new ArrayList<Expense>();
		for (Expense e : getAllExpenses()){
			if (e.getClaimId().equals(claim.getId())){
				out.add(e);
			}
		}
		return out;
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
		updateViews();
	}
	
	private void writeExpenses() throws IOException{
		storageAdapter.storeAllExpenses(getAllExpenses());
		updateViews();
	}
	
	
	
}
