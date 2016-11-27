package my.com.friendsrequest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import my.com.friendsrequest.*;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.user_activity_name));

        WebView webView = (WebView) findViewById(R.id.my_web_view);
        TextView nameTV = (TextView) findViewById(R.id.tv_name);
        TextView surnameTV = (TextView) findViewById(R.id.tv_surname);
        TextView bdateTV = (TextView) findViewById(R.id.tv_bdate);
        TextView cityNameTV = (TextView) findViewById(R.id.tv_city);

        Intent intent = getIntent();
        String photo = intent.getStringExtra("photo");
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String bDate = intent.getStringExtra("bDate");
        String city = intent.getStringExtra("city");

        webView.loadUrl(photo);
        nameTV.setText("First Name: " + name);
        surnameTV.setText("Last Name: " + surname);
        bdateTV.setText("Date of birth: " + bDate);
        cityNameTV.setText("City: " + city);
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

}
