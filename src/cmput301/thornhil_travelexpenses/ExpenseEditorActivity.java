package cmput301.thornhil_travelexpenses;

import java.io.IOException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.Expense;
import cmput301.thornhil_dataClasses.ExpenseCategories;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ExpenseEditorActivity extends Activity implements OnDismissListener{
	
	private Cache cache;
	private Expense expense;
	private Boolean newExpense;
	private Intent intent;
	private Date storageDate;
	private Claim parentClaim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cache = TravelExpensesApplication.getCache();
		setContentView(R.layout.activity_expense_editor);
		
		intent = getIntent();
		parseIntent();
		
		try{
			storageDate = new Date(expense.getDate().getTime());
		} catch (NullPointerException e){
			Calendar cal = Calendar.getInstance();
			storageDate = cal.getTime();
		}
		
		setUpView();
		setUpSpinners();
		setUpListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_claim_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save_claim) {
			getAllFields();
			if (newExpense){
				try {
					cache.addNewExpense(expense);
				} catch (IOException e) {
					e.printStackTrace();
					Log.d("Error", "error saving new expense");
				}
			} else {
				cache.notifyDataChanged();
			}
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void parseIntent(){
		try{
			parentClaim = cache.getClaim(intent.getExtras().getInt(Constants.PASSEDCLAIM));
			expense = cache.getExpense(intent.getExtras().getInt(Constants.PASSEDEXPENSE));
		} catch (NullPointerException e){
			Log.d("verbose", "No expense passed in intent");
		}
		initialize();
	}
	
	private void initialize(){
		if (expense == null){
			expense = new Expense();
			newExpense = true;
		} else {
			newExpense = false;
		}
	}
	
	
	private void setUpView(){
		EditText expenseDesc = (EditText) findViewById(R.id.edit_expense_desc);
		EditText expenseTot = (EditText) findViewById(R.id.edit_expense_total);
		try{
			expenseDesc.setText(expense.getName());
			expenseTot.setText(expense.getAmmount().toString());
		} catch (NullPointerException e){
			Log.d("verbose", "Setting up view without expense");
		}
		setUpDate();
	}
	
	private void setUpDate(){
		TextView date = (TextView) findViewById(R.id.expense_date_textView);
		date.setText(Formatter.formatDate(storageDate));
		
	}
	
	private void setUpSpinners(){
		Spinner currency = (Spinner) findViewById(R.id.expense_currency_spinner);
		Spinner category = (Spinner) findViewById(R.id.expense_category_spinner);
		
		ArrayAdapter<ExpenseCategories> categoryAdapter = new ArrayAdapter<ExpenseCategories>(this, android.R.layout.simple_spinner_item, ExpenseCategories.values());
		category.setAdapter(categoryAdapter);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setSelection(categoryAdapter.getPosition(expense.getCategory()));
		
		ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency_spinner, android.R.layout.simple_spinner_item);
		currency.setAdapter(currencyAdapter);
		currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 if (!newExpense) currency.setSelection(currencyAdapter.getPosition(expense.getCurrency().toString()));
		
	}
	
	private void setUpListeners(){
		TextView date = (TextView) findViewById(R.id.expense_date_textView);
		date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment(storageDate);
				datePicker.show(getFragmentManager(), null);
				
			}
		});
	}
	
	private void getAllFields(){
		EditText desc = (EditText) findViewById(R.id.edit_expense_desc);
		Spinner category = (Spinner) findViewById(R.id.expense_category_spinner);
		Spinner currency = (Spinner) findViewById(R.id.expense_currency_spinner);
		EditText amt = (EditText) findViewById(R.id.edit_expense_total);
		
		expense.setDate(storageDate);
		expense.setName(desc.getText().toString());
		expense.setCategory(ExpenseCategories.fromString(category.getSelectedItem().toString()));
		expense.setCurrency(Currency.getInstance(currency.getSelectedItem().toString()));
		expense.setClaimId(parentClaim.getId());
		expense.setAmmount(Float.parseFloat(amt.getText().toString()));
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		setUpDate();
	}
}
