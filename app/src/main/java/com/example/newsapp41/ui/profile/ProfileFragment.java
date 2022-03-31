package com.example.newsapp41.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.newsapp41.Prefs;
import com.example.newsapp41.R;
import com.example.newsapp41.ShotsAdapter;
import com.example.newsapp41.databinding.FragmentProfileBinding;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private ArrayList<Integer> photos;

    private FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Prefs prefs = new Prefs(requireContext());
        binding.profileEditText.setText(prefs.getProfileEditText());
        if (prefs.getImageUri()!=null){
            Glide.with(binding.imageView).load(prefs.getImageUri()).into(binding.imageView);
        }
        binding.imageView.setOnClickListener(view1 -> mGetContent.launch("image/*"));

        binding.profileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                prefs.saveProfileEditText(binding.profileEditText.getText().toString());
            }
        });
        loadData();
        ShotsAdapter shotsAdapter = new ShotsAdapter(photos);
        binding.profileShotsRecycler.setAdapter(shotsAdapter);
        binding.profileShotsRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    private void loadData() {
        photos = new ArrayList<Integer>();
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
        photos.add(R.drawable.image);
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            Prefs prefs = new Prefs(requireContext());
            if (result != null) {
                Glide.with(binding.imageView).load(result).circleCrop().into(binding.imageView);
                prefs.saveImageUri(String.valueOf(result));
            }
        }
    });

}