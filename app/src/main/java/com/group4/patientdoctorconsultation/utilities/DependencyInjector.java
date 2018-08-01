package com.group4.patientdoctorconsultation.utilities;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class DependencyInjector {

    private FirebaseFirestore getFirestore(){
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);

        return firestore;
    }

    // USE THIS PATTERN IF YOU ARE SETTING UP A REPO THAT REQUIRES FIRESTORE
//    private Repository getRepository(){
//        return Repository.getInstance(getFirestore());
//    }

}
