package com.group4.patientdoctorconsultation.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.adapter.PacketAdapter;
import com.group4.patientdoctorconsultation.model.DataPacket;
import com.group4.patientdoctorconsultation.model.DataPacketItem;
import com.group4.patientdoctorconsultation.ui.dialogfragment.TextDialogFragment;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;

import androidx.navigation.Navigation;

public class HomeFragment extends FirestoreFragment
        implements View.OnClickListener {

    private static final int RC_TITLE = 1;
    private static final String TAG = HomeFragment.class.getSimpleName();

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

    @SuppressLint("CommitTransaction")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.new_packet_card) {
            if(getFragmentManager() == null){
                throw new NullPointerException();
            }
            TextDialogFragment textDialogFragment = new TextDialogFragment();
            textDialogFragment.setTargetFragment(this, RC_TITLE);
            textDialogFragment.show(getFragmentManager().beginTransaction(), TAG);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_TITLE && resultCode == Activity.RESULT_OK) {
            try {
                DataPacketItem result = (DataPacketItem) data.getSerializableExtra(TextDialogFragment.EXTRA_RESULT);
                if (result == null) {
                    throw new NullPointerException();
                }

                viewModel
                    .addDataPacket(result.getValue())
                    .observe(HomeFragment.this, additionResult -> {
                        if (additionResult != null && handleFirestoreResult(additionResult)) {
                            openDataPacket(getView(), additionResult.getResource().getId());
                        }
                    });

            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            }
        }
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }
}
