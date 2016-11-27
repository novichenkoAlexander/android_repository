package my.com.friendsrequest.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

import my.com.friendsrequest.*;
import my.com.friendsrequest.utils.HttpUtils;
import my.com.friendsrequest.utils.Parser;
import my.com.friendsrequest.utils.RandomGenerator;

public class MainActivity extends AppCompatActivity {

    private TextView resTextView;
    private EditText input;

    public static String url;
    private String friendsRequestType;
    private String firstFriendsParam;
    private String secondFriendsParam;
    private String thirdFriendsParam;
    private String fourthFriendsParam;

    private String userId;
    private String order;
    private String friendsFields;
    public static String nameCase;

    public static String arrayName;
    private String firstName;
    private String lastName;
    private String response;

    private ArrayList<Friend> friendsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.user_id_edit_text);
        Button randomButton = (Button) findViewById(R.id.random_button);
        Button getFriendsButton = (Button) findViewById(R.id.user_friends_button);
        Button myIdButton = (Button) findViewById(R.id.my_id_button);
        resTextView = (TextView) findViewById(R.id.result_text_view);

        input.setOnClickListener(inputFieldClickListener);
        randomButton.setOnClickListener(randomButtonClick);
        myIdButton.setOnClickListener(myIdButtonClick);
        getFriendsButton.setOnClickListener(getFriendsClick);

        url = getResources().getString(R.string.url);

        /**
         * Friends.get params
         */
        friendsRequestType = HttpUtils.RequestType.GET_FRIENDS.toString();
        firstFriendsParam = HttpUtils.FriendsGetParamsName.USER_ID.toString();
        secondFriendsParam = HttpUtils.FriendsGetParamsName.ORDER.toString();
        thirdFriendsParam = HttpUtils.FriendsGetParamsName.FIELDS.toString();
        fourthFriendsParam = HttpUtils.FriendsGetParamsName.NAME_CASE.toString();
        friendsFields = getResources().getString(R.string.friends_fields);

        order = getResources().getString(R.string.order);
        nameCase = getResources().getString(R.string.name_case);
        /**
         * Parser's params
         */
        arrayName = Parser.Parameters.ARRAY_NAME.toString();
        firstName = Parser.Parameters.NAME.toString();
        lastName = Parser.Parameters.SURNAME.toString();
    }

    View.OnClickListener inputFieldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resTextView.setText("");
            input.setText("");
        }
    };

    View.OnClickListener myIdButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resTextView.setText("");
            String myId = getResources().getString(R.string.my_id);
            input.setText(myId);
        }
    };

    View.OnClickListener randomButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resTextView.setText("");
            input.setText(RandomGenerator.generator());
        }
    };

    class MyTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            response = null;
            try {
                response = HttpUtils.makeRequest(url, friendsRequestType, firstFriendsParam,
                        secondFriendsParam, thirdFriendsParam, fourthFriendsParam, userId, order,
                        friendsFields, nameCase);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    View.OnClickListener getFriendsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            friendsList.clear();
            resTextView.setText("");
            userId = input.getText().toString();
            new MyTask().execute();
            try {
                Thread.sleep(1000);
                friendsList = Parser.parseMethod(response, arrayName, firstName, lastName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(friendsList.size() == 0){
                resTextView.setText(getResources().getString(R.string.in_case_of_empty_list));
                return;
            }
            if(friendsList.size() > 5000){
                resTextView.setText(getResources().getString(R.string.in_case_of_overload_list));
                return;
            }
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            intent.putExtra("Friends", friendsList);
            startActivity(intent);
        }
    };

}
