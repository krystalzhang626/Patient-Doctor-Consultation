package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.group4.patientdoctorconsultation.common.FirestoreResource;
import com.group4.patientdoctorconsultation.model.DataPacket;
import com.group4.patientdoctorconsultation.repository.DataPacketRepository;

import java.util.List;

public class DataPacketViewModel extends ViewModel {

    private final LiveData<FirestoreResource<List<DataPacket>>> dataPackets;

    DataPacketViewModel(DataPacketRepository dataPacketRepository, String profileId) {
        dataPackets = dataPacketRepository.getDataPacketsByPatientId(profileId);
    }

    public LiveData<FirestoreResource<List<DataPacket>>> getDataPackets(){
        return dataPackets;
    }
}
