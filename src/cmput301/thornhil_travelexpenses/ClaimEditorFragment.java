package cmput301.thornhil_travelexpenses;

import java.util.Calendar;

import cmput301.thornhil_dataClasses.Claim;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.EditText;


public class ClaimEditorFragment extends Fragment
{
	private DataChangedListener<Claim> claimListener;
	private Claim claim;
	private Boolean newClaim;
	private EditText claimNameEditor;
	private DatePicker startDate;
	private DatePicker endDate;
	
	public interface ClaimChangeListener extends DataChangedListener<Claim>{};

	public ClaimEditorFragment(Claim claim) {
		this.claim = claim;
		newClaim = false;
	}
	
	public ClaimEditorFragment() {
		claim = new Claim();
		newClaim = true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.claim_edit_view, container, false);
		claimNameEditor = (EditText) view.findViewById(R.id.editClaimName);
		startDate = (DatePicker) view.findViewById(R.id.claimStartDate);
		endDate = (DatePicker) view.findViewById(R.id.claimEndDate);
		
		Calendar cal = Calendar.getInstance();
		
		
		try{
			claimNameEditor.setText(claim.getName());
			
			cal.setTime(claim.getDate());
			startDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			cal.setTime(claim.getEndDate());
			endDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
		} catch (NullPointerException e){}
		
		return view;
	};
	
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
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.edit_claim_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch (item.getItemId()) {
		case R.id.save_claim:
			getAllFields();
			if (newClaim){
				claimListener.dataItemCreated(claim);
			} else {
				claimListener.dataItemChanged(claim);
			}
			getActivity().getFragmentManager().popBackStack();
			break;
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
	
}
