package cmput301.thornhil_travelexpenses;

import cmput301.thornhil_dataClasses.Claim;
import android.R.anim;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClaimInfoFragment extends Fragment implements OnItemSelectedListener{
	
	private DataChangedListener<Claim> claimListener;
	private Claim claim;
	
	public ClaimInfoFragment(Claim claim){
		this.claim = claim;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
		inflater.inflate(R.menu.claim_info_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	private void setUpView(View mainView){
		TextView claimTitle = (TextView) mainView.findViewById(R.id.ClaimTitle);
		TextView fromDate = (TextView) mainView.findViewById(R.id.fromDate);
		TextView toDate = (TextView) mainView.findViewById(R.id.toDate);
		
		claimTitle.setText(claim.getName());
		fromDate.setText(claim.getDate().toString());
		toDate.setText(claim.getEndDate().toString());
		
		setUpSpinner(mainView);
	}
	
	private void setUpSpinner(View mainView){
		//This code was retrieved from http://developer.android.com/guide/topics/ui/controls/spinner.html on 01/25/2015
		Spinner spinner = (Spinner) mainView.findViewById(R.id.claim_status_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Status_Spinner, android.R.layout.simple_spinner_item);
		spinner.setAdapter(adapter);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(claim.getStatusString()));
		spinner.setOnItemSelectedListener(this);
		
	}

}
