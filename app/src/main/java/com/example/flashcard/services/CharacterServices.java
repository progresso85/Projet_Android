package com.example.flashcard.services;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.Utils.Utils;
import com.example.flashcard.models.Character;
import com.example.flashcard.models.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CharacterServices {

    private static final String TAG = "MainActivity";

    public static ArrayList<Character> getAllCharacters(Context context, String fileName) {
        String jsonFile = Utils.getJsonFromAssets(context, fileName);
        Gson gson = new Gson();

        Type listQuestionType = new TypeToken<List<Character>>(){}.getType();
        return gson.fromJson(jsonFile, listQuestionType);
    }

    public static ArrayList<Question> getAllQuestionsRandomly(Context context, String fileName) {
        ArrayList<Character> characters = getAllCharacters(context, fileName);
        ArrayList<Integer> indexToFindQuestions = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();
        int nbCharacter = 7;

        Log.i(TAG, "getAllQuestionsRandomly: ");
        for (int i=0; i<nbCharacter; i++){
            ArrayList<Integer> indexList = new ArrayList<>();
            List<String> answers = new ArrayList<>();

            //To collect answer
            for (int o=0; o<4; o++){
                //Get randomCharacter
                int randomRightIndex = ThreadLocalRandom.current().nextInt(0, characters.size()-1);
                while (indexList.contains(randomRightIndex)){
                    randomRightIndex = ThreadLocalRandom.current().nextInt(0, characters.size()-1);
                }
                indexList.add(randomRightIndex);
                //add the character name to the possible answer
                answers.add(characters.get(randomRightIndex).getName());
            }

            //The right character to find
            Log.i(TAG, "indexList: "+ indexList);
            int indexToFind = indexList.get(0);
            for (int id : indexList) {
                if (!indexToFindQuestions.contains(id)){
                    indexToFind = id;
                    indexToFindQuestions.add(id);
                    break;
                }
            }
            //Log.i(TAG, "indexToFi: "+ indexToFind);
            Character rightCharacter = characters.get(indexToFind);

            Log.i(TAG, "indexToFi: " + indexToFind + " - " + rightCharacter.getName());

            //The question object that we need to display
            Question question = new Question("Quel est ce personnage ?", "Guess character", rightCharacter.getImage(), answers, rightCharacter.getName(), 0);
            questions.add(question);
        }
        Log.i(TAG, "indexToFindQuestions: "+ indexToFindQuestions);

        return questions;
    }
}
