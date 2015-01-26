package cmput301.thornhil_helpers;

import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_travelexpenses.DataChangedListener;
import cmput301.thornhil_travelexpenses.R;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

public class MultiSelectListener <T extends DataItem> implements MultiChoiceModeListener{

	private DataChangedListener<T> listener;
	
	public MultiSelectListener(DataChangedListener<T> listener) {
		this.listener = listener;
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(R.menu.multi_select_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		// TODO Auto-generated method stub
		
	}

}
