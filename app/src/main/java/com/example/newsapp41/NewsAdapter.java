package com.example.newsapp41;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp41.interfaces.OnItemClickListener;
import com.example.newsapp41.models.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<News> list;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter() {
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
        if (position % 2 == 0) {
            holder.rootView.setBackgroundColor(Color.WHITE);
            holder.textTitle.setTextColor(Color.BLACK);
            holder.date.setTextColor(Color.GRAY);
        } else {
            holder.rootView.setBackgroundColor(Color.BLACK);
            holder.textTitle.setTextColor(Color.WHITE);
            holder.date.setTextColor(Color.WHITE);
        }
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

    public void insertItem(News news, int position) {
        list.set(position, news);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        App.getDatabase().newsDao().delete(getItem(position));
        notifyItemRemoved(list.indexOf(position));
        notifyDataSetChanged();
    }

    public News getItem(int position) {
        return list.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addList(List<News> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void sortAZ(List<News> sortList) {
        this.list = (ArrayList<News>) sortList;
        notifyDataSetChanged();
    }

    public void filterNews(ArrayList<News> filteredNews) {
        this.list = filteredNews;
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rootView;
        private TextView textTitle;
        private TextView date;

        public NewsViewHolder(@NonNull View itemView) {

            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.back);

            textTitle = itemView.findViewById(R.id.newsTitle);
            date = itemView.findViewById(R.id.date_tv);
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
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMMM yyyy ");
            Date resultdate = new Date(news.getCreatedAt());
            date.setText(sdf.format(resultdate));
        }
    }


}
