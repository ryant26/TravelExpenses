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
