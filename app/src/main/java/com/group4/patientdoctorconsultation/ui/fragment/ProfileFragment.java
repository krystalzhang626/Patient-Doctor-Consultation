package com.group4.patientdoctorconsultation.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.databinding.FragmentProfileBinding;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModelFactory;

public class ProfileFragment extends Fragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

         oberserveProfile(binding);

         return binding.getRoot();
    }

    private void oberserveProfile(FragmentProfileBinding binding){
        ProfileViewModel viewModel = getViewModel();
        viewModel.getProfile().observe(this, profile -> {
            if(profile == null){
                throw new IllegalStateException("Null result passed from firestoreResource");
            }

            if(profile.isSuccessful()){
                binding.setProfile(profile.getResource());
            }else {
                Log.w(TAG, profile.getError());
                Toast.makeText(
                        requireContext(), profile.getError().getMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private ProfileViewModel getViewModel() {
        ProfileViewModelFactory profileViewModelFactory = DependencyInjector.provideProfileViewModelFactory();

        return ViewModelProviders
                .of(requireActivity(), profileViewModelFactory)
                .get(ProfileViewModel.class);
    }
}
