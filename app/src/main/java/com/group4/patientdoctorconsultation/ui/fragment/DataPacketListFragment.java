package com.group4.patientdoctorconsultation.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.adapter.PacketAdapter;
import com.group4.patientdoctorconsultation.common.FirestoreResource;
import com.group4.patientdoctorconsultation.ui.NavigationActivity;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;

public class DataPacketListFragment extends Fragment {

    PacketAdapter packetAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_packets, container, false);

        packetAdapter = new PacketAdapter(packet -> Toast.makeText(requireContext(), packet.getPatientId(), Toast.LENGTH_LONG).show());
        RecyclerView packetList = view.findViewById(R.id.data_packet_list);
        packetList.setLayoutManager(new LinearLayoutManager(requireContext()));
        packetList.setAdapter(packetAdapter);

        getViewModel().getDataPackets().observe(this, dataPackets -> {
            if(handleFirestoreResult(dataPackets)){
                packetAdapter.replaceListItems(dataPackets.getResource());
            }
        });

        return view;
    }

    private boolean handleFirestoreResult(FirestoreResource resource){
        if(resource == null || (resource.getResource() == null && resource.getError() == null)){
            throw new IllegalStateException("Null result passed from Firestore Resource");
        }

        if(resource.isSuccessful()){
            return true;
        }else {
            Log.w("TAG", resource.getError());
            Toast.makeText(
                    requireContext(), resource.getError().getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory(
                FirebaseAuth.getInstance().getUid()
        );

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }
}
