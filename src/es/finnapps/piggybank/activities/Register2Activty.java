package es.finnapps.piggybank.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.bankapi.BankApiInterface;
import es.finnapps.piggybank.model.UserInfo;
import es.finnapps.piggybank.sharedprefs.PiggyBankPreferences;



public class Register2Activty extends RoboActivity {

	@InjectView(R.id.button1)
	private Button okButton;
	
	@InjectView(R.id.first_name_edit)
	private EditText firstNameEdit;
	@InjectView(R.id.last_name_edit)
	private EditText lastNameEdit;
	@InjectView(R.id.street_edit)
	private EditText streetEdit;
	@InjectView(R.id.street_number_edit)
	private EditText streetNumberEdit;
	@InjectView(R.id.postal_code_edit)
	private EditText postalCodeEdit;
	@InjectView(R.id.city_edit)
	private EditText cityEdit;
	@InjectView(R.id.country_edit)
	private EditText countryEdit;
	
	@Inject
	private BankApiInterface bankApi;
	@Inject
	private PiggyBankPreferences mPreferences;
	
	private UserInfo userInfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2_activty);
        
        okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				UserInfo userInfo = new UserInfo(
						getIntent().getStringExtra(RegisterActivty.EXTRA_USER), 
						getIntent().getStringExtra(RegisterActivty.EXTRA_PASSWORD), 
						firstNameEdit.getText().toString(),
						lastNameEdit.getText().toString(),
						streetEdit.getText().toString(),
						streetNumberEdit.getText().toString(),
						cityEdit.getText().toString(),
						postalCodeEdit.getText().toString(),
						countryEdit.getText().toString());
				
				mPreferences.setUserName(userInfo.getUserName());
				mPreferences.setUserPhoneNumber(userInfo.getNumber());
				
				bankApi.registerClient(userInfo);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_activty, menu);
        return true;
    }
}
