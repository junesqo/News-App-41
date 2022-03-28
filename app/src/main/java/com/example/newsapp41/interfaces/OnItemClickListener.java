package com.example.newsapp41.interfaces;

import com.example.newsapp41.models.News;

public interface OnItemClickListener {
    void onItemClick(News position);
    void onItemLongClick(News position);
}
