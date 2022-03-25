package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.flashcard.models.Character;
import com.example.flashcard.models.Question;
import com.example.flashcard.services.CharacterServices;
import com.example.flashcard.services.QuestionsServices;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.quizButtonAnime).setOnClickListener(this);
        findViewById(R.id.quizButtonCharacters).setOnClickListener(this);
        findViewById(R.id.aboutActionButton).setOnClickListener(this);
        findViewById(R.id.listeQuestionButton).setOnClickListener(this);

    }

    /**
     *
     */
    private void selectQuizAndStart(ArrayList arrayList, Class destination) {
        Intent intent = new Intent(HomeActivity.this, destination);


        intent.putExtra("allQuestion", arrayList);
        intent.putExtra("indexCurrentQuestion", 0);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quizButtonAnime:
                Intent intent = new Intent(HomeActivity.this, QuestionDifficultyChioicesActivity.class);
                startActivity(intent);
                break;
            case R.id.quizButtonCharacters:
                ArrayList<Question> questionLength = CharacterServices.getAllQuestionsRandomly(getApplicationContext(), "character.json");
                selectQuizAndStart(questionLength, MainActivity.class);
                break;
            case R.id.aboutActionButton:
                Intent aboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
                aboutIntent.putExtra("aString", "dark_sasuke_xhamster_xnxx");
                aboutIntent.putExtra("description","FlashCard est un jeu à questionnaire sur certain animes.");
                startActivity(aboutIntent);
                break;
            case R.id.listeQuestionButton:
                Intent intent1 = new Intent(HomeActivity.this, ListQuestionActivity.class);
                startActivity(intent1);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.japanese_drum);
        mediaPlayer.start();
    }
}