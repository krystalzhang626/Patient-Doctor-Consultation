package com.group4.patientdoctorconsultation.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.group4.patientdoctorconsultation.model.DataPacketItem;

import java.util.HashMap;
import java.util.Map;

public abstract class PacketItemDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() == null){
            throw new NullPointerException("PacketItemDialog must implement activity");
        }

        if(!(getActivity() instanceof PacketItemResultListener)){
            throw new IllegalStateException(getActivity().toString() + " must extend PacketItemResultListener");
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(getItemType().toString())
                .setView(getDialogView())
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel())
                .setPositiveButton("SAVE", (dialogInterface, i) -> {
                    Map<DataPacketItem.DataPacketItemType, String> result = new HashMap<DataPacketItem.DataPacketItemType, String>();
                    result.put(getItemType(), getDialogViewResult());
                    ((PacketItemResultListener) getActivity()).onResult(result);
                })
                .show();
    }

    abstract View getDialogView();
    abstract String getDialogViewResult();
    abstract DataPacketItem.DataPacketItemType getItemType();
}
