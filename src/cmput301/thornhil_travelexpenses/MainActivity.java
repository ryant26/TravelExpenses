package cmput301.thornhil_travelexpenses;


import java.util.ArrayList;

import cmput301.thornhil_helpers.*;
import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_helpers.Constants;
import cmput301.thornhil_helpers.Formatter;
import cmput301.thornhil_helpers.MultiSelectListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements Observer<Cache>, DataChangedListener<Claim>{ 
	
	private ClaimAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        
        Cache dataCache = TravelExpensesApplication.getCache();
        dataCache.addView(this);
        
        adapter = new ClaimAdapter(this, R.layout.claim_row_layout, dataCache);
        ListView listView = (ListView) findViewById(R.id.Claims_List_View);
        listView.setAdapter(adapter);
        listView.setEmptyView((TextView) findViewById(android.R.id.empty));	
        setupListeners();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	return super.onCreateOptionsMenu(menu);
    };
    
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
    	switch (item.getItemId()) {
    	   case android.R.id.home:
    	       return true;
    	   case R.id.add_claim:
			   openAddClaimActivity(null);
			   return true;
    	    default:
    	       break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    
    public void openAddClaimActivity(Claim claim) {
    	Intent intent = new Intent(this, ClaimEditorActivity.class);
    	if (claim != null) intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
		startActivity(intent);
    }
    
    private void openClaimInfoActivity(Claim claim){
    	Intent intent = new Intent(this, ClaimInfoActivity.class);
    	intent.putExtra(Constants.PASSEDCLAIM, claim.getId());
    	startActivity(intent);
    }
    
    private void setupListeners(){
		ListView listView = (ListView) findViewById(R.id.Claims_List_View);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openClaimInfoActivity(adapter.getItem(position));
			}
			
		});
		
		listView.setMultiChoiceModeListener(new MultiSelectListener<Claim>(this));
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);		
    }
    
    private class ClaimAdapter extends BaseAdapter{
    	
    	private Cache cache;
    	private Context context;
    	
		public ClaimAdapter(Context context, int textViewResourceId, Cache cache) {
			this.context = context;
			this.cache = cache;
		}
		
		@Override
		public int getCount() {
			return cache.getAllClaims().size();
		};
		
		@Override
		public Claim getItem(int position){
			return (Claim) cache.getAllClaims().toArray()[position];
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			View rowView = null;
			Claim item = getItem(position);
			
			if ( convertView == null){
				rowView = inflater.inflate(R.layout.claim_row_layout, parent, false);
			} else {
				rowView = convertView;
			}
			
			return formatView(rowView, item);
		}
		
		public void forceSaveData(){
			try{
				cache.writeAllData();
			} catch (Exception e){
				Log.d("error", "Force write data, failed!");
			}
		}
		
		public void deletePositions(ArrayList<Integer> positions){
			ArrayList<Claim> toDelete = new ArrayList<Claim>();
			for (Integer i : positions){
				toDelete.add(getItem(positions.get(i)));
			}
			for (Claim c : toDelete){
				cache.deleteClaimNoWrite(c);
			}
			forceSaveData();
		}
		
		public View formatView(View rowView, Claim item){
			TextView status = ((TextView) rowView.findViewById(R.id.Status));
			((TextView) rowView.findViewById(R.id.Name)).setText(item.getName());
			((TextView) rowView.findViewById(R.id.Status)).setText("Status: " + item.getStatus().name());
			((TextView) rowView.findViewById(R.id.date)).setText(Formatter.formatDate(item.getDate()));
			
			switch (item.getStatus()) {
			case approved:
				status.setTextColor(Color.GREEN);
				break;

			default:
				status.setTextColor(Color.BLACK);
				break;
			}
			
			return rowView;
		}
    	
    }

	@Override
	public void update(Cache model) {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void dataItemChanged(Claim item) {
		
	}

	@Override
	public void dataItemCreated(Claim item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataItemsDeleted(ArrayList<Integer> itemPositions) {
		adapter.deletePositions(itemPositions);
		
	}

}