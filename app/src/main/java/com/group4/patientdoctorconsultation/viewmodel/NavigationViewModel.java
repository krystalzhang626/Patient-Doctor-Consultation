package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class NavigationViewModel extends ViewModel {

    private LiveData<Boolean> isSignedIn = new LiveData<Boolean>(){
        @Override
        protected void onActive() {
            super.onActive();
            setValue(FirebaseAuth.getInstance().getCurrentUser() != null);
        }
    };

    public LiveData<Boolean> getIsSignedIn() {
        return isSignedIn;
    }

}
