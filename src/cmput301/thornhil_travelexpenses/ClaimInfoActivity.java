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

import java.util.ArrayList;
import java.util.Hashtable;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.ClaimStatus;
import cmput301.thornhil_dataClasses.Expense;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import cmput301.thornhil_helpers.Observer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClaimInfoActivity extends Activity implements OnItemSelectedListener, Observer<Cache>{
	
	private Cache cache;
	private Claim claim;
	private Intent intent;
	private ArrayList<View> addedView = new ArrayList<View>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.claim_info_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		cache = TravelExpensesApplication.getCache();
		cache.addView(this);
		intent = getIntent();
		
		parseIntent();
		setUpSpinner();
		setUpView();
		
		Button button = (Button) findViewById(R.id.add_edit_expenses_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startExpenseListActivity();
			}
		});
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.claim_info_menu, menu);		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_claim:
			Intent intent = new Intent(this, ClaimEditorActivity.class);
			intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
			startActivity(intent);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		ClaimStatus status = ClaimStatus.fromString(((String) parent.getItemAtPosition(position)));
		claim.setStatus(status);
		cache.notifyDataChanged();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(Cache model) {
		setUpView();	
	}
	
	@Override
	protected void onDestroy() {
		cache.removeView(this);
		super.onDestroy();
	}
	
	private void setUpView(){
		TextView claimTitle = (TextView) findViewById(R.id.ClaimTitle);
		TextView fromDate = (TextView) findViewById(R.id.fromDate);
		TextView toDate = (TextView) findViewById(R.id.toDate);
		TextView claimDesc = (TextView) findViewById(R.id.claim_description_textview);
		
		claimTitle.setText(claim.getName());
		claimDesc.setText(claim.getDescription());
		fromDate.setText(Formatter.formatDate(claim.getDate()));
		toDate.setText(Formatter.formatDate(claim.getEndDate()));
		
		setUpButton();
		setUpTotal();
	}
	
	private void setUpSpinner(){
		//This code was retrieved from http://developer.android.com/guide/topics/ui/controls/spinner.html on 01/25/2015
		Spinner spinner = (Spinner) findViewById(R.id.claim_status_spinner);
		
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Status_Spinner, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(claim.getStatus().toString()));
		spinner.setOnItemSelectedListener(this);
	}
	
	private void setUpButton(){
		TextView textView = (TextView) findViewById(R.id.not_editable_warning);
		Button button = (Button) findViewById(R.id.add_edit_expenses_button);
		
		
		if (!isClaimEditable()){
			button.setClickable(false);
			textView.setVisibility(View.VISIBLE);
		} else {
			button.setClickable(true);
			textView.setVisibility(View.INVISIBLE);
		}
	}
	
	private void setUpTotal(){
		clearTotals();
		Hashtable<String, Float> totals = new Hashtable<String, Float>();
		for (Expense e : cache.getExpensesForClaim(claim)){
			String key = e.getCurrency().toString();
			Float newAmt = e.getAmmount();
			if (totals.containsKey(key)){
				Float amt = totals.get(key);
				amt += newAmt;
				totals.remove(key);
				totals.put(key, amt);
			} else {
				totals.put(key, newAmt);
			}
		}
		
		LinearLayout parent = (LinearLayout) findViewById(R.id.claim_info_left_linlayout);
		for (String key : totals.keySet()){
			Float amt = totals.get(key);
			View newView = getTotalView(amt, key);
			parent.addView(newView);
			addedView.add(newView);
		}
	}
	
	private TextView getTotalView(Float amt, String currencyCode){
		TextView out = new TextView(this);
		out.setText(amt.toString() + " " + currencyCode);
		return out;
	}
	
	private void clearTotals(){
		LinearLayout parent = (LinearLayout) findViewById(R.id.claim_info_left_linlayout);
		for(View v : addedView){
			parent.removeView(v);
		}
		addedView = new ArrayList<View>();
	}
	
	private boolean isClaimEditable(){
		if (claim.getStatus() == ClaimStatus.submitted || claim.getStatus() == ClaimStatus.approved){
			return false;
		}
		return true;
	}
	
	private void parseIntent(){
		claim = cache.getClaim(intent.getExtras().getInt(Constants.PASSEDCLAIM));
	}

	private void startExpenseListActivity(){
		Log.d("Verbose", "Starting Expense List");
		Intent intent = new Intent(this, ExpenseListActivity.class);
		intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
		startActivity(intent);
	}

}
