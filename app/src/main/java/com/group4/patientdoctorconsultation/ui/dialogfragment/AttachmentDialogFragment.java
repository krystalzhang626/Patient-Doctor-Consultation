package com.group4.patientdoctorconsultation.ui.dialogfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.model.DataPacketItem;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;

public class AttachmentDialogFragment extends PacketItemDialog {

    private final static int RC_OPEN_FILE = 1;
    private final static String TAG = AttachmentDialogFragment.class.getSimpleName();

    TextView fileName;

    @Override
    public String getDialogResult(){
        if(fileName != null){
            return fileName.getText().toString();
        }else{
            return "";
        }
    }

    @Override
    public View getView(LayoutInflater inflater) {

        openFile();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_attachment, null);
        fileName = view.findViewById(R.id.filename);

        return view;
    }

    @Override
    public DataPacketItem.DataPacketItemType getPacketItemType() {
        return DataPacketItem.DataPacketItemType.DOCUMENT_REFERENCE;
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }

    private void openFile(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        startActivityForResult(intent, RC_OPEN_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_OPEN_FILE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try{
                getViewModel().uploadAttachment(
                        "filename",
                        requireContext().getContentResolver().openInputStream(data.getData())
                );
                fileName.setText("filename");
            } catch (Exception e){
                Log.w(TAG, e.getMessage());
            }
        }
    }
}
