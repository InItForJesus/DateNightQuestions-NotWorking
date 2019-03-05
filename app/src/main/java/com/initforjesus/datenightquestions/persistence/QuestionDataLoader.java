package com.initforjesus.datenightquestions.persistence;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionDataLoader {

    public static Question[] loadInitialData() {
        return new Question[] {
                new Question("Is that loud enough?"),
                new Question("What is that smell?"),
                new Question("Isn't that Beautiful?"),
                new Question("Is that rough or smooth?"),
                new Question("How does that taste?")
        };
    }

    public static Question[] loadInitialDataFromAsset(Context Context) {
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: start");
        List<Question> questions = new ArrayList<Question>();

        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: enter try block");
        try {
            System.out.println("QuestionDataLoader.loadInitialDataFromAsset: get BufferedReader");
            BufferedReader br = new BufferedReader(new InputStreamReader(Context.getAssets().open("test.csv")));
            System.out.println("QuestionDataLoader.loadInitialDataFromAsset: Have reader");
            String line;

            System.out.println("QuestionDataLoader.loadInitialDataFromAsset: Loop through file");
            while ((line = br.readLine()) != null && line.length() > 0) {
                System.out.println("QuestionDataLoader.loadInitialDataFromAsset: line = " + line);
                questions.add(new Question(line));
            }
            System.out.println("QuestionDataLoader.loadInitialDataFromAsset: Done - close file");
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: questions.size = " + questions.size());
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: returning ");
        return (Question[]) questions.toArray();
    }

    public static void loadDataFromFile() {
        // TODO implement loader
        throw new UnsupportedOperationException();
    }
}
