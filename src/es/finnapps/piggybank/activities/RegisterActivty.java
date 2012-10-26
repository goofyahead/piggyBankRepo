package es.finnapps.piggybank.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import es.finnapps.piggybank.R;



public class RegisterActivty extends RoboActivity {
	public static final String EXTRA_USER = "USER";
	public static final String EXTRA_PHONE = "PHONE";
	public static final String EXTRA_PASSWORD = "PASSWORD";

	@InjectView(R.id.button1)
	private Button okButton;
	
	@InjectView(R.id.username_edit)
	private EditText userNameEdit;
	@InjectView(R.id.phonenumber_edit)
	private EditText phoneNumberEdit;
	@InjectView(R.id.password_edit)
	private EditText passwordEdit;
	@InjectView(R.id.confirm_password_edit)
	private EditText confirmPasswordEdit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activty);
        
        okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivty.this, Register2Activty.class);
				// Add extras with form data.
				intent.putExtra(EXTRA_USER, userNameEdit.getText().toString());
				intent.putExtra(EXTRA_PHONE, phoneNumberEdit.getText().toString());
				intent.putExtra(EXTRA_PASSWORD, passwordEdit.getText().toString());
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_activty, menu);
        return true;
    }
}
