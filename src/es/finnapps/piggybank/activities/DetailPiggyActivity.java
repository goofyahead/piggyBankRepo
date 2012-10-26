package es.finnapps.piggybank.activities;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.R.layout;
import es.finnapps.piggybank.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DetailPiggyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_piggy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_piggy, menu);
        return true;
    }
}
