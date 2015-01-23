package cmput301.thornhil_travelexpenses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.Claim;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_helpers.StorageAdapter;
import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        Cache dataCache = null;
        try{
        dataCache = new StorageAdapter(getApplicationContext()).getCacheFromFile();
        } catch (FileNotFoundException e){
        	Log.d("error", "Cannot initialize Cache!!!!!!!!!");
        	dataCache = new Cache(getApplicationContext());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ClaimAdapter adapter = new ClaimAdapter(this, R.layout.claim_row_layout, dataCache);
        ListView listView = (ListView) findViewById(R.id.Claims_List_View);
        listView.setAdapter(adapter);
        
        listView.setEmptyView((TextView) findViewById(android.R.id.empty));	
        
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	return super.onCreateOptionsMenu(menu);
    };
    
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
				rowView = inflater.inflate(R.layout.claim_row_layout, parent);
			} else {
				rowView = convertView;
			}
			
			return formatView(rowView, item);
		}
		
		public View formatView(View rowView, Claim item){
			((TextView) rowView.findViewById(R.id.Name)).setText(item.getName());
			((TextView) rowView.findViewById(R.id.Status)).setText(item.getStatusString());
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