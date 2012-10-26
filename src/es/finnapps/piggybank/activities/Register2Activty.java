package es.finnapps.piggybank.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import es.finnapps.piggybank.R;



public class Register2Activty extends RoboActivity {

	@InjectView(R.id.button1)
	private Button okButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activty);
        
        okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_activty, menu);
        return true;
    }
}
