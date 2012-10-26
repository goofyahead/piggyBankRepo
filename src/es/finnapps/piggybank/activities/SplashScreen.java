package es.finnapps.piggybank.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.inject.Inject;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.sharedprefs.PiggyBankPreferences;

public class SplashScreen extends RoboActivity implements OnClickListener {
	@InjectView(R.id.imageView1)
	private ImageView splashImageView;
	
	@Inject
	private PiggyBankPreferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getActionBar().hide();
        
        splashImageView.setOnClickListener(this);
    }

	public void onClick(View v) {
		//if (mPreferences.isUserRegistered()){
	    if (false){
			// Go to main screen
			startActivity(new Intent(this,BrowsePigsActivity.class));
		}
		else {
			// Go to Register screen
			startActivity(new Intent(this,RegisterActivty.class));
		}
		finish();
	}
}
