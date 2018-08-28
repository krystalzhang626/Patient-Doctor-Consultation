package com.group4.patientdoctorconsultation.common;

import com.group4.patientdoctorconsultation.model.DataPacketItem;

import java.util.Map;

public interface PacketItemResultListener {
    Map<DataPacketItem.DataPacketItemType, String> onResult();
}
