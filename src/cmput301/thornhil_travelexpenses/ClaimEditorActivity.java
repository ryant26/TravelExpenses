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
import java.util.Date;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface.OnDismissListener;


public class ClaimEditorActivity extends Activity implements OnDismissListener
{
	private Cache cache;
	private Claim claim;
	private Boolean newClaim;
	private EditText claimNameEditor;
	private EditText claimDescriptionEditor;
	private TextView startDateView;
	private TextView endDateView;
	private Date startDateStorage;
	private Date endDateStorage;
	
	private Intent intent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cache = TravelExpensesApplication.getCache();
		
		setContentView(R.layout.claim_edit_view);
		intent = getIntent();
		parseIntent();
		
		claimNameEditor = (EditText) findViewById(R.id.editClaimName);
		claimDescriptionEditor = (EditText) findViewById(R.id.claim_description);
		startDateView = (TextView) findViewById(R.id.claimStartDate);
		endDateView = (TextView) findViewById(R.id.claimEndDate);
		
		
		try{
			startDateStorage = new Date(claim.getDate().getTime());
			endDateStorage = new Date(claim.getEndDate().getTime());
			
		} catch (NullPointerException e){
			Calendar cal = Calendar.getInstance();
			startDateStorage = cal.getTime();
			endDateStorage = cal.getTime();
		}
		
		setUpView();
		setUpListeners();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpView();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_claim_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_claim:
			try {
				getAllFields();
				if (newClaim){
					cache.addNewClaim(claim);
				} else {
					cache.notifyDataChanged();
				}
				finish();
			} catch (IOException e){
				e.printStackTrace();
			} catch (RuntimeException e){
				Formatter.showToast(this, "Ensure start date is before end date");
			}
			return true;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public Intent getParentActivityIntent() {
		super.getParentActivityIntent();
		Intent intent = new Intent(getApplicationContext(), ClaimInfoActivity.class);
		intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
		return intent;
	}
	
	private void getAllFields(){
		
		if (endDateStorage.before(startDateStorage)){
			throw new RuntimeException("The End date must be after the Start date");
		} else {
			claim.setDate(startDateStorage);
			claim.setEndDate(endDateStorage);
		}
		
		String name = claimNameEditor.getText().toString();

		claim.setName(name);
		claim.setDescription(claimDescriptionEditor.getText().toString());
		
	}
	
	private void parseIntent(){
		try{
			claim = cache.getClaim(intent.getExtras().getInt(Constants.PASSEDCLAIM));
		} catch (NullPointerException e){
			Log.d("VERBOSE", "No data passed in intent");
		}
		initialize();
	}
	
	private void initialize(){
		if (claim == null){
			claim = new Claim();
			newClaim = true;
		} else {
			newClaim = false;
		}
	}
	
	private void setUpView(){
		claimNameEditor.setText(claim.getName());
		claimDescriptionEditor.setText(claim.getDescription());
		setUpDates();
	}
	
	private void setUpDates(){
		startDateView.setText(Formatter.formatDate(startDateStorage));
		endDateView.setText(Formatter.formatDate(endDateStorage));
	}
	
	private void setUpListeners(){
		
		
		startDateView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerFragment sDatePicker = new DatePickerFragment(startDateStorage);
				sDatePicker.show(getFragmentManager(), null);
			}});
		
		endDateView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerFragment eDatePicker = new DatePickerFragment(endDateStorage);
				eDatePicker.show(getFragmentManager(), null);
			}
		});
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		setUpDates();
	}
}
