package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LiveCompleteListener extends LiveData<FirestoreResource<Boolean>> implements OnCompleteListener {

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            setValue(new FirestoreResource<>(true));
        } else {
            setValue(new FirestoreResource<>(task.getException()));
        }
    }
}
