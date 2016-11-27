package my.com.friendsrequest.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.StringTokenizer;

import my.com.friendsrequest.*;
import my.com.friendsrequest.utils.HttpUtils;
import my.com.friendsrequest.utils.MyArrayAdapter;
import my.com.friendsrequest.utils.Parser;

public class ListViewActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    private ArrayList<Friend> friendsList;
    private String response;
    private String url;
    private String name_case;
    private String usersFields;

    private String cityResponse;

    private String arrayName;

    private String cityRequestType;
    private String usersRequestType;
    private String firstUsersParam;
    private String secondUsersParam;
    private String thirdUsersParam;

    private String userIds;
    private String userName;
    private String userSurname;

    private int cityIDS;
    private String firsCityParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_activty);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.my_list_view);
        Intent intent = getIntent();
        friendsList = intent.getParcelableArrayListExtra("Friends");
        MyArrayAdapter arrayAdapter = new MyArrayAdapter(this,friendsList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        firstUsersParam = HttpUtils.UsersGetParamsName.USER_IDS.toString();
        secondUsersParam = HttpUtils.FriendsGetParamsName.FIELDS.toString();
        thirdUsersParam = HttpUtils.FriendsGetParamsName.NAME_CASE.toString();

        url = MainActivity.url;

        /**
         * Users.get params
         */
        usersRequestType = HttpUtils.RequestType.GET_USERS.toString();
        usersFields = getResources().getString(R.string.users_fields);
        name_case = MainActivity.nameCase;
        arrayName = MainActivity.arrayName;

        cityRequestType = HttpUtils.RequestType.GET_CITY_NAME.toString();
        firsCityParam = HttpUtils.UsersGetParamsName.CITY_IDS.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class UserRequest extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            response = null;
            try {
                response = HttpUtils.makeRequest(url, usersRequestType, firstUsersParam,
                        secondUsersParam, thirdUsersParam, null, userIds, usersFields,
                        name_case, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    class cityRequest extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            cityResponse = null;
            String cityIds = String.valueOf(cityIDS);
            try {
                cityResponse = HttpUtils.makeRequest(url, cityRequestType, firsCityParam, null, null, null, cityIds, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cityResponse;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        userIds = friendsList.get(position).getId().toString();
        userName = friendsList.get(position).getName();
        userSurname = friendsList.get(position).getSurname();
        new UserRequest().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String photoParameter = Parser.Parameters.PHOTO.toString();
        String cityParameter = Parser.Parameters.CITY.toString();
        String bDateParameter = Parser.Parameters.B_DATE.toString();
        String cityNameParameter = Parser.Parameters.CITY_NAME.toString();
        String cityName = null;
        String birthDate = null;
        String photo = null;
        try {
            photo = Parser.getStringItem(response, arrayName, photoParameter);
            cityIDS = Parser.getIntItem(response, arrayName, cityParameter);
            birthDate = Parser.getStringItem(response, arrayName, bDateParameter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new cityRequest().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            cityName = Parser.getStringItem(cityResponse,arrayName, cityNameParameter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(ListViewActivity.this, UserProfileActivity.class);
        intent.putExtra("photo", photo);
        intent.putExtra("name", userName);
        intent.putExtra("surname",userSurname);
        intent.putExtra("bDate", birthDate);
        intent.putExtra("city",cityName);
        startActivity(intent);
    }

}

