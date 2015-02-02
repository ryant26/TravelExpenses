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
		int id = item.getItemId();
		if (id == R.id.save_claim) {
			try {
				getAllFields();
				if (newExpense){
					cache.addNewExpense(expense);
				} else {
					cache.notifyDataChanged();
				}
				finish();
			} catch (IOException e){
				Log.d("Error", "Could not save expense");
			} catch (RuntimeException e){
				Formatter.showToast(this, "Date must be within claim range");
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public Intent getParentActivityIntent() {
		super.getParentActivityIntent();
		Intent intent = new Intent(getApplicationContext(), ExpenseListActivity.class);
		intent.putExtra(Constants.PASSEDCLAIM, parentClaim.getId());
		return intent;
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
		
//		if (storageDate.before(parentClaim.getDate()) || storageDate.after(parentClaim.getEndDate())){
//			throw new RuntimeException("Invalid date!");
//		}
		
		expense.setDate(storageDate);
		expense.setName(desc.getText().toString());
		expense.setCategory(ExpenseCategories.fromString(category.getSelectedItem().toString()));
		expense.setCurrency(Currency.getInstance(currency.getSelectedItem().toString()));
		expense.setClaimId(parentClaim.getId());
		try{
			expense.setAmmount(Float.parseFloat(amt.getText().toString()));
		} catch (RuntimeException e){
			expense.setAmmount(0.00f);
		}
		
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		setUpDate();
	}
}
