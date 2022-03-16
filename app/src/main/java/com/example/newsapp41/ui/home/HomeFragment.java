package com.example.newsapp41.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.newsapp41.NewsAdapter;
import com.example.newsapp41.R;
import com.example.newsapp41.databinding.FragmentHomeBinding;
import com.example.newsapp41.interfaces.OnItemClickListener;
import com.example.newsapp41.models.News;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();

    }

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
                open();
            }
        });

//        getParentFragmentManager().setFragmentResultListener("ne", getViewLifecycleOwner(), new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                String newTitle = result.getString("title");
//                index = (int) result.getInt("position");
////                adapter.insertItem(news);
//                if (adapter.getItemPosition(index) != -1){
//                    adapter.getItem(index+1).setTitle(newTitle);
//                }
//            }
//        });

        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                adapter.addItem(news);
                Log.e("Home", "text = " + news.getTitle());
                Log.e("Home", "listener object position = " + adapter.getItemObjPosition(news));
                Log.e("Home", "new object position = " + result.getInt("new position"));
                adapter.getItem(result.getInt("new position")).setTitle(news.getTitle());
//                adapter.removeItem(result.getInt("new position"));
                result.clear();
            }
        });
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                News news = adapter.getItem(position);
//                Toast.makeText(requireContext(), news.getTitle(), Toast.LENGTH_SHORT).show();
                editNews(position);
            }

            @Override
            public void onItemLongClick(int position) {
                deleteNewsDialog(position);
            }
        });
    }

    private void editNews(int position) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", adapter.getItem(position));
        bundle.putInt("position", adapter.getItemObjPosition(adapter.getItem(position)));
//        Log.e("Home", "editing, object position = " + adapter.getItem(position));
        Log.e("Home", "editing, object position position = " + adapter.getItemObjPosition(adapter.getItem(position)));
        navController.navigate(R.id.newsFragment, bundle);
    }

    private void deleteNewsDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Вы уверены что хотите удалить данную запись?");
        builder.setMessage("эээээ");
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.removeItem(position);
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

    private void open() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.newsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}