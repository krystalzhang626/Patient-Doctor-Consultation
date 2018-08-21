package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.group4.patientdoctorconsultation.repository.DataPacketRepository;


public class DataPacketViewModelFactory implements ViewModelProvider.Factory {

    private DataPacketRepository dataPacketRepository;
    private String profileId;

    public DataPacketViewModelFactory(DataPacketRepository dataPacketRepository, String profileId) {
        this.dataPacketRepository = dataPacketRepository;
        this.profileId = profileId;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(DataPacketViewModel.class)){
            return (T) new DataPacketViewModel(dataPacketRepository, profileId);
        }else{
            throw new IllegalArgumentException("View Model not found");
        }
    }

}
