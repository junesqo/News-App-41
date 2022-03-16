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

import com.example.newsapp41.NewsAdapter;
import com.example.newsapp41.R;
import com.example.newsapp41.databinding.FragmentHomeBinding;
import com.example.newsapp41.databinding.FragmentNewsBinding;
import com.example.newsapp41.models.News;


public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private NewsAdapter adapter;
    private Integer index = 0;
    private boolean isEditing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isEditing = true;
            index = bundle.getInt("position");
//            Integer position = bundle.getInt("pos");
//            News news = adapter.getItem(position);
//            binding.editText.setText(news.getTitle());
            News news = (News) bundle.getSerializable("post");
            binding.editText.setText(news.getTitle());
            Log.e("News", "text = " + news.getTitle());
            Log.e("News", "index = " + index);
            Log.e("News", "bundle = " + isEditing);
            binding.btnSave.setText("Edit");
//            binding.btnSave.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Editable nextText = binding.editText.getText();
//                    news.setTitle(nextText.toString());
//                    close();
//                }
//            });

        }
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.btnSave.getText() == "Edit") {
//                    edit((News) bundle.getSerializable("post"));
                    save();
                } else {
                    save();
                }
            }
        });
    }

//    private void edit(News news) {
//        String text = binding.editText.getText().toString();
//        Bundle bundle = new Bundle();
//        bundle.putString("title", text);
//        adapter.removeItemObj(news);
//        close();
//    }

    private void save() {
        String text = binding.editText.getText().toString();
//        if (isEditing){
//            News news = new News(text, System.currentTimeMillis());
//            Bundle bundle = new Bundle();
//            bundle.putString("title", text);
//            bundle.putInt("position", index);
//            getParentFragmentManager().setFragmentResult("ne", bundle);
//        }
//        else {
//            News news = new News(text, System.currentTimeMillis());
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("news", news);
//            getParentFragmentManager().setFragmentResult("rk_news", bundle);
//        }
        News news = new News(text, System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        bundle.putInt("new position", index);
        getParentFragmentManager().setFragmentResult("rk_news", bundle);
        Log.e("News", "bundle = " + isEditing);
        Log.e("News", "text = " + text);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        isEditing = false;
        binding.btnSave.setText("Save");
        navController.navigateUp();
    }


}