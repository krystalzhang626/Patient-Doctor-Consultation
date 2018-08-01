package com.group4.patientdoctorconsultation.utilities;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.group4.patientdoctorconsultation.repository.ProfileRepository;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModelFactory;

public class DependencyInjector {

    private static FirebaseFirestore getFirestore() {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);

        return firestore;
    }

    private static ProfileRepository getProfileRepository() {
        return ProfileRepository.getInstance(getFirestore());
    }

    public static ProfileViewModelFactory provideProfileViewModelFactory(){
        return new ProfileViewModelFactory(getProfileRepository());
    }

}
