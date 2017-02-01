package com.akexorcist.googledirection.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Authentication extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passEditText;
    private String userString, passString;
    private String[] userStrings = new String[4];
    private boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText);
        passEditText = (EditText) findViewById(R.id.editText2);
    } // Main Method

    public void clickLogin(View view) {
        userString = userEditText.getText().toString().trim();
        passString = passEditText.getText().toString().trim();

        //Check space
        if (userString.equals("") || passString.equals("")) {
            //Have Space
            MyAlert myAlert = new MyAlert(Authentication.this);
            myAlert.myDialog(R.drawable.doremon48, "Have Space", "Please Fill All Every Blank");
        } else {
            //No Space
            checkUserPass();

        }
    }// clickLogin

    private void checkUserPass() {

        try {

            MySynchronize mySynchronize = new MySynchronize(Authentication.this);
            MyConstant myConstant = new MyConstant();
            mySynchronize.execute(myConstant.getUrlDriver());
            String strJSON = mySynchronize.get();
            Log.d("1febV1", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {

                    userStrings[0] = jsonObject.getString("id");
                    userStrings[1] = jsonObject.getString("User");
                    userStrings[2] = jsonObject.getString("Password");
                    userStrings[3] = jsonObject.getString("Name");

                    aBoolean = false;

                }   // if
            }   // for

            if (aBoolean) {

                MyAlert myAlert = new MyAlert(Authentication.this);
                myAlert.myDialog(R.drawable.nobita48, "User False", "No This User in Database");

            } else if (!passString.equals(userStrings[2])) {

                MyAlert myAlert = new MyAlert(Authentication.this);
                myAlert.myDialog(R.drawable.nobita48, "Password False",
                        "Please Try Again Password False");

            } else {

                //Welcome User
                Toast.makeText(Authentication.this,
                        "ยินดีต้อนรับ " + userStrings[3],
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Authentication.this, ServiceActivity.class);
                intent.putExtra("Login", userStrings);
                startActivity(intent);
                finish();
            }


        } catch (Exception e) {
            Log.d("1febV1", "e checkUserPass ==> " + e.toString());
        }

    }   // checkUserPass


}// Main Class
