package edu.bu.ollie.imageeffect;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ChangeDialog extends DialogFragment {
    public interface ChangeDialogListener {
        public void onChangeDialogPositiveClick(DialogFragment dialog);
        public void onChangeDialogNegativeClick(DialogFragment dialog);
    }
    ChangeDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ChangeDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("switching image will discard changes");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onChangeDialogPositiveClick(ChangeDialog.this);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onChangeDialogNegativeClick(ChangeDialog.this);
            }
        });
        return builder.create();
    }
}
