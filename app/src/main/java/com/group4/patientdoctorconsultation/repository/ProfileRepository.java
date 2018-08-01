package com.group4.patientdoctorconsultation.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group4.patientdoctorconsultation.common.LiveCompleteListener;
import com.group4.patientdoctorconsultation.common.LiveDocument;
import com.group4.patientdoctorconsultation.model.Profile;

public class ProfileRepository {

    private static ProfileRepository instance;
    private final CollectionReference profileCollection;

    private ProfileRepository(FirebaseFirestore firestore) {
        this.profileCollection = firestore.collection(Profile.COLLECTION_NAME);
    }

    public LiveDocument<Profile> profileFromUserId(String userId){
        return new LiveDocument<>(
                profileCollection.document(userId),
                Profile.class
        );
    }

    public LiveCompleteListener updateProfile(String userId, Profile profile){
        LiveCompleteListener liveCompleteListener = new LiveCompleteListener();
        profileCollection.document(userId).set(profile).addOnCompleteListener(liveCompleteListener);
        return liveCompleteListener;
    }

    public static synchronized ProfileRepository getInstance(FirebaseFirestore firestore){
        if(instance == null){
            instance = new ProfileRepository(firestore);
        }

        return instance;
    }


}
