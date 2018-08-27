package com.group4.patientdoctorconsultation.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.adapter.PacketItemAdapter;
import com.group4.patientdoctorconsultation.common.FirestoreResource;
import com.group4.patientdoctorconsultation.databinding.FragmentDataPacketBinding;
import com.group4.patientdoctorconsultation.model.DataPacket;
import com.group4.patientdoctorconsultation.model.DataPacketItem;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataPacketFragment extends FirestoreFragment {

    PacketItemAdapter packetItemAdapter;
    FragmentDataPacketBinding binding;
    DataPacketViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_packet, container, false);
        viewModel = getViewModel();
        packetItemAdapter = new PacketItemAdapter(packetItem -> {});

        binding.packetItemList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.packetItemList.setAdapter(packetItemAdapter);

        viewModel.getActivePacket().observe(this, activePacket -> {
            if (handleFirestoreResult(activePacket)) {
                updatePacketBinding(activePacket.getResource());
                binding.setDataPacket(activePacket.getResource());
            }
        });

        return binding.getRoot();
    }

    private void updatePacketBinding(DataPacket newPacket) {
        List<DataPacketItem> dataPacketItems = new ArrayList<>();

        if (newPacket.getHeartRate() != null) {
            dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.HEART_RATE, newPacket.getHeartRate()));
        }

        if (newPacket.getDocumentReferences() != null) {
            for (String documentReference : newPacket.getDocumentReferences()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.DOCUMENT_REFERENCE, documentReference));
            }
        }

        if (newPacket.getComments() != null) {
            for (String comment : newPacket.getComments()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.COMMENT, comment));
            }
        }

        if (newPacket.getNotes() != null) {
            for (String note : newPacket.getNotes()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.NOTE, note));
            }
        }

        packetItemAdapter.replaceListItems(dataPacketItems);
    }

    private void updateDataPacket() {
        if (packetItemAdapter == null || packetItemAdapter.getListItems().isEmpty()) {
            return;
        }

        DataPacket dataPacket = binding.getDataPacket();

        List<String> documentReferences = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        List<String> notes = new ArrayList<>();

        for (DataPacketItem dataPacketItem : packetItemAdapter.getListItems()) {
            switch (dataPacketItem.getDataPacketItemType()) {
                case HEART_RATE:
                    dataPacket.setHeartRate(dataPacketItem.getValue());
                    break;
                case DOCUMENT_REFERENCE:
                    documentReferences.add(dataPacketItem.getValue());
                    break;
                case COMMENT:
                    comments.add(dataPacketItem.getValue());
                    break;
                case NOTE:
                    notes.add(dataPacketItem.getValue());
                    break;
            }
        }

        dataPacket.setDocumentReferences(documentReferences);
        dataPacket.setComments(comments);
        dataPacket.setNotes(notes);

        viewModel.updateDataPacket(dataPacket);
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }

}
