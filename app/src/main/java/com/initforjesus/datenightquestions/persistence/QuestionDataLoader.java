package com.initforjesus.datenightquestions.persistence;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Context.getAssets().open("initialdata.txt")));
            String line;

            while ((line = br.readLine()) != null && line.length() > 0) {
                questions.add(new Question(line));
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: questions.size = " + questions.size());

        Question[] questionsArray = new Question[questions.size()];
        questionsArray = questions.toArray(questionsArray);
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: questionsArray.size = " + questionsArray.length);
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: last question = " + questionsArray[questions.size()-1].getQuestion());
        System.out.println("QuestionDataLoader.loadInitialDataFromAsset: returning ");
        return questionsArray;
//        return (Question[]) questions.toArray();
    }

    public static void loadDataFromFile() {
        // TODO implement loader
        throw new UnsupportedOperationException();
    }

    public static void loadInitialDataFromJsonAsset(Context context){

        String jsondata = null;
        try {
            InputStream is = context.getAssets().open("initialdata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsondata = new String(buffer, "UTF-8");
        }
        catch (Exception e)
        {
            System.out.println(e);
            // TODO notify user of error
        }

        System.out.println(jsondata);
        if (jsondata != null) {
            loadDataFromJsonString(jsondata);
        }


    }

    public static void loadDataFromJsonString(String jsonData) {
        System.out.println("loadDataFromJson - Start");

        //can throw com.google.gson.stream.MalformedJsonException
        JsonDataModel jsonDataModel = new Gson().fromJson( jsonData, JsonDataModel.class);
        if (jsonDataModel != null && jsonDataModel.sources != null) {
            for (JsonDataModel.SourceData sourceData: jsonDataModel.sources){
                System.out.println("SourceID = " + sourceData.sourceID);
                if (sourceData.catagories != null) {
                    System.out.println("Catagory count = " + sourceData.catagories.size());

                    for (JsonDataModel.CatagoryData catagory : sourceData.catagories) {
                        System.out.println("Catagory = " + catagory.catagory);
                        if (catagory.questions != null) {
                            System.out.println("question count = " + catagory.questions.size());
                            for (String question : catagory.questions) {
                                System.out.println("question = " + question);
                            }
                        }
                    }
                }
            }
        }
        else {
            System.out.println("loadDataFromJson - jsonDataModel == " + jsonDataModel);
        }

        System.out.println("loadDataFromJson - Done");
    }
}
