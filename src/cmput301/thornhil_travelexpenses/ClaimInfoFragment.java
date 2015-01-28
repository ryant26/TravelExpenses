package cmput301.thornhil_travelexpenses;

import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.ClaimStatus;
import cmput301.thornhil_helpers.Formatter;
import android.R.anim;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClaimInfoFragment extends Fragment implements OnItemSelectedListener, Observer{
	
	private DataChangedListener<Claim> claimListener;
	private Claim claim;
	
	public ClaimInfoFragment(Claim claim){
		this.claim = claim;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.claim_info_view, container, false);
		setUpView(view);
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		activity.invalidateOptionsMenu();
		try {
			claimListener = (DataChangedListener<Claim>) activity;
		} catch (ClassCastException e){
			Log.d("error", "Activity calling fragment has not implemented the listener");
		}
		super.onAttach(activity);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (getFragmentManager().getBackStackEntryCount() == 1){
			inflater.inflate(R.menu.claim_info_menu, menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_claim:
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
		claimListener.dataItemChanged(claim);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(Observable observable, Object data) {
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				setUpView(getView());
			}
		});
		
		
	}
	
	private void setUpView(View mainView){
		TextView claimTitle = (TextView) mainView.findViewById(R.id.ClaimTitle);
		TextView fromDate = (TextView) mainView.findViewById(R.id.fromDate);
		TextView toDate = (TextView) mainView.findViewById(R.id.toDate);
		
		claimTitle.setText(claim.getName());
		fromDate.setText(Formatter.formatDate(claim.getDate()));
		toDate.setText(Formatter.formatDate(claim.getEndDate()));
		
		
		
		setUpSpinner(mainView);
	}
	
	private void setUpSpinner(View mainView){
		//This code was retrieved from http://developer.android.com/guide/topics/ui/controls/spinner.html on 01/25/2015
		Spinner spinner = (Spinner) mainView.findViewById(R.id.claim_status_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Status_Spinner, android.R.layout.simple_spinner_item);
		spinner.setAdapter(adapter);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(claim.getStatus().toString()));
		spinner.setOnItemSelectedListener(this);
		
	}

}
