package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.flashcard.models.Question;
import com.example.flashcard.services.QuestionsServices;

import java.util.ArrayList;

public class ListQuestionActivity extends AppCompatActivity {

    ListQuestionAdapter adapter;
    RecyclerView recyclerView;
    private ArrayList<Question> questions ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);

        questions = QuestionsServices.getAllQuestions(this, "table.json");

        adapter = new ListQuestionAdapter(questions, new OnItemClickListener() {
            @Override
            public void onItemClick(Question question) {
                Intent intentQuestion = new Intent(ListQuestionActivity.this, MainActivity.class);
                ArrayList<Question> questions = new ArrayList<>();
                questions.add(question);
                intentQuestion.putExtra("allQuestion", questions);
                intentQuestion.putExtra("indexCurrentQuestion", 0);
                startActivity(intentQuestion);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}