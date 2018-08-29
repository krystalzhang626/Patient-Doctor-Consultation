package com.group4.patientdoctorconsultation.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.group4.patientdoctorconsultation.model.DataPacketItem;

public abstract class PacketItemDialog extends DialogFragment {

    public static final String EXTRA_RESULT = "extra_result";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getTargetFragment() == null){
            throw new NullPointerException("PacketItemDialog must implement fragment");
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(getPacketItemType().toString())
                .setView(getView(getTargetFragment().getLayoutInflater()))
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel())
                .setPositiveButton("SAVE", (dialogInterface, i) -> {
                    Intent result = new Intent();
                    result.putExtra(EXTRA_RESULT, new DataPacketItem(getPacketItemType(), getDialogResult()));
                    (getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
                })
                .create();
    }

    abstract public String getDialogResult();
    abstract public DataPacketItem.DataPacketItemType getPacketItemType();
    abstract public View getView(LayoutInflater inflater);
}
