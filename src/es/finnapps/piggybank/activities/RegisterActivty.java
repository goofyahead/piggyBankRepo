package es.finnapps.piggybank.activities;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.R.layout;
import es.finnapps.piggybank.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RegisterActivty extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register_activty, menu);
        return true;
    }
}
