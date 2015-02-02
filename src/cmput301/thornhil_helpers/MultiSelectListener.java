//	Copyright 2015 Ryan Thornhill
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

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
