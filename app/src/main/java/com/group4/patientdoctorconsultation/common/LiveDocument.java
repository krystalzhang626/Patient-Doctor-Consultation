package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class LiveDocument<T> extends LiveData<FirestoreResource<T>> implements EventListener<DocumentSnapshot> {

    private ListenerRegistration listenerRegistration;
    private final DocumentReference documentReference;
    private final Class<T> objectType;

    public LiveDocument(DocumentReference documentReference, Class<T> objectType) {
        this.documentReference = documentReference;
        this.objectType = objectType;
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new FirestoreResource<>(e));
        }else {
            setValue(new FirestoreResource<>(snapshot.toObject(objectType)));
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        listenerRegistration = documentReference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if(listenerRegistration != null){
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }
}
