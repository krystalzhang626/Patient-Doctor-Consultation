package com.group4.patientdoctorconsultation.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group4.patientdoctorconsultation.common.LiveAdditionListener;
import com.group4.patientdoctorconsultation.common.LiveCompleteListener;
import com.group4.patientdoctorconsultation.common.LiveDocument;
import com.group4.patientdoctorconsultation.common.LiveQuery;
import com.group4.patientdoctorconsultation.model.DataPacket;

public class DataPacketRepository {

    private static DataPacketRepository instance;
    private final CollectionReference dataPacketCollection;

    private DataPacketRepository(FirebaseFirestore firestore) {
        dataPacketCollection = firestore.collection(DataPacket.COLLECTION_NAME);
    }

    public LiveQuery<DataPacket> getDataPacketsByPatientId(String patientId){
        return new LiveQuery<>(
                dataPacketCollection
                        .whereEqualTo(DataPacket.FIELD_PATIENT_ID, patientId)
                , DataPacket.class);
    }

    public LiveDocument<DataPacket> getDataPacketById(String patientId){
        return new LiveDocument<>(
                dataPacketCollection.document(patientId),
                DataPacket.class
        );
    }

    public LiveAdditionListener addDataPacket(String profileId, String title){
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

    public LiveCompleteListener updateDataPacket(DataPacket dataPacket){
        LiveCompleteListener liveCompleteListener = new LiveCompleteListener();
        dataPacketCollection.document(dataPacket.getId()).set(dataPacket).addOnCompleteListener(liveCompleteListener);
        return liveCompleteListener;
    }

    //Singleton instantiation - probably should be thread safe
    public static synchronized DataPacketRepository getInstance(FirebaseFirestore firestore){
        if(instance == null){
            instance = new DataPacketRepository(firestore);
        }

        return instance;
    }


}
