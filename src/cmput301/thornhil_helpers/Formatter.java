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
