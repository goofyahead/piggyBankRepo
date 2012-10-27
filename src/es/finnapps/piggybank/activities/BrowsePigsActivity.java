package es.finnapps.piggybank.activities;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.piggyapi.PiggyApiInterface;
import es.finnapps.piggybank.sharedprefs.PiggyBankPreferences;

public class BrowsePigsActivity extends RoboActivity implements OnItemSelectedListener, OnClickListener {

    @InjectView(R.id.gallery)
    private Gallery mGallery;
    @InjectView(R.id.piggy_name_textview)
    private TextView mPiggyNameTextView;
    @InjectView(R.id.coin1)
    private ImageButton mCoin1;
    @InjectView(R.id.coin2)
    private ImageButton mCoin2;
    @InjectView(R.id.coin20)
    private ImageButton mCoin20;
    @InjectView(R.id.coin50)
    private ImageButton mCoin50;
    @InjectView(R.id.coin100)
    private ImageButton mCoin100;
    @InjectView(R.id.coin200)
    private ImageButton mCoin200;

    @Inject
    private PiggyApiInterface mApi;
    @Inject
    private PiggyBankPreferences mPreferences;
    private ImageView mEmptyPiggyView;
    private List<Piggy> mPiggies;
    private List<View> mPiggyViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_pigs_activity);

        mGallery.setOnItemSelectedListener(this);

        mCoin1.setOnClickListener(this);
        mCoin2.setOnClickListener(this);
        mCoin20.setOnClickListener(this);
        mCoin50.setOnClickListener(this);
        mCoin100.setOnClickListener(this);
        mCoin200.setOnClickListener(this);

        // Empty piggy, for creating new piggy
        mEmptyPiggyView = new ImageView(BrowsePigsActivity.this);
        mEmptyPiggyView.setImageResource(R.drawable.nuevo_piggy);
        mEmptyPiggyView.setOnClickListener(BrowsePigsActivity.this);

      
        initActivity();
    }
    
    @Override
    protected void onRestart() {
        initActivity();
    }
    private void initActivity() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                mPiggies.clear();
                mPiggyViews.clear();
                
                mPiggies = new ArrayList<Piggy>();
                mPiggies.addAll(mApi.getSharedPiggys(mPreferences.getUserPhone()));
                mPiggyViews = new ArrayList<View>();

                for (int i = 0; i < mPiggies.size(); i++) {
                    ImageView view = new ImageView(BrowsePigsActivity.this);
                    view.setImageResource(R.drawable.cerdo_galeria);
                    mPiggyViews.add(view);
                }
                // Add empty piggy
                mPiggyViews.add(mEmptyPiggyView);

                return null;
            }

            protected void onPostExecute(Void result) {
                mGallery.setAdapter(new PiggySpinner());
            };

        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_browse_pigs_activicity, menu);
        return true;
    }

    class PiggySpinner extends BaseAdapter {
        public int getCount() {
            return mPiggyViews.size();
        }

        public Object getItem(int position) {
            return mPiggyViews.get(position);
        }

        public long getItemId(int position) {
            return mPiggyViews.get(position).getId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return mPiggyViews.get(position);
        }

    }

    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        if (view == mEmptyPiggyView){
            mPiggyNameTextView.setText(getString(R.string.new_piggy));
           return; 
        }
        mPiggyNameTextView.setText(mPiggies.get(position).getName());

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void onClick(View v) {
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.coin_click);
        v.startAnimation(scaleAnim);

        if (v == mEmptyPiggyView) {
            scaleAnim.setAnimationListener(new AnimationListener() {
                
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                    
                }
                
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                    
                }
                
                public void onAnimationEnd(Animation animation) {
                    startActivity(new Intent(BrowsePigsActivity.this, CreatePiggyActivity.class));
                }
            });
        }
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            protected void onPostExecute(Void result) {

            };

        }.execute();

    }
}
