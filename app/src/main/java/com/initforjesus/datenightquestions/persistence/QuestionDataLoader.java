package com.initforjesus.datenightquestions.persistence;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.List;

public class QuestionDataLoader {

/*    public static Question[] loadInitialData() {
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
*/
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
        catch (Exception e) {
            System.out.println(e);
            // TODO notify user of error
        }

        //System.out.println(jsondata);
        if (jsondata != null) {
            loadDataFromJsonString(jsondata, context);
        }


    }

    public static void loadDataFromJsonString(String jsonData, Context context) {
        System.out.println("loadDataFromJson - Start");

        JsonDataModel jsonDataModel = null;
        try {
            //can throw com.google.gson.stream.MalformedJsonException
            jsonDataModel = new Gson().fromJson( jsonData, JsonDataModel.class);
        }
        catch (Exception e) {
            System.out.println(e);
            // TODO notify user of error
        }

        if (jsonDataModel != null) {
            if (jsonDataModel.sourceConfigs != null) {
                processSourceConfigs(jsonDataModel.sourceConfigs, context);
            }
            if (jsonDataModel.catagoryConfigs != null) {
                processCategoryConfigs(jsonDataModel.catagoryConfigs, context);
            }
            if (jsonDataModel.sources != null) {
                processData(jsonDataModel.sources, context);
            }
        }
/*
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
        */
        else {
            System.out.println("loadDataFromJson - NO DATA - jsonDataModel == " + jsonDataModel);
        }

        System.out.println("loadDataFromJson - Done");
    }

    private static void processSourceConfigs(List<JsonDataModel.SourceConfig> sourceConfigs, Context context) {
        System.out.println("processSourceConfigs - Start");
        for (JsonDataModel.SourceConfig sourceConfig: sourceConfigs ) {
            if (sourceConfig.sourceID != null) {
                //Source[] sourceArray = new Source[1];
                Source source = new Source(sourceConfig.sourceID, sourceConfig.sourceName, sourceConfig.sourceURL);
                if (sourceConfig.sourceName == null) {
                    source.setSourceName(sourceConfig.sourceID);
                }
                try {
                    //sourceArray[0] = source;
                    QuestionDatabase.getDatabase(context).sourceDao().insertAll(source);
                }
                catch (Exception e) {
                    // TODO notify user of error
                    System.out.println(e);
                }
            }
            else {
                //TODO notify user
                System.out.println("SourceID cannot be NULL");
            }
        }
        System.out.println("processSourceConfigs - Done");
    }

    private static void processCategoryConfigs(List<String> categoryConfigs, Context context) {
        System.out.println("processCategoryConfigs - Start");

        for (String categoryID: categoryConfigs) {
            if (categoryID != null) {
                Category category = new Category(categoryID);
                try {
                    //sourceArray[0] = source;
                    QuestionDatabase.getDatabase(context).categoryDao().insertAll(category);
                }
                catch (Exception e) {
                    // TODO notify user of error
                    System.out.println(e);
                }
            }
            else {
                //TODO notify user
                System.out.println("category cannot be NULL");
            }

        }

        System.out.println("processCategoryConfigs - Done");
    }

    private static void processData(List<JsonDataModel.SourceData> sourceDataList, Context context) {
        System.out.println("processData - Start");

        for (JsonDataModel.SourceData sourceData: sourceDataList){
            System.out.println("SourceID = " + sourceData.sourceID);
            if (sourceData.catagories != null && sourceData.sourceID != null) {
                System.out.println("Catagory count = " + sourceData.catagories.size());

                if (sourceData.catagories.size() == 0) {
                    if (sourceData.sourceID == null) {
                        //TODO notify user
                        System.out.println("categories cannot be an empty list");
                    }
                }

                for (JsonDataModel.CatagoryData catagory : sourceData.catagories) {
                    System.out.println("Catagory = " + catagory.catagory);
                    if (catagory.questions != null && catagory.catagory != null) {
                        System.out.println("question count = " + catagory.questions.size());
                        for (String question : catagory.questions) {
                            if (question != null){
                                System.out.println("question = " + question);
                                Question questionObject = new Question(question, sourceData.sourceID,catagory.catagory);
                                try {
                                    QuestionDatabase.getDatabase(context).questionDao().insertAll(questionObject);
                                }
                                catch (Exception e){
                                    // TODO notify user of error
                                    System.out.println(e);
                                }
                            }
                            else {
                                //TODO notify user
                                System.out.println("question cannot be NULL");
                            }
                        }
                    }
                    else {
                        if (catagory.catagory == null) {
                            //TODO notify user
                            System.out.println("category cannot be NULL");
                        }
                        else {
                            //TODO notify user
                            System.out.println("questions cannot be NULL");
                        }
                    }
                }
            }
            else {
                if (sourceData.sourceID == null) {
                    //TODO notify user
                    System.out.println("sourceID cannot be NULL");
                }
                else {
                    //TODO notify user
                    System.out.println("categories cannot be NULL");
                }
            }
        }

        System.out.println("processData - Done");
    }

}
