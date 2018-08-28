package com.group4.patientdoctorconsultation.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.adapter.PacketAdapter;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class HomeFragment extends FirestoreFragment implements View.OnClickListener {

    private DataPacketViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewModel = getViewModel();

        PacketAdapter packetAdapter = new PacketAdapter(packet -> openDataPacket(view, packet.getId()));
        RecyclerView packetList = view.findViewById(R.id.data_packet_list);
        packetList.setLayoutManager(new LinearLayoutManager(requireContext()));
        packetList.setAdapter(packetAdapter);

        viewModel.getDataPackets().observe(this, dataPackets -> {
            if (dataPackets != null && handleFirestoreResult(dataPackets)) {
                packetAdapter.replaceListItems(dataPackets.getResource());
            }
        });

        view.findViewById(R.id.new_packet_card).setOnClickListener(this);

        return view;
    }

    private void openDataPacket(View view, String packetId) {
        viewModel.setActivePacketId(packetId);
        Navigation.findNavController(view).navigate(R.id.action_home_to_data_packet);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.new_packet_card){
            final EditText input = new EditText(requireContext());
            input.setHint("Title");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
            alertDialog
                    .setTitle("New Data Packet")
                    .setMessage("")
                    .setView(input)
                    .setPositiveButton("OK",
                            (dialog, which) ->
                                    viewModel
                                            .addDataPacket(input.getText().toString())
                                            .observe(HomeFragment.this, additionResult -> {
                                                if (additionResult != null && handleFirestoreResult(additionResult)) {
                                                    openDataPacket(view, additionResult.getResource().getId());
                                                }
                                            }))
                    .setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel())
                    .show();
        }
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }
}
