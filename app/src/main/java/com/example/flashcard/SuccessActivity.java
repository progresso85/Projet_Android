package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.flashcard.models.Question;
import com.example.flashcard.services.QuestionsServices;

import java.util.ArrayList;

public class SuccessActivity extends AppCompatActivity {

    public static final String TAG = "SuccessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent srcIntent = getIntent();
        int wrongPoint = srcIntent.getIntExtra("wrongPoint", 0);
        ArrayList<Question> allQuestion = srcIntent.getParcelableArrayListExtra("allQuestion");

        TextView result = findViewById(R.id.totalAnswersTextView);
        TextView percentageResult = findViewById(R.id.percentageSuccesTextView);

        float numberResult = allQuestion.size()-wrongPoint;
        float percentageR = (numberResult/allQuestion.size())*100;

        percentageResult.setText("Réussite : "+percentageR+"%");
        result.setText((int)numberResult + "/" + allQuestion.size());

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.retryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
                int difficulty= 0;
                ArrayList<Question> questionLength = QuestionsServices.getAllQuestionsRandomly(getApplicationContext(),"table.json", difficulty);

                intent.putExtra("allQuestion", questionLength);
                intent.putExtra("indexCurrentQuestion", 0);
                startActivity(intent);
                finish();
            }
        });
    }
}