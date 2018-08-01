package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class NavigationViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(NavigationViewModel.class)){
            return (T) new NavigationViewModel();
        }else{
            throw new IllegalArgumentException("View Model not found");
        }
    }

}
