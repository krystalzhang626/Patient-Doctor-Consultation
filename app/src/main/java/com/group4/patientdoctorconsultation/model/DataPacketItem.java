package com.group4.patientdoctorconsultation.model;

import android.graphics.drawable.Drawable;

import com.group4.patientdoctorconsultation.R;

public class DataPacketItem {
    public enum DataPacketItemType{
        HEART_RATE,
        DOCUMENT_REFERENCE,
        LOCATION,
        COMMENT,
        NOTE
    }

    private DataPacketItemType dataPacketItemType;
    private int iconResourceId;
    private String value;

    public DataPacketItem(DataPacketItemType dataPacketItemType, String value) {
        this.dataPacketItemType = dataPacketItemType;
        this.value = value;
        setIconResourceId();
    }

    public DataPacketItemType getDataPacketItemType() {
        return dataPacketItemType;
    }

    public void setDataPacketItemType(DataPacketItemType dataPacketItemType) {
        this.dataPacketItemType = dataPacketItemType;
        setIconResourceId();
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    private void setIconResourceId() {
        switch(dataPacketItemType){
            case HEART_RATE:
                iconResourceId = R.drawable.ic_favorite_white_24dp;
                break;
            case DOCUMENT_REFERENCE:
                iconResourceId = R.drawable.ic_attach_file_white_24dp;
                break;
            case LOCATION:
                iconResourceId = R.drawable.ic_location_on_white_24dp;
                break;
            case COMMENT:
                iconResourceId = R.drawable.ic_text_fields_white_24dp;
                break;
            case NOTE:
                iconResourceId = R.drawable.ic_text_fields_white_24dp;
                break;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
