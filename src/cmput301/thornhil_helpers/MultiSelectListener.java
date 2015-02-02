package cmput301.thornhil_helpers;

import java.util.ArrayList;

import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_travelexpenses.DataChangedListener;
import cmput301.thornhil_travelexpenses.R;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

public class MultiSelectListener <T extends DataItem> implements MultiChoiceModeListener{

	private DataChangedListener<T> listener;
	ArrayList<Integer> selectedItems = new ArrayList<Integer>();
	int menu;
	
	public MultiSelectListener(DataChangedListener<T> listener) {
		this(listener, R.menu.multi_select_menu);
	}
	
	public MultiSelectListener(DataChangedListener<T> listener, int menu) {
		this.menu = menu;
		this.listener = listener;
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(this.menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			listener.dataItemsDeleted(selectedItems);
			mode.finish();
			break;
		case R.id.email_item_main:
			listener.dataItemChanged(selectedItems);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		selectedItems = new ArrayList<Integer>();
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		if (checked){
			selectedItems.add(position);
		}else {
			selectedItems.remove(selectedItems.indexOf(position));
		}
	}
}
