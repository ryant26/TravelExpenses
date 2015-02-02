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

package cmput301.thornhil_travelexpenses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_dataClasses.Expense;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import cmput301.thornhil_helpers.MultiSelectListener;
import cmput301.thornhil_helpers.Observer;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExpenseListActivity extends ListActivity implements Observer<Cache>, DataChangedListener<DataItem> {

	private Cache cache; 
	private Claim claim;
	private ExpenseAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_list);
		cache = TravelExpensesApplication.getCache();
		cache.addView(this);
		
		parseIntent();
		
		adapter = new ExpenseAdapter(cache, this);
		setListAdapter(adapter);
		getListView().setMultiChoiceModeListener(new MultiSelectListener<DataItem>(this));
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add_item) {
			startExpenseEditor(null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		startExpenseEditor(adapter.getItem(position));
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	public void update(Cache model) {
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public Intent getParentActivityIntent() {
		super.getParentActivityIntent();
		Intent intent = new Intent(getApplicationContext(), ClaimInfoActivity.class);
		intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
		return intent;
	}
	
	@Override
	protected void onDestroy() {
		cache.removeView(this);
		super.onDestroy();
	}
	
	public void parseIntent(){
		try {
			Intent intent = getIntent();
			claim = cache.getClaim(intent.getExtras().getInt(Constants.PASSEDCLAIM));
		} catch (NullPointerException e){
			Log.d("error", "Expense List wasn't passed a claim");
		}
	}

	private void startExpenseEditor(Expense expense){
		Intent intent = new Intent(this, ExpenseEditorActivity.class);
		if (expense != null) intent.putExtra(Constants.PASSEDEXPENSE, expense.getId());
		intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
		startActivity(intent);
	}
	
	private class ExpenseAdapter extends BaseAdapter{
		
		Cache cache;
		Context context;
		
		public ExpenseAdapter(Cache cache, Context context) {
			this.cache = cache;
			this.context = context;
		}
		
		@Override
		public int getCount() {
			return cache.getExpensesForClaim(claim).size();
		}

		@Override
		public Expense getItem(int position) {
			ArrayList<Expense> list = cache.getExpensesForClaim(claim);
			Collections.sort(list);
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			View rowView;
			Expense item = getItem(position);
			
			if (convertView == null){
				rowView = inflater.inflate(R.layout.expense_row_layout, parent, false);
			} else {
				rowView = convertView;
			}
			
			return formatView(rowView, item);
		}
		
		public View formatView(View rowView, Expense item){
			((TextView) rowView.findViewById(R.id.expense_name)).setText(item.getName());
			((TextView) rowView.findViewById(R.id.expense_category)).setText(item.getCategory().toString());
			((TextView) rowView.findViewById(R.id.expense_date)).setText(Formatter.formatDate(item.getDate()));
			
			((TextView) rowView.findViewById(R.id.expense_total)).setText(item.getAmmount().toString() + " " + item.getCurrency().toString());
			return rowView;
		}
		
		public void deletePositions(ArrayList<Integer> positions){
			ArrayList<Expense> toDelete = new ArrayList<Expense>();
			for (Integer i: positions){
				toDelete.add(getItem(i));
			}
			for (Expense e : toDelete){
				try {
					cache.deleteExpense(e.getId());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void dataItemChanged(ArrayList<Integer> item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataItemCreated(ArrayList<Integer> item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataItemsDeleted(ArrayList<Integer> itemPositions) {
		adapter.deletePositions(itemPositions);
		
	}
}
