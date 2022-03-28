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

import com.example.newsapp41.App;
import com.example.newsapp41.NewsAdapter;
import com.example.newsapp41.R;
import com.example.newsapp41.databinding.FragmentHomeBinding;
import com.example.newsapp41.databinding.FragmentNewsBinding;
import com.example.newsapp41.models.News;


public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private Integer index = 0;
    private boolean isEditing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // adapter = new NewsAdapter(this);
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

        if (news!=null) {
            binding.editText.setText(news.getTitle());
        }
        binding.btnSave.setText(bundle.isEmpty() ? "save" : "edit");

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle.isEmpty()){
                    save();
                }else{
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
            return;
        }

        News news = new News(text, System.currentTimeMillis());
        App.getDatabase().newsDao().insert(news);
        Log.e("News", "text setted = " + text);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        binding.btnSave.setText("Save");
        navController.navigateUp();
    }


}