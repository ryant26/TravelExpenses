package cmput301.thornhil_travelexpenses;

import java.util.Date;
import java.util.GregorianCalendar;

import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_dataClasses.Expense;
import android.R.anim;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


public class ClaimEditorFragment extends Fragment
{
	DataChangedListener<Claim> claimListener;
	Claim claim;
	
	
	public interface ClaimChangeListener extends DataChangedListener<Claim>{};

	public ClaimEditorFragment(Claim claim) {
		this.claim = claim;
	}
	
	public ClaimEditorFragment() {
		claim = new Claim();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.claim_edit_view, container, false);
		EditText claimNameEditor = (EditText) view.findViewById(R.id.editClaimName);
		DatePicker startDate = (DatePicker) view.findViewById(R.id.claimStartDate);
		DatePicker endDate = (DatePicker) view.findViewById(R.id.claimEndDate);
		
		
		try{
			claimNameEditor.setText(claim.getName());
			startDate.updateDate(claim.getDate().getYear(), claim.getDate().getMonth(), claim.getDate().getDay());
			endDate.updateDate(claim.getEndDate().getYear(), claim.getEndDate().getMonth(), claim.getEndDate().getDay());
			
		} catch (NullPointerException e){}
		
		return view;
	};
	
	@Override
	public void onAttach(Activity activity) {
		try {
			claimListener = (DataChangedListener<Claim>) activity;
		} catch (ClassCastException e){
			Log.d("error", "Activity calling fragment has not implemented the listener");
		}
		super.onAttach(activity);
	}
	
	
}
