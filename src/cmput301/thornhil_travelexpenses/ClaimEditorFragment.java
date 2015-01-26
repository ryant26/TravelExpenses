package cmput301.thornhil_travelexpenses;

import java.util.Date;
import java.util.GregorianCalendar;

import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_dataClasses.Expense;
import android.R.anim;
import android.R.menu;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


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
		
		
		try{
			claimNameEditor.setText(claim.getName());
			startDate.updateDate(claim.getDate().getYear(), claim.getDate().getMonth(), claim.getDate().getDay());
			endDate.updateDate(claim.getEndDate().getYear(), claim.getEndDate().getMonth(), claim.getEndDate().getDay());
			
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
			if (newClaim){
				getAllFields();
				claimListener.dataItemCreated(claim);
			} else {
				claimListener.dataItemChanged(claim);
			}
			break;
		default:
			break;
		}
		getActivity().getFragmentManager().popBackStack();
		return super.onOptionsItemSelected(item);
	}
	
	private void getAllFields(){
		Date sDate = new Date(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
		Date eDate = new Date(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());
		String name = claimNameEditor.getText().toString();
		
		claim = new Claim(name, sDate, eDate);
	}
	
}
