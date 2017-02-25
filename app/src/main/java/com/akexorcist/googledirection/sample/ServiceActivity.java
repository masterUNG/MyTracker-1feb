package com.akexorcist.googledirection.sample;

import android.content.Intent;
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
    private ArrayList<String> routesLocationStringArrayList, idRoutesArrayList;
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
            idRoutesArrayList = new ArrayList<String>();


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (loginStrings[0].equals(jsonObject.getString("id_driver"))) {
                    routesLocationStringArrayList.add(jsonObject.getString("Plate"));
                    idRoutesArrayList.add(jsonObject.getString("routes_id"));

                }   // if

            }   // for

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(ServiceActivity.this,
                    android.R.layout.simple_list_item_1, routesLocationStringArrayList);
            listView.setAdapter(stringArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    activeClickListView(i);

                }   // onItemClick
            });


        } catch (Exception e) {
            Log.d("1febV2", "e createList ==> " + e.toString());
        }


    }   // createListView

    private void activeClickListView(int position) {
        Log.d("25febV1", "position ที่ถูก Click ==> " + position);
        Log.d("25febV1", "id ที่ถูกคลิก Click ==> " + idRoutesArrayList.get(position));

        try {

            GetDetailWhereID getDetailWhereID = new GetDetailWhereID(ServiceActivity.this);
            getDetailWhereID.execute(idRoutesArrayList.get(position));
            String strJSON = getDetailWhereID.get();
            Log.d("25febV1", "JSON ที่อ่านได้ จาก Where id ==> " + idRoutesArrayList.get(position) + " ตือ >>>>> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);

            String[] detailStrings = new String[11];
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            detailStrings[0] = jsonObject.getString("routes_id");
            detailStrings[1] = jsonObject.getString("id_driver");
            detailStrings[2] = jsonObject.getString("Plate");
            detailStrings[3] = jsonObject.getString("MyDate");
            detailStrings[4] = jsonObject.getString("MyTime");
            detailStrings[5] = jsonObject.getString("Lat");
            detailStrings[6] = jsonObject.getString("Lng");
            detailStrings[7] = jsonObject.getString("TypeCar");
            detailStrings[8] = jsonObject.getString("ID_car");
            detailStrings[9] = jsonObject.getString("Passenter");
            detailStrings[10] = jsonObject.getString("Request");

            for (int i=0;i<detailStrings.length;i++) {
                Log.d("25febV1", "detailString(" + i + ") ==> " + detailStrings[i]);
            }

            //Intent to Detail Show
            Intent intent = new Intent(ServiceActivity.this, DetailShow.class);
            intent.putExtra("Detail", detailStrings);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }   // activeClickListView

}   // Main Class
