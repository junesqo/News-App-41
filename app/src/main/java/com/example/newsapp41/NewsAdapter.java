package com.example.newsapp41;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp41.interfaces.OnItemClickListener;
import com.example.newsapp41.models.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<News> list;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(){
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(list.indexOf(news));
    }

    public void insertItem(int position) {
        list.indexOf(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(list.indexOf(position));
    }

    public void removeItemObj(News news){
        list.remove(news);
        notifyItemRemoved(list.indexOf(news));
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getItemPosition(int position){
        return list.indexOf(position);
    }

    public int getItemObjPosition(News news){
        return list.indexOf(news);
    }

    public News getItem(int position) {
        return list.get(position);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        public NewsViewHolder(@NonNull View itemView) {


            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        public void bind(News news) {
            textTitle.setText(news.getTitle());
        }
    }

}
