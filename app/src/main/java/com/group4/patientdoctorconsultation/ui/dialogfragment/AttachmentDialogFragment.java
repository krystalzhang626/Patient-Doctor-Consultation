package com.group4.patientdoctorconsultation.ui.dialogfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.model.DataPacketItem;
import com.group4.patientdoctorconsultation.ui.fragment.FirestoreFragment;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;

import java.io.IOException;

public class AttachmentDialogFragment extends PacketItemDialog {

    //TODO - convert this to use data binding

    private final static int RC_OPEN_FILE = 1;
    private final static String TAG = AttachmentDialogFragment.class.getSimpleName();

    TextView fileName;
    String firestoreUri;
    ProgressBar progressBar;
    ImageView imageView;

    @Override
    public String getDialogResult(){
        return firestoreUri == null ? "" : firestoreUri;
    }

    @Override
    public DataPacketItem.DataPacketItemType getPacketItemType() {
        return DataPacketItem.DataPacketItemType.DOCUMENT_REFERENCE;
    }

    @Override
    public View getView(LayoutInflater inflater) {
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.fragment_dialog_attachment, null);

        fileName = view.findViewById(R.id.filename);
        progressBar = view.findViewById(R.id.progress_bar);
        imageView = view.findViewById(R.id.image_preview);

        openFile();

        return view;
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
                ContentResolver contentResolver = requireContext().getContentResolver();
                Uri fileUri = data.getData();

                uploadAttachment(contentResolver, fileUri);
                fileName.setText(getFileName(contentResolver, fileUri));
            } catch (Exception e){
                Log.w(TAG, e.getMessage());
            }
        }
    }

    private void uploadAttachment(ContentResolver contentResolver, Uri fileUri) throws IOException {

        if(!(getTargetFragment() instanceof FirestoreFragment)){
            throw new IllegalStateException("Host fragment must extend FireStoreFragment");
        }

        progressBar.setVisibility(View.VISIBLE);

        getViewModel().uploadAttachment(
                "filename",
                contentResolver.openInputStream(fileUri)
        ).observe(this, uriFirestoreResource -> {
            if(uriFirestoreResource != null &&
                    ((FirestoreFragment)getTargetFragment()).handleFirestoreResult(uriFirestoreResource)){
                firestoreUri = uriFirestoreResource.getResource().toString();

                Glide.with(requireContext())
                    .load(uriFirestoreResource.getResource())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                    .into(imageView);

            }
        });
    }

    private String getFileName(ContentResolver contentResolver, Uri uri){

        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null) {
                int nameIndex = cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DISPLAY_NAME);
                cursor.moveToFirst();
                return cursor.getString(nameIndex);
            }
        }

        throw new NullPointerException("Could not determine file name");
    }

    private DataPacketViewModel getViewModel() {
        DataPacketViewModelFactory dataPacketViewModelFactory = DependencyInjector.provideDataPacketViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), dataPacketViewModelFactory)
                .get(DataPacketViewModel.class);
    }

}
