package cmput301.thornhil_travelexpenses;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.ClaimStatus;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import cmput301.thornhil_helpers.Observer;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClaimInfoActivity extends Activity implements OnItemSelectedListener, Observer<Cache>{
	
	private Cache cache;
	private Claim claim;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.claim_info_view);
		cache = TravelExpensesApplication.getCache();
		intent = getIntent();
		
		parseIntent();
		setUpView();
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
			//TODO FIRE AN INTENT
			((MainActivity) getActivity()).openAddClaimFrag(claim);
			break;

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
	
	private void setUpView(){
		TextView claimTitle = (TextView) findViewById(R.id.ClaimTitle);
		TextView fromDate = (TextView) findViewById(R.id.fromDate);
		TextView toDate = (TextView) findViewById(R.id.toDate);
		
		claimTitle.setText(claim.getName());
		fromDate.setText(Formatter.formatDate(claim.getDate()));
		toDate.setText(Formatter.formatDate(claim.getEndDate()));
		
		
		setUpSpinner();
	}
	
	private void setUpSpinner(){
		//This code was retrieved from http://developer.android.com/guide/topics/ui/controls/spinner.html on 01/25/2015
		Spinner spinner = (Spinner) findViewById(R.id.claim_status_spinner);
		TextView textView = (TextView) findViewById(R.id.not_editable_warning);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Status_Spinner, android.R.layout.simple_spinner_item);
		spinner.setAdapter(adapter);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(claim.getStatus().toString()));
		spinner.setOnItemSelectedListener(this);
		
		if (!isClaimEditable()){
			Button button = (Button) findViewById(R.id.add_edit_expenses_button);
			button.setClickable(false);
			textView.setVisibility(View.VISIBLE);
		} else {
			textView.setVisibility(View.INVISIBLE);
		}
		
	}
	
	private boolean isClaimEditable(){
		if (claim.getStatus() == ClaimStatus.submitted || claim.getStatus() == ClaimStatus.approved){
			return false;
		}
		return true;
	}
	
	private void parseIntent(){
		String claimIdString = intent.getExtras().getString(Constants.PASSEDCLAIM);
		claim = cache.getClaim(Integer.parseInt(claimIdString));
	}

	

}
