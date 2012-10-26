package es.finnapps.piggybank.activities;

import es.finnapps.piggybank.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BrowsePigsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_pigs_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_browse_pigs_activicity, menu);
        return true;
    }
}
