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
import com.group4.patientdoctorconsultation.common.FirestoreResource;
import com.group4.patientdoctorconsultation.databinding.FragmentProfileBinding;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModelFactory;

public class ProfileFragment extends FirestoreFragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    ProfileViewModel viewModel;
    FragmentProfileBinding binding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
         binding.setProfileHandler(this);
         observeProfile();
         return binding.getRoot();
    }

    private void observeProfile(){
        viewModel = getViewModel();
        viewModel.getProfile().observe(this, profile -> {
            if(handleFirestoreResult(profile)){
                binding.setProfile(profile.getResource());
            }
        });
    }

    public void submit(View view){ //Do not remove parameter, required for data binding
        viewModel.updateProfile(binding.getProfile()).observe(this, isComplete -> {
            if(handleFirestoreResult(isComplete)){
                Toast.makeText(requireContext(), "Saved",Toast.LENGTH_LONG).show();
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
