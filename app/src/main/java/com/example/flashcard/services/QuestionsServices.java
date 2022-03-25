package com.example.flashcard.services;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.Utils.Utils;
import com.example.flashcard.models.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionsServices {
    private static final String MAIN_ACTIVITY = "MainActivity";

    /**
     * Retrives all Questions from Json File ( or Database )
     *
     * @param context
     * @param fileName
     * @return
     */
    public static ArrayList<Question> getAllQuestions(Context context, String fileName) {
        String jsonFile = Utils.getJsonFromAssets(context, fileName);
        Gson gson = new Gson();

        Type listQuestionType = new TypeToken<List<Question>>(){}.getType();
        return gson.fromJson(jsonFile, listQuestionType);
    }

    /*public static ArrayList<Question> getAllQuestionsByDifficulty(Context context, String fileName){
        return getAllQuestionsByDifficulty(context, fileName, 0);
    }*/

    public static ArrayList<Question> getAllQuestionsByDifficulty(Context context, String fileName, int difficulty){
        ArrayList<Question> questionArrayList = getAllQuestions(context, fileName);
        ArrayList<Question> toReturn = new ArrayList<>();
        for (Question question : questionArrayList) {
            if (question.getDifficulty() == difficulty){
                toReturn.add(question);
            }
        }
        return toReturn;
    }

    /**
     * Retrives all questions from Json File and sort them randomly
     * @param context
     * @param fileName
     */
    public static ArrayList<Question> getAllQuestionsRandomly(Context context, String fileName, int difficulty){
        ArrayList<Question> allQuestions = getAllQuestionsByDifficulty(context, fileName, difficulty);
        ArrayList<Question> allRandomQuestions = new ArrayList<>();

        for (Question question: allQuestions) {
            int max = (allRandomQuestions.size() == 0 ? 1 : allRandomQuestions.size());
            int randomNum = ThreadLocalRandom.current().nextInt(0, max);
            allRandomQuestions.add(randomNum, question);
        }
        return allRandomQuestions;
    }

    /**
     * Get all answers of a specific question
     *
     * @param question
     * @return
     */
    public static ArrayList<String> getAnswersOfQuestionOrdered(Question question) {
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            answers.add(i, question.getAnswers().get(i));
        }
        return answers;
    }

    /**
     * Get all answers of a specific question
     *
     * @param question
     * @return
     */
    public static ArrayList<String> getAnswersOfQuestionRandomly(Question question) {
        ArrayList<String> answers = new ArrayList<String>();
        HashSet<Integer> used = new HashSet<Integer>();

        for (int i = 0; i < question.getAnswers().size(); i++) {
            int random = (int)(Math.random() * question.getAnswers().size());
            while(used.contains(random)) {
                random = (int)(Math.random() * question.getAnswers().size());
            }
            used.add(random);
            answers.add(question.getAnswers().get(random));
        }
        return answers;
    }

    /**
     * Check for the answer
     *
     * @param selectedAnswer
     * @param rightAnswer
     * @return
     */
    public static boolean checkRightAnswer(String selectedAnswer, String rightAnswer) {
        Log.i("MainActivity", "verif 1 "+selectedAnswer+" "+ rightAnswer);
        Log.i("MainActivity", "verif 2 "+selectedAnswer.length());

        if (selectedAnswer.equals(rightAnswer) && selectedAnswer.length() == rightAnswer.length()) {
            Log.i("MainActivity", "Bonne response");
            return true;
        }else {
            Log.i("MainActivity", "Mauvaise response");
            return false;
        }
    }

}
