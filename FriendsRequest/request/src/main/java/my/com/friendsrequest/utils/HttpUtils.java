package my.com.friendsrequest.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtils {

    public enum RequestType {
        GET_FRIENDS("friends.get"),
        GET_USERS("users.get"),
        GET_CITY_NAME("database.getCitiesById");
        private final String text;

        RequestType(String text) {
            this.text = text;
        }

        public String toString(){
            return text;
        }
    }

    public enum FriendsGetParamsName{
        USER_ID("user_id="),
        ORDER("order="),
        FIELDS("fields="),
        NAME_CASE("name_case=");
        private final String text;

        FriendsGetParamsName(String text){
            this.text = text;
        }

        public String toString(){
            return text;
        }
    }

    public enum UsersGetParamsName{
        USER_IDS("user_ids="),
        CITY_IDS("city_ids=");
        private final String text;

        UsersGetParamsName(String text){
            this.text = text;
        }

        public String toString(){
            return text;
        }
    }

    public static String makeRequest(String resourceUrl, String requestType, String firstParamName,
                                     String secondParamName, String thirdParamName, String fourthParamName,
                                     String firstParam, String secondParam, String thirdParam,
                                     String fourthParam) throws Exception {
        String responseResult;
        String urlString = resourceUrl + requestType + "?" + firstParamName + firstParam + "&" + secondParamName +
                secondParam + "&" + thirdParamName + thirdParam + "&" + fourthParamName + fourthParam;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(1000);
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        responseResult = result.toString();
        return responseResult;
    }

}
