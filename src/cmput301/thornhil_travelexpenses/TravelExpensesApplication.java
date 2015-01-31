package cmput301.thornhil_travelexpenses;

import cmput301.thornhil_dataClasses.Cache;
import android.app.Application;
import android.content.Context;

public class TravelExpensesApplication extends Application {
	private static Cache cache;
	private static Context context;
	
	@Override
	public void onCreate() {
		context = getApplicationContext();
		super.onCreate();
	}
	
	public static Cache getCache(){
		if (cache == null){
			cache = new Cache(context);
		}
		return cache;
	}
}
