package cmput301.thornhil_helpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;

import cmput301.thornhil_dataClasses.Expense;
import android.content.Context;
import android.widget.Toast;

public class Formatter {
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
		return sdf.format(date);
	}
	
	public static void showToast(Context context, String msg){
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static ArrayList<String> getTotals(ArrayList<Expense> expenses){
		Hashtable<String, Float> totals = new Hashtable<String, Float>();
		ArrayList<String> out = new ArrayList<String>();
		for (Expense e : expenses){
			String key = e.getCurrency().toString();
			Float newAmt = e.getAmmount();
			if (totals.containsKey(key)){
				Float amt = totals.get(key);
				amt += newAmt;
				totals.remove(key);
				totals.put(key, amt);
			} else {
				totals.put(key, newAmt);
			}
		}
		
		for (String key : totals.keySet()){
			Float amt = totals.get(key);
			out.add(amt.toString() + " " + key);
		}
		return out;
	}
}
