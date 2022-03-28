package com.example.newsapp41.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.newsapp41.App;
import com.example.newsapp41.NewsAdapter;
import com.example.newsapp41.R;
import com.example.newsapp41.databinding.FragmentHomeBinding;
import com.example.newsapp41.interfaces.OnItemClickListener;
import com.example.newsapp41.models.News;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener{

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private int index;
    private boolean isEditing = false;
    private List<News> list;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter(this);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sort_by_alphabet:
//                item.setTitle("Сортировать по дате");
////                adapter.sortAZ(App.getDatabase().newsDao().getAZ());
//                return false;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //isEditing = false;
                addNews();
                //open(null);
            }
        });

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString());
            }
        });

        list = App.getDatabase().newsDao().getAll();
        adapter.addList(list);
        binding.recyclerView.setAdapter(adapter);




      /*  adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(News position) {
                News news = adapter.getItem(position);
                isEditing = true;
                open(news);
                //HomeFragment.this.index = position;
            }
        });*/

    }

    private void addNews() {
        Bundle bundle = new Bundle();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.newsFragment, bundle);
    }

    private void search(String searchText) {
        ArrayList<News> filteredNews = new ArrayList<>();
        for (News item : App.getDatabase().newsDao().getAll()){
            if (item.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                filteredNews.add(item);
            }
        }
        adapter.filterNews(filteredNews);

    }

    private void deleteNewsDialog(News position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Вы уверены что хотите удалить данную запись?");
        builder.setMessage(position.getTitle());
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getDatabase().newsDao().delete(position);
                list=App.getDatabase().newsDao().getAll();
                adapter.addList(list);
            }
        });
        builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void open(News news) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("editTask", news);
        navController.navigate(R.id.newsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(News position) {
        open(position);
    }

    @Override
    public void onItemLongClick(News position) {
        deleteNewsDialog(position);
    }
}