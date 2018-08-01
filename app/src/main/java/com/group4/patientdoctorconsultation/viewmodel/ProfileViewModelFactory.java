package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.group4.patientdoctorconsultation.repository.ProfileRepository;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private ProfileRepository profileRepository;

    public ProfileViewModelFactory(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ProfileViewModel.class)){
            return (T) new ProfileViewModel(profileRepository);
        }else{
            throw new IllegalArgumentException("View Model not found");
        }
    }

}
