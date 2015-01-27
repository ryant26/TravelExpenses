package cmput301.thornhil_helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
		return sdf.format(date);
	}
}
