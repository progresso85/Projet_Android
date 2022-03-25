package com.example.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.models.Question;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListQuestionAdapter extends
        RecyclerView.Adapter<ListQuestionAdapter.ViewHolder>{

    ArrayList<Question> questions;
    OnItemClickListener onItemClickListener;

    public ListQuestionAdapter(ArrayList<Question> questions, OnItemClickListener onItemClickListener) {
        this.questions = questions;
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final TextView categoryQuestionTextView;
        final TextView nameQuestionTextView;
        final ImageView imageQuestionImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryQuestionTextView = itemView.findViewById(R.id.categoryQuestionTextView);
            nameQuestionTextView = itemView.findViewById(R.id.nameQuestionTextView);
            imageQuestionImageView = itemView.findViewById(R.id.animeImageView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_question, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);

        holder.nameQuestionTextView.setText(question.getTitle());
        holder.categoryQuestionTextView.setText(question.getCategory());
        Picasso.get().load(question.getImageUrl()).into(holder.imageQuestionImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
