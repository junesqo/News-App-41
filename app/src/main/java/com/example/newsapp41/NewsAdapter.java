package com.example.newsapp41;

import android.annotation.SuppressLint;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> list;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(OnItemClickListener onItemClickListener) {
        list = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(list.get(position));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemLongClick(list.get(position));

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addList(List<News> list) {
        Comparator<News> comparator = Comparator.comparing(News::getCreatedAt);
        this.list=list;
        this.list.sort(comparator);
        Collections.reverse(this.list);
        notifyDataSetChanged();
    }

    public void sortAZ(List<News> sortList) {
        this.list = sortList;
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
        }


        public void bind(News news) {
            textTitle.setText(news.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMMM yyyy ");
            Date resultdate = new Date(news.getCreatedAt());
            date.setText(sdf.format(resultdate));
        }
    }


}
