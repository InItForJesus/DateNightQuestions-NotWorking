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
        List<Question> questions = new ArrayList<Question>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Context.getAssets().open("test.csv")));
            String line;

            while ((line = br.readLine()) != null && line.length() > 0) {
                questions.add(new Question(line));
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return (Question[]) questions.toArray();
    }

    public static void loadDataFromFile() {
        // TODO implement loader
        throw new UnsupportedOperationException();
    }
}
