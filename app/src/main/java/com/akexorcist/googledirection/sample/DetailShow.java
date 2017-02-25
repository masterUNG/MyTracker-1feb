package com.akexorcist.googledirection.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DetailShow extends AppCompatActivity implements OnMapReadyCallback, DirectionCallback {
    private Button btnRequestDirection;
    private GoogleMap googleMap;
    private String serverKey = "AIzaSyD_6HZwKgnxSOSkMWocLs4-2AViQuPBteQ";
    private LatLng camera, origin, destination;
    private TextView plateTextView, dateTextView, timeTextView, typeCarTextView,
            passengerTextView, idCarTextView, requestTextView;
    private String[] detailStrings;
    private Button startButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);

        bindWidget();

        setupValue();

        showTextView();

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDetail)).getMapAsync(this);

        requestDirection();

        buttonController();

    }   // Main Method

    private void buttonController() {

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailShow.this, MonitorCar.class);
                intent.putExtra("Detail", detailStrings);
                startActivity(intent);
                finish();

            }   // onClick
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void bindWidget() {

        plateTextView = (TextView) findViewById(R.id.textView);
        dateTextView = (TextView) findViewById(R.id.textView2);
        timeTextView = (TextView) findViewById(R.id.textView3);
        typeCarTextView = (TextView) findViewById(R.id.textView5);
        passengerTextView = (TextView) findViewById(R.id.textView6);
        idCarTextView = (TextView) findViewById(R.id.textView7);
        requestTextView = (TextView) findViewById(R.id.textView8);
        startButton = (Button) findViewById(R.id.button3);
        cancelButton = (Button) findViewById(R.id.button4);

    }

    private void showTextView() {

        plateTextView.setText(detailStrings[2]);
        dateTextView.setText(detailStrings[3]);
        timeTextView.setText(detailStrings[4]);
        typeCarTextView.setText(detailStrings[7]);
        passengerTextView.setText(detailStrings[8]);
        idCarTextView.setText(detailStrings[9]);
        requestTextView.setText(detailStrings[10]);



    }

    private void setupValue() {

        detailStrings = getIntent().getStringArrayExtra("Detail");

        MyConstant myConstant = new MyConstant();
        camera = myConstant.getThpLatLng();
        origin = myConstant.getThpLatLng();
        destination = new LatLng(Double.parseDouble(detailStrings[5]),
                Double.parseDouble(detailStrings[6]));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 11));
    }   // onMapReady


    public void requestDirection() {

        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            googleMap.addMarker(new MarkerOptions().position(origin));
            googleMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.BLUE));


        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }
}
