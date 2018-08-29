package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LiveUploadListener extends LiveData<FirestoreResource<Uri>>
        implements OnCompleteListener<Uri> {

    @Override
    public void onComplete(@NonNull Task<Uri> task) {
        if (task.isSuccessful()) {
            setValue(new FirestoreResource<>(task.getResult()));
        } else {
            setValue(new FirestoreResource<>(task.getException()));
        }
    }
}
