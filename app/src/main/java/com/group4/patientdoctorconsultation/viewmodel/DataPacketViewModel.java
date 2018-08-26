package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.group4.patientdoctorconsultation.common.FirestoreResource;
import com.group4.patientdoctorconsultation.model.DataPacket;
import com.group4.patientdoctorconsultation.repository.DataPacketRepository;

import java.util.List;

public class DataPacketViewModel extends ViewModel implements FirebaseAuth.AuthStateListener {

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<String> profileId = new MutableLiveData<>();

    private final LiveData<FirestoreResource<List<DataPacket>>> dataPackets;
    private final MutableLiveData<String> activePacketId;
    private final LiveData<FirestoreResource<DataPacket>> activePacket;


    DataPacketViewModel(DataPacketRepository dataPacketRepository, FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        firebaseAuth.addAuthStateListener(this);

        activePacketId = new MutableLiveData<>();
        dataPackets = Transformations.switchMap(profileId, dataPacketRepository::getDataPacketsByPatientId);
        activePacket = Transformations.switchMap(activePacketId, dataPacketRepository::getDataPacketById);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        this.profileId.setValue(
                firebaseAuth.getCurrentUser() == null ? "" : firebaseAuth.getUid()
        );
    }

    @Override
    protected void onCleared() {
        firebaseAuth.removeAuthStateListener(this);
        super.onCleared();
    }

    public void setActivePacketId(String activePacketId){
        this.activePacketId.setValue(activePacketId);
    }

    public LiveData<FirestoreResource<List<DataPacket>>> getDataPackets(){
        return dataPackets;
    }
}
