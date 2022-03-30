package com.example.newsapp41;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.newsapp41.models.Board;
import com.example.newsapp41.ui.board.BoardFragment;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ArrayList<Board> list;


    public BoardAdapter() {

        list = new ArrayList<>();
        list.add(new Board("Добро пожаловать", "В этом приложении ты можешь публиковать новости", "first_lottie.json"));
        list.add(new Board("Делись мнением", "Обсуждай новости с другими пользователями", "second_lottie.json"));
        list.add(new Board("Давай начнем", "Нажми на кнопку Start, чтобы начать пользоваться приложением", "third_lottie.json"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private Button btnStart;
        private TextView textDesc;
        private LottieAnimationView lottieView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textTitle = itemView.findViewById(R.id.boardTitle);
            textDesc = itemView.findViewById(R.id.boardDesc);
            lottieView = itemView.findViewById(R.id.lottieView);
            btnStart = itemView.findViewById(R.id.btnStart);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).popBackStack();
                }
            });
        }

        public void bind(int position) {
            Board board = list.get(position);
            textTitle.setText(board.getTitle());
            textDesc.setText(board.getDesc());
            lottieView.setAnimation(board.getLottie());
            if (position == list.size()-1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }
    }
}
