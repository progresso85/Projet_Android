package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flashcard.models.Question;
import com.example.flashcard.services.QuestionsServices;

import java.util.ArrayList;

public class QuestionDifficultyChioicesActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_difficulty_chioices);

        findViewById(R.id.difficultyBeginner).setOnClickListener(this);
        findViewById(R.id.difficultyAdvanced).setOnClickListener(this);
        findViewById(R.id.difficultyExpert).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.difficultyBeginner:
                chooseDifficultyAndStart(0);
                break;
            case R.id.difficultyAdvanced:
                chooseDifficultyAndStart(1);
                break;
            case R.id.difficultyExpert:
                chooseDifficultyAndStart(2);
                break;
        }
    }

    private void chooseDifficultyAndStart(int difficulty) {
        Intent intent = new Intent(QuestionDifficultyChioicesActivity.this, MainActivity.class);
        ArrayList<Question> questions = QuestionsServices.getAllQuestionsRandomly(getApplicationContext(), "table.json", difficulty);
        intent.putExtra("allQuestion", questions);
        intent.putExtra("indexCurrentQuestion", 0);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}