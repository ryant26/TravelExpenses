package cmput301.thornhil_travelexpenses;


import java.util.ArrayList;
import java.util.Collections;

import cmput301.thornhil_helpers.*;
import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.Expense;
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
import android.widget.Toast;


public class MainActivity extends Activity implements Observer<Cache>, DataChangedListener<Claim>{ 
	
	private ClaimAdapter adapter;
	private Cache dataCache;
	private String emailAddress;
	ArrayList<Claim> claimsToEmail;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        
        dataCache = TravelExpensesApplication.getCache();
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
    	   case R.id.add_item:
			   openAddClaimActivity(null);
			   return true;
    	    default:
    	       break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
    	dataCache.removeView(this);
    	super.onDestroy();
    }
    
    private void openAddClaimActivity(Claim claim) {
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
		
		listView.setMultiChoiceModeListener(new MultiSelectListener<Claim>(this, R.menu.main_and_email));
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);		
    }
    
    private void sendEmail(ArrayList<Claim> claims){
    	claimsToEmail = claims;
    	EmailSelectDialog emailDiag = new EmailSelectDialog(new Observer<String>() {
			
			@Override
			public void update(String model) {
				emailAddress = model;
				buildAndSendEmail(claimsToEmail);
			}
		});
    	
    	emailDiag.show(getFragmentManager(), null);
    }
    
    private void buildAndSendEmail(ArrayList<Claim> claims){
    	StringBuilder body = new StringBuilder();
    	for (Claim c : claims){
    		body.append(c.toEmailString() + "\n");
    		for (Expense e : dataCache.getExpensesForClaim(c)){
    			body.append("\n" + e.toEmailString());
    		}
    		try{
	    		for (String s : Formatter.getTotals(dataCache.getExpensesForClaim(c))){
	    			body.append(s + "\n\n");
	    		}
    		} catch (RuntimeException e){
    			body.append("Total: 0\n\n");
    		}
    	}
    	
    	// This code retrieved from SO http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.setType("message/rfc822");
    	intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailAddress});
    	intent.putExtra(Intent.EXTRA_SUBJECT, "Claim sent from thornhil-TravelExpenses");
    	intent.putExtra(Intent.EXTRA_TEXT   , body.toString());
    	try {
    	    startActivity(Intent.createChooser(intent, "Send mail..."));
    	} catch (android.content.ActivityNotFoundException ex) {
    	    Formatter.showToast(this, "No email clients installed");
    	}
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
			ArrayList<Claim> list = cache.getAllClaims();
			Collections.sort(list);
			return list.get(position);
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
				toDelete.add(getItem(i));
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
	public void dataItemChanged(ArrayList<Integer> item) {
		ArrayList<Claim> out = new ArrayList<Claim>();
		for (Integer i : item){
			out.add(adapter.getItem(i));
		}
		sendEmail(out);
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