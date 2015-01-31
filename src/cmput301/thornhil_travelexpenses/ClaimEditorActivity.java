package cmput301.thornhil_travelexpenses;

import java.io.IOException;
import java.util.Calendar;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_helpers.Constants;
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
			getAllFields();
			if (newClaim){
				try {
					cache.addNewClaim(claim);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				cache.notifyDataChanged();
			}
			finish();
			return true;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void getAllFields(){
		Calendar cal = Calendar.getInstance();
		
		cal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
		claim.setDate(cal.getTime());
		
		cal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());		
		claim.setEndDate(cal.getTime());
		
		String name = claimNameEditor.getText().toString();

		claim.setName(name);
	}
	
	private void parseIntent(){
		claim = cache.getClaim(intent.getExtras().getInt(Constants.PASSEDCLAIM));
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
