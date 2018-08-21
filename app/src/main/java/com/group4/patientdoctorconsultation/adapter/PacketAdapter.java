package com.group4.patientdoctorconsultation.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.FirestoreResourceAdapter;
import com.group4.patientdoctorconsultation.common.FirestoreResourceClickListener;
import com.group4.patientdoctorconsultation.databinding.ItemDataPacketBinding;
import com.group4.patientdoctorconsultation.model.DataPacket;

public class PacketAdapter extends FirestoreResourceAdapter<DataPacket, ItemDataPacketBinding> {

    public PacketAdapter(FirestoreResourceClickListener<DataPacket> clickListener){
        super(clickListener);
    }

    @Override
    protected ItemDataPacketBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemDataPacketBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_data_packet, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final DataPacket chosen = binding.getDataPacket();
            if (chosen != null) {
                clickListener.onClicked(chosen);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ItemDataPacketBinding binding, DataPacket item) {
        binding.setDataPacket(item);
    }

}
