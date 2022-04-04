package com.example.newsapp41.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.newsapp41.App;
import com.example.newsapp41.NewsAdapter;
import com.example.newsapp41.R;
import com.example.newsapp41.databinding.FragmentHomeBinding;
import com.example.newsapp41.databinding.FragmentNewsBinding;
import com.example.newsapp41.models.News;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        News news = (News) bundle.getSerializable("editTask");

        if (news != null) {
            binding.editText.setText(news.getTitle());
        }
        binding.btnSave.setText(bundle.isEmpty() ? "save" : "edit");

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle.isEmpty()) {
                    save();
                } else {
                    update(bundle);
                }
            }
        });

        if (news != null) {
            binding.editText.setText(news.getTitle());
            Log.e("News", "title = " + news.getTitle());
            binding.btnSave.setText("Edit");
        }

    }

    private void update(Bundle bundle) {
        News news = (News) bundle.getSerializable("editTask");
        news.setTitle(binding.editText.getText().toString());
        App.getDatabase().newsDao().update(news);
        close();
    }


    private void save() {

        String text = binding.editText.getText().toString();

        if (text.isEmpty()) {
            Toast.makeText(requireContext(), "Title is empty", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(1)
                    .playOn(binding.editText);
            return;
        }

        News news = new News(text, System.currentTimeMillis());
        App.getDatabase().newsDao().insert(news);
        db.collection("news").add(news)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
        close();

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        binding.btnSave.setText("Save");
        navController.navigateUp();
    }


}