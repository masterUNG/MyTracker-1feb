package com.akexorcist.googledirection.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private String[] loginStrings;
    private ListView listView;
    private String[] idStrings, routesLocationStrings;
    private ArrayList<String> routesLocationStringArrayList;
    private String[] dataStrings = new String[11]; // 11 คือจำนวน Column ของ routes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Setting
        loginStrings = getIntent().getStringArrayExtra("Login");

        //Bind Widget
        listView = (ListView) findViewById(R.id.livJob);

        //Create ListView
        createListView();


    }   // Main Method

    private void createListView() {

        try {

            MySynchronize mySynchronize = new MySynchronize(ServiceActivity.this);
            MyConstant myConstant = new MyConstant();
            mySynchronize.execute(myConstant.getUrlRoutes());
            String strJSON = mySynchronize.get();
            Log.d("1febV2", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            idStrings = new String[jsonArray.length()];
            routesLocationStrings = new String[jsonArray.length()];
            routesLocationStringArrayList = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (loginStrings[0].equals(jsonObject.getString("id_driver"))) {
                    routesLocationStringArrayList.add(jsonObject.getString("Plate"));
                }   // if

            }   // for

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(ServiceActivity.this,
                    android.R.layout.simple_list_item_1, routesLocationStringArrayList);
            listView.setAdapter(stringArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    activeClickListView(i + 1);

                }   // onItemClick
            });


        } catch (Exception e) {
            Log.d("1febV2", "e createList ==> " + e.toString());
        }


    }   // createListView

    private void activeClickListView(int id) {
        Log.d("25febV1", "id ที่ถูก Click ==> " + id);
    }

}   // Main Class
