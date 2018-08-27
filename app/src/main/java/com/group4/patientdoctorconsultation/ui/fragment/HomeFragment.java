package com.group4.patientdoctorconsultation.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

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

        view.findViewById(R.id.new_packet_card).setOnClickListener(cardView -> {
            final EditText input = new EditText(requireContext());
            input.setHint("Title");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
            alertDialog
                    .setTitle("New Data Packet")
                    .setMessage("")
                    .setView(input)
                    .setPositiveButton("OK",
                            (dialog, which) ->
                                    viewModel.addDataPacket(input.getText().toString()).observe(HomeFragment.this, additionResult -> {
                                        if (handleFirestoreResult(additionResult)) {
                                            viewModel.setActivePacketId(additionResult.getResource().getId());
                                            Navigation.findNavController(view).navigate(R.id.action_home_to_data_packet);
                                        }
                                    }))
                    .setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel())
                    .show();
        });

        RecyclerView packetList = view.findViewById(R.id.data_packet_list);
        packetList.setLayoutManager(new LinearLayoutManager(requireContext()));
        packetList.setAdapter(packetAdapter);

        viewModel.getDataPackets().observe(this, dataPackets -> {
            if (handleFirestoreResult(dataPackets)) {
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
