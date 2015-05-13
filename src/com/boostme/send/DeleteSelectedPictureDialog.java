package com.boostme.send;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.boostme.activity.EditQuestionActivity;
import com.boostme.activity.R;

/**
 * User: qii
 * Date: 14-5-31
 */
public class DeleteSelectedPictureDialog extends DialogFragment {

    public static DeleteSelectedPictureDialog newInstance() {
        return new DeleteSelectedPictureDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items = {getString(R.string.delete)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditQuestionActivity activity
                                = (EditQuestionActivity) getActivity();
                        if (activity != null) {
                            activity.deletePicture();
                        }
                    }
                });
        return builder.create();
    }
}
