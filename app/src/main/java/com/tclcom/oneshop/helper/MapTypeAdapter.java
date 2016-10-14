package com.tclcom.oneshop.helper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangduan on 16/10/13.
 */

public class MapTypeAdapter extends TypeAdapter<Map<String, Object>> {

    private Gson gson;

    public MapTypeAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public void write(JsonWriter out, Map<String, Object> value) throws IOException {
        out.beginObject();
//        for (final String dataKey : value.keySet()) {
//            out.name(dataKey).nullValue();
//        }
        out.name("map").jsonValue(gson.toJson(value));
        out.endObject();
    }

    @Override
    public Map<String, Object> read(JsonReader in) throws IOException {
        final Map<String, Object> map = new HashMap<>();
        in.beginObject();

        in.endObject();
        return map;
    }
}
