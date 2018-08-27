package com.group4.patientdoctorconsultation.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.group4.patientdoctorconsultation.repository.DataPacketRepository;
import com.group4.patientdoctorconsultation.repository.ProfileRepository;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;
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

    private static FirebaseAuth getFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    private static ProfileRepository getProfileRepository() {
        return ProfileRepository.getInstance(getFirestore());
    }

    private static DataPacketRepository getDataPacketRepository(){
        return DataPacketRepository.getInstance(getFirestore());
    }

    public static ProfileViewModelFactory provideProfileViewModelFactory(){
        return new ProfileViewModelFactory(getProfileRepository(), getFirebaseAuth());
    }

    public static DataPacketViewModelFactory provideDataPacketViewModelFactory(){
        return new DataPacketViewModelFactory(getDataPacketRepository(), getFirebaseAuth());
    }

}
