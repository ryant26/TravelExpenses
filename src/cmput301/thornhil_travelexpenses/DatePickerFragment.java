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

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.widget.DatePicker;
import android.content.DialogInterface.OnDismissListener;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener{
	Date storage;
	
	public DatePickerFragment(Date storage) {
		this.storage = storage;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(storage);
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, monthOfYear, dayOfMonth);
		storage.setTime(cal.getTimeInMillis());
	}
	
	public void onDismiss(android.content.DialogInterface dialog) {
		Activity activity = getActivity();
		if (activity instanceof OnDismissListener){
			((OnDismissListener)activity).onDismiss(dialog);
		}
	};

}
