package cmput301.thornhil_travelexpenses;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.EditText;


public class ClaimEditorActivity extends Activity
{
	private Cache cache;
	private Claim claim;
	private Boolean newClaim;
	private EditText claimNameEditor;
	private DatePicker startDate;
	private DatePicker endDate;
	private Intent intent;
	
	public interface ClaimChangeListener extends DataChangedListener<Claim>{};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cache = TravelExpensesApplication.getCache();
		
		setContentView(R.layout.claim_edit_view);
		intent = getIntent();
		parseIntent();
		
		claimNameEditor = (EditText) findViewById(R.id.editClaimName);
		startDate = (DatePicker) findViewById(R.id.claimStartDate);
		endDate = (DatePicker) findViewById(R.id.claimEndDate);
		
		Calendar cal = Calendar.getInstance();
		
		try{
			claimNameEditor.setText(claim.getName());
			
			cal.setTime(claim.getDate());
			startDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			cal.setTime(claim.getEndDate());
			endDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
		} catch (NullPointerException e){}
		
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
		Calendar cal = Calendar.getInstance();
		Date sDate;
		Date eDate;
		
		cal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
		sDate = cal.getTime();
		
		
		cal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());		
		eDate = cal.getTime();
		
		if (eDate.before(sDate)){
			throw new RuntimeException("The End date must be after the Start date");
		} else {
			claim.setDate(sDate);
			claim.setEndDate(eDate);
		}
		
		String name = claimNameEditor.getText().toString();

		claim.setName(name);
		
		
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
	
}
