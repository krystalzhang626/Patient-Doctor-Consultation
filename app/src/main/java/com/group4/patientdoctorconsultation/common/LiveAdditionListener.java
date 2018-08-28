package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class LiveAdditionListener extends LiveData<FirestoreResource<DocumentReference>>
        implements OnSuccessListener<DocumentReference>, OnFailureListener {

    @Override
    public void onFailure(@NonNull Exception e) {
        setValue(new FirestoreResource<>(e));
    }

    @Override
    public void onSuccess(DocumentReference documentReference) {
        setValue(new FirestoreResource<>(documentReference));
    }
}
