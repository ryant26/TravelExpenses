package cmput301.thornhil_travelexpenses;


import java.io.IOException;
import java.util.ArrayList;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_helpers.MultiSelectListener;
import cmput301.thornhil_travelexpenses.ClaimEditorFragment.ClaimChangeListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.content.Context;
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


public class MainActivity extends Activity implements ClaimChangeListener { 
	
	private ClaimAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        
        Cache dataCache = new Cache(getApplicationContext());
        adapter = new ClaimAdapter(this, R.layout.claim_row_layout, dataCache);
        ListView listView = (ListView) findViewById(R.id.Claims_List_View);
        listView.setAdapter(adapter);
        listView.setEmptyView((TextView) findViewById(android.R.id.empty));	
        setupListeners();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if (getFragmentManager().getBackStackEntryCount() == 0){
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.main, menu);
    	}
    	return super.onCreateOptionsMenu(menu);
    };
    
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
    	switch (item.getItemId()) {
    	   case android.R.id.home:
    		   popFragment();
    	       break;
    	   case R.id.add_claim:
			   openAddClaimFrag(null);
			   break;
    	    default:
    	       break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void dataItemChanged(Claim item) {
    	adapter.notifyDataSetChanged();
    	adapter.forceSaveData();
    };
    
    @Override
    public void dataItemCreated(Claim item) {
    	try{
    		adapter.addClaim(item);
    	} catch (IOException e){
    		Log.d("error", "Could not add claim to storage!");
    	}
    }
    
    @Override
	public void dataItemsDeleted(ArrayList<Claim> items) {
		// TODO Auto-generated method stub
		
	}
    
    private void openAddClaimFrag(Claim claim) {
    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    	ClaimEditorFragment fragment;
    	
    	if (claim == null) {
    		fragment = new ClaimEditorFragment();
    	} else {
    		fragment = new ClaimEditorFragment(claim);
    	}
    	
		fragmentTransaction.add(R.id.main_activity_frame_layout, fragment, "Add Claim");
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
    }
    
    private void popFragment(){
    	FragmentManager fm = getFragmentManager();
    	if (fm.getBackStackEntryCount() > 0){
    		fm.popBackStack();
    	}
    }
    
    private void setupListeners(){
		getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			
			@Override
			public void onBackStackChanged() {
				int stackSize = getFragmentManager().getBackStackEntryCount();
				if (stackSize > 0){
					getActionBar().setDisplayShowHomeEnabled(true);
					getActionBar().setDisplayHomeAsUpEnabled(true);
				} else {
					getActionBar().setDisplayShowHomeEnabled(false);
					getActionBar().setDisplayHomeAsUpEnabled(false);
				}
			}
		});
		
		ListView listView = (ListView) findViewById(R.id.Claims_List_View);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openAddClaimFrag(adapter.getItem(position));
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
		
		public void addClaim(Claim claim) throws IOException{
			cache.addNewClaim(claim);
			notifyDataSetChanged();
		}
		
		
		public View formatView(View rowView, Claim item){
			((TextView) rowView.findViewById(R.id.Name)).setText(item.getName());
			((TextView) rowView.findViewById(R.id.Status)).setText("Status: " + item.getStatusString());
			((TextView) rowView.findViewById(R.id.date)).setText(item.getDate().toString());
			
			switch (item.getStatus()) {
			case approved:
				rowView.setBackgroundColor(Color.DKGRAY);
				break;

			default:
				break;
			}
			
			return rowView;
		}
    	
    }

}