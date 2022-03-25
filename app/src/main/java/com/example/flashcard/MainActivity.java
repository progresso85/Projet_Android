package com.example.flashcard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcard.models.Question;
import com.example.flashcard.services.QuestionsServices;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Boolean toPass;
    private int wrongPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent srcIntent = getIntent();

        //setSupportActionBar(findViewById(R.id.my_toolbar));

        ActionBar actionBar = getSupportActionBar();

        //Value to navigate the next question, when a response is false
        toPass = false;

        ArrayList<Question> allQuestion = srcIntent.getParcelableArrayListExtra("allQuestion");
        int index = srcIntent.getIntExtra("indexCurrentQuestion", 0);
        wrongPoint = srcIntent.getIntExtra("wrongPoint", 0);
        int difficulty = srcIntent.getIntExtra("difficulty", 0);
        Question currentQuestion = allQuestion.get(index);
        ArrayList<String> answers = QuestionsServices.getAnswersOfQuestionRandomly(currentQuestion);

        Log.i("MainActivity", "Question : " + currentQuestion.getTitle());
        Log.i("MainActivity", "Index : " + index);

        actionBar.setTitle("Question : "+ index + "/"+ allQuestion.size());
        actionBar.show();

        RadioGroup radio = findViewById(R.id.responseRadioGroup);

        for(int i = 0; i < answers.size(); i++) {
            Log.i("MainActivity", "Response : " + answers.get(i));
            RadioButton rb = new RadioButton(this);
            rb.setId(View.generateViewId());
            rb.setText(answers.get(i));
            radio.addView(rb);
        }

        TextView question = findViewById(R.id.questionTextView);
        ImageView image = findViewById(R.id.questionImageView);
        Button submit = findViewById(R.id.submitButton);
        TextView answer = findViewById(R.id.explainAnswerTextView);
        TextView timerText = findViewById(R.id.timerTextView);
        timerText.setVisibility(View.INVISIBLE);

        /// TIMER DIFFICULTY
        /*if (difficulty > 0) {
            startDifficultyTimer(difficulty, timerText, allQuestion, index);
        }*/

        question.setText(currentQuestion.getTitle());
        Log.i("mainActivity", "onCreate: "+currentQuestion.getImageUrl());
        Picasso.get().load(currentQuestion.getImageUrl()).into(image);

        submit.setEnabled(false);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submit.setEnabled(true);
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Test question
                int selectedRadioButtonId = radio.getCheckedRadioButtonId();
                RadioButton rdBtn = findViewById(selectedRadioButtonId);
                Log.i(TAG, "onClick: "+ selectedRadioButtonId);
                String response = (String) rdBtn.getText();

                String badAnswer = "Mauvaise Réponse";

                Log.i(TAG, "responsetest: "+ response);
                if(QuestionsServices.checkRightAnswer(response, currentQuestion.getRight_answer()) || toPass ){
                    if (toPass){
                        navigetToNextQuestion(allQuestion, index, difficulty);
                    }else{
                        Toast.makeText(MainActivity.this, "Bonne Réponse", Toast.LENGTH_SHORT).show();
                        navigetToNextQuestion(allQuestion, index, difficulty);
                    }
                }else{
                    int count= radio.getChildCount();
                    for (int i = 0; i<count ; i++){
                        RadioButton v = (RadioButton) radio.getChildAt(i);
                        v.setEnabled(false);
                    }

                    answer.setText("La Bonne réponse est : "+currentQuestion.getRight_answer());
                    Toast.makeText(MainActivity.this, "Mauvaise Réponse", Toast.LENGTH_SHORT).show();
                    toPass = true;
                    wrongPoint++;
                    submit.setText("Suivant");

                }
                // navigate to the next question or the result
            }
        });
    }

    private void navigetToNextQuestion(ArrayList<Question> allQuestion, int index, int difficulty) {
        Class navigate;
        int newIndex = 0;
        if(allQuestion.size()-1 > index){
            navigate = MainActivity.class;
            newIndex = index + 1;
        }else{
            navigate = SuccessActivity.class;
        }
        Intent intent = new Intent(MainActivity.this, navigate);
        intent.putExtra("allQuestion", allQuestion);
        intent.putExtra("indexCurrentQuestion", newIndex);
        intent.putExtra("wrongPoint", wrongPoint);
        intent.putExtra("difficulty", difficulty);

        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Arrêter le quiz")
                .setMessage("Êtes-vous sûre de vouloir arrêter le quiz ?")
                .setNegativeButton("Non", null)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    private void startDifficultyTimer(int difficulty, TextView timerText, ArrayList<Question> allQuestion, int index) {

        timerText.setVisibility(View.VISIBLE);
        long time = difficulty == 1 ? 600000 : 30000;

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.timer_counter);
        new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long milliseconds) {
                timerText.setText("Temps restant : " + (milliseconds / 1000) % 60);
                mediaPlayer.start();
            }

            @Override
            public void onFinish() {
                navigetToNextQuestion(allQuestion, index, difficulty);
                wrongPoint++;
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "ENCORE MOI ICI ");
    }
}
