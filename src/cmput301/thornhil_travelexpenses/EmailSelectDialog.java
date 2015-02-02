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
	
//	@Override
//	public void show(FragmentManager manager, String tag) {
//		super.show(manager, tag);
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//		ft.add(this, null);
//		ft.addToBackStack(null);
//		ft.commit();
//	}
	
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
