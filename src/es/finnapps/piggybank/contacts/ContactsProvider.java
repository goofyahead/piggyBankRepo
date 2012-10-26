package es.finnapps.piggybank.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class ContactsProvider {
    private Context mContext;

    public ContactsProvider(Context context) {
        this.mContext = context;
    }

    public String getContactNameWihtNumber(String number) {
        String displayName = null;
        Long id;

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor phoneLookupCursor = mContext.getContentResolver().query(uri,
                new String[] { PhoneLookup.DISPLAY_NAME, PhoneLookup._ID }, null, null, null);
        int displayNameIndex = phoneLookupCursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
        int idIndex = phoneLookupCursor.getColumnIndex(PhoneLookup._ID);

        if (phoneLookupCursor.moveToFirst()) {
            displayName = phoneLookupCursor.getString(displayNameIndex);
            id = phoneLookupCursor.getLong(idIndex);
        }
        phoneLookupCursor.close();

        if (displayName == null) {
            displayName = number;
        }
        return displayName;
    }
}
