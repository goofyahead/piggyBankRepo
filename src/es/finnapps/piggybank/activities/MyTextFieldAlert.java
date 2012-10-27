package es.finnapps.piggybank.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import es.finnapps.piggybank.R;

public class MyTextFieldAlert extends DialogFragment {

    Runnable positiveAction = null;
    Runnable negativeAction = null;

    public static MyTextFieldAlert newInstance(int title, String text, Runnable positiveAction, Runnable negativeAction) {
        MyTextFieldAlert frag = new MyTextFieldAlert();
        frag.positiveAction = positiveAction;
        frag.negativeAction = negativeAction;
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putString("text", text);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        String text= getArguments().getString("text");
        
        return new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(text).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (positiveAction != null) {
                            positiveAction.run();
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (negativeAction != null) {
                            negativeAction.run();
                        }
                    }
                }).create();
    }
}