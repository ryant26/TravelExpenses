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

import cmput301.thornhil_helpers.Formatter;
import cmput301.thornhil_helpers.Observer;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EmailSelectDialog extends DialogFragment {
	
	private String email;
	private Observer<String> observer;
	private Button sendButton;
	private EditText emailEditor;
	
	public EmailSelectDialog(Observer<String> o){
		observer = o;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.email_dialog, container, false);
		sendButton = (Button) v.findViewById(R.id.send_mail_button);
		emailEditor = (EditText) v.findViewById(R.id.edit_email);
		setUpButton();
		return v;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	private void setUpButton(){
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (( email = emailEditor.getText().toString()) != null){
					
					observer.update(email);
					dismiss();
				}
				else {
					Formatter.showToast(getActivity(), "You must enter an email");
				}
			}
		});
	}
}
