package com.group4.patientdoctorconsultation.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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

    public static synchronized DataPacketRepository getInstance(FirebaseFirestore firestore){
        if(instance == null){
            instance = new DataPacketRepository(firestore);
        }

        return instance;
    }


}
