package my.com.friendsrequest.utils;


import org.json.*;
import java.util.ArrayList;

import my.com.friendsrequest.Friend;


public class Parser {

    public enum Parameters {

        NAME("first_name"),
        SURNAME("last_name"),
        ARRAY_NAME("response"),
        CITY("city"),
        B_DATE("bdate"),
        PHOTO("photo_200"),
        CITY_NAME("name");
        private final String text;

        Parameters(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }

    public static ArrayList<Friend> parseMethod(String inputData,
                                                String arrayName, String arrayItemName,
                                                String arrayItemSurName)
                                                throws Exception {

        JSONObject jsonObject = new JSONObject(inputData);
        JSONArray array = jsonObject.getJSONArray(arrayName);
        ArrayList<Friend> friends = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            String name = array.getJSONObject(i).getString(arrayItemName);
            String surName = array.getJSONObject(i).getString(arrayItemSurName);
            Long id = array.getJSONObject(i).getLong("user_id");
            Friend friend = new Friend(name,surName,id);
            friends.add(friend);
        }
        return friends;
    }

    public static String getStringItem(String inputData, String arrayName,
                                   String arrayItemName) throws JSONException {
        String item;
        JSONObject jsonObject = new JSONObject(inputData);
        JSONArray array = jsonObject.getJSONArray(arrayName);
        item = array.getJSONObject(0).getString(arrayItemName);
        return item;
    }

    public static Integer getIntItem(String inputData, String arrayName,
                                   String arrayItemName) throws JSONException {
        int item;
        JSONObject jsonObject = new JSONObject(inputData);
        JSONArray array = jsonObject.getJSONArray(arrayName);
        item = array.getJSONObject(0).getInt(arrayItemName);
        return item;
    }

}