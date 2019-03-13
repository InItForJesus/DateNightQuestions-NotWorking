package com.initforjesus.datenightquestions.persistence;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JsonDataModel {

    @SerializedName("allowedSources")
    public ArrayList<SourceConfig> sourceConfigs;

    @SerializedName("allowedCatagories")
    public ArrayList<String> catagoryConfigs;

    @SerializedName("data")
    public ArrayList<SourceData> sources;

    static public class SourceConfig {
        @SerializedName("sourceID")
        public String sourceID;
        @SerializedName("sourceName")
        public String sourceName;
        @SerializedName("sourceURL")
        public String sourceURL;
    }

    static public class SourceData {
        @SerializedName("sourceID")
        public String sourceID;

        @SerializedName("catagories")
        public ArrayList<CatagoryData> catagories;
    }


    public static class CatagoryData {
        @SerializedName("catagory")
        public String catagory;

        @SerializedName("questions")
        public ArrayList<String> questions;
    }
}
