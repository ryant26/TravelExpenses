package cmput301.thornhil_travelexpenses;

import cmput301.thornhil_dataClasses.Claim;
import android.app.Fragment;


public class CreateClaimFragment extends Fragment
{
	public interface OnClaimCreatedListener {
		public void onClaimCreated(Claim claim);
	}
}
