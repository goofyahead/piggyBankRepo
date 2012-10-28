package es.finnapps.piggybank.activities;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import es.finnapps.piggybank.R;
import es.finnapps.piggybank.bankapi.BankApiInterface;
import es.finnapps.piggybank.contacts.ContactsProvider;
import es.finnapps.piggybank.model.Account;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.piggyapi.PiggyApiInterface;
import es.finnapps.piggybank.sharedprefs.PiggyBankPreferences;

public class CreatePiggyActivity extends RoboActivity implements OnClickListener {
    private static final int PICK_CONTACT_REQUEST_CODE = 1;

    @InjectView(R.id.add_button)
    private Button mAddButton;
    @InjectView(R.id.ok_button)
    private Button mOkButton;
    @InjectView(R.id.membersListView)
    private ListView mMembersListView;
    @InjectView(R.id.goal_edit)
    private EditText mGoalEdit;
    @InjectView(R.id.piggy_name_edit)
    private EditText mNameEditText;

    private MembersAdapter mMembersAdapter;
    private List<String> mMembers;
    private List<View> mMemberViews;

    @Inject
    PiggyBankPreferences mPreferences;
    @Inject
    PiggyApiInterface mPiggyApi;
    @Inject
    BankApiInterface mBankApi;
    @Inject
    ContactsProvider mContactsProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_piggy);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mMembers = new ArrayList<String>();
        mMemberViews = new ArrayList<View>();
        mAddButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
        mMembersAdapter = new MembersAdapter();
        mMembersListView.setAdapter(mMembersAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_piggy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MembersAdapter extends BaseAdapter {

        public int getCount() {
            return mMembers.size();
        }

        public Object getItem(int position) {
            return mMembers.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(CreatePiggyActivity.this);
            textView.setText(mMembers.get(position));
            return textView;
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // If the request went well (OK) and the request was MY_REQUEST_CODE
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_CONTACT_REQUEST_CODE) {
            // Perform a query to the contact's content provider for the
            // contact's name
            final Cursor cursor = getContentResolver().query(data.getData(),
                    new String[] { Contacts.DISPLAY_NAME, Phone.NUMBER }, null, null, null);

            if (cursor.moveToFirst()) { // True if the cursor is not empty
                final int displayNameIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME);

                if (displayNameIndex == -1) {
                    Log.e("CreatePiggyActivity", "No name in query");
                    return;
                }

                final String displayName = cursor.getString(displayNameIndex);

                final int numberIndex = cursor.getColumnIndex(Phone.NUMBER);
                if (numberIndex == -1) {
                    // NUMBER no existe en la consulta
                    Log.e("CreatePiggyActivity", "No number in query");
                    return;
                }
                String s = cursor.getString(numberIndex);
                mMembers.add(s);
                TextView textView = new TextView(CreatePiggyActivity.this);
                textView.setText(s);
                // textView.setText(mContactsProvider.getContactNameWihtNumber(s));
                mMemberViews.add(textView);
                Runnable run = new Runnable() {
                    public void run() {
                        mMembersAdapter.notifyDataSetChanged();
                        mMembersListView.invalidateViews();
                    }
                };
                CreatePiggyActivity.this.runOnUiThread(run);

                Toast.makeText(CreatePiggyActivity.this, mMembers.size() + " contacts selected", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // No contact selected
                Toast.makeText(CreatePiggyActivity.this, "No contact selected", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mAddButton) {
            final Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT_REQUEST_CODE);
        }
        if (v == mOkButton) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    Account newAccount = mBankApi.createAccount(mPreferences.getToken());

                    float amount = Float.parseFloat(mGoalEdit.getText().toString());
                    Piggy piggy = new Piggy(mNameEditText.getText().toString(), newAccount.getAccountId(), amount,
                            null, null, 0, mMembers, newAccount.getAccountBank(), 0);
                    mPiggyApi.createPiggy(piggy, mPreferences.getUserPhone());
                    mPiggyApi.sharePiggyWith(piggy);
                    return null;
                }

                protected void onPostExecute(Void result) {
                    finish();
                };
            }.execute();

        }
    }

}
