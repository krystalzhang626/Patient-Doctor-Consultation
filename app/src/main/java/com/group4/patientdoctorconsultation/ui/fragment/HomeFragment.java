package com.group4.patientdoctorconsultation.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.adapter.PacketAdapter;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModelFactory;

import androidx.navigation.Navigation;

import androidx.navigation.Navigation;

import androidx.navigation.Navigation;

import androidx.navigation.Navigation;

public class HomeFragment extends FirestoreFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DataPacketViewModel viewModel = getViewModel();

        PacketAdapter packetAdapter = new PacketAdapter(packet -> {
            viewModel.setActivePacketId(packet.getId());
            Navigation.findNavController(view).navigate(R.id.action_home_to_data_packet);
        });
        RecyclerView packetList = view.findViewById(R.id.data_packet_list);
        packetList.setLayoutManager(new LinearLayoutManager(requireContext()));
        packetList.setAdapter(packetAdapter);

        viewModel.getDataPackets().observe(this, dataPackets -> {
            if(handleFirestoreResult(dataPackets)){
                packetAdapter.replaceListItems(dataPackets.getResource());
            }
        });

        return view;
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }

}
