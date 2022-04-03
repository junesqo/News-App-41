package com.example.newsapp41.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp41.App;
import com.example.newsapp41.NewsAdapter;
import com.example.newsapp41.databinding.FragmentDashboardBinding;
import com.example.newsapp41.interfaces.OnItemClickListener;
import com.example.newsapp41.models.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements OnItemClickListener {

    private FragmentDashboardBinding binding;
    private NewsAdapter adapter;
    private List<News> list;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        list = App.getDatabase().newsDao().getAll();
        db.collection("news").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = task.getResult().toObjects(News.class);
                    Log.e("TAG", String.valueOf(list.size()));
                    adapter.filterNews((ArrayList<News>) list);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        binding.dashboardRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(News position) {

    }

    @Override
    public void onItemLongClick(News position) {

    }
}