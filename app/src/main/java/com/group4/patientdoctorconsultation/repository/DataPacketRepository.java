package com.group4.patientdoctorconsultation.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group4.patientdoctorconsultation.common.LiveAdditionListener;
import com.group4.patientdoctorconsultation.common.LiveCompleteListener;
import com.group4.patientdoctorconsultation.common.LiveDocument;
import com.group4.patientdoctorconsultation.common.LiveQuery;
import com.group4.patientdoctorconsultation.common.LiveUploadListener;
import com.group4.patientdoctorconsultation.model.DataPacket;

import java.io.InputStream;

public class DataPacketRepository {

    private static DataPacketRepository instance;
    private final CollectionReference dataPacketCollection;
    private final StorageReference storageReference;

    private DataPacketRepository(FirebaseFirestore firestore, FirebaseStorage firebaseStorage) {
        dataPacketCollection = firestore.collection(DataPacket.COLLECTION_NAME);
        storageReference = firebaseStorage.getReference();
    }

    public LiveQuery<DataPacket> getDataPacketsByPatientId(String patientId) {
        return new LiveQuery<>(
                dataPacketCollection
                        .whereEqualTo(DataPacket.FIELD_PATIENT_ID, patientId)
                , DataPacket.class);
    }

    public LiveDocument<DataPacket> getDataPacketById(String patientId) {
        return new LiveDocument<>(
                dataPacketCollection.document(patientId),
                DataPacket.class
        );
    }

    public LiveAdditionListener addDataPacket(String profileId, String title) {
        LiveAdditionListener liveAdditionListener = new LiveAdditionListener();
        DataPacket dataPacket = new DataPacket();
        dataPacket.setPatientId(profileId);
        dataPacket.setTitle(title);

        dataPacketCollection
                .add(dataPacket)
                .addOnSuccessListener(liveAdditionListener)
                .addOnFailureListener(liveAdditionListener);

        return liveAdditionListener;
    }

    public LiveCompleteListener updateDataPacket(DataPacket dataPacket) {
        LiveCompleteListener liveCompleteListener = new LiveCompleteListener();
        dataPacketCollection.document(dataPacket.getId()).set(dataPacket).addOnCompleteListener(liveCompleteListener);
        return liveCompleteListener;
    }

    public LiveUploadListener uploadAttachment(String packetId, String fileName, InputStream inputStream) {
        StorageReference fileReference = storageReference.child(packetId + "/" + fileName);
        UploadTask uploadTask = fileReference.putStream(inputStream);
        LiveUploadListener uploadListener = new LiveUploadListener();

        uploadTask
            .continueWithTask(task -> {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            })
            .addOnCompleteListener(uploadListener);

        return uploadListener;
    }

    //Singleton instantiation - probably should be thread safe
    public static synchronized DataPacketRepository getInstance(FirebaseFirestore firestore, FirebaseStorage firebaseStorage) {
        if (instance == null) {
            instance = new DataPacketRepository(firestore, firebaseStorage);
        }

        return instance;
    }


}
