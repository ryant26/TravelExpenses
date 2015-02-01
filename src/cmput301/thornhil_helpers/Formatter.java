package cmput301.thornhil_helpers;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

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
}
