package com.example.masood.accelerometerfinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    double ax, ay, az;   // these are the acceleration in x,y and z axis
    TextView display_x, display_y, display_z;
    boolean flag = false;
    public static String ret = "";

    OutputStreamWriter outputStreamWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display_x = (TextView) findViewById(R.id.xValue);
        display_y = (TextView) findViewById(R.id.yValue);
        display_z = (TextView) findViewById(R.id.zValue);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        try {
            outputStreamWriter = new OutputStreamWriter(openFileOutput("accelerometer_data.txt", Context.MODE_PRIVATE));
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void start(View view){
        flag = true;
    }

    public void stop(View view) throws Exception {
        flag = false;
        outputStreamWriter.close();

        //Reading data from file


        try {
            InputStream inputStream = openFileInput("accelerometer_data.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        Intent openFileData = new Intent("com.example.masood.accelerometerfinal.FILEDATA");
//        TextView tv_filedata = (TextView) findViewById(R.id.textview_filedata);
//        tv_filedata.setText("masood");
        startActivity(openFileData);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
        }
        if(flag){
            display_x.setText("x-axis = " + ax);
            display_y.setText("y-axis = " + ay);
            display_z.setText("z-axis = " + az);
            try {
                outputStreamWriter.write("x-axis = " + ax + "   ");
                outputStreamWriter.write("Y-axis = " + ay+ "    ");
                outputStreamWriter.write("z-axis = " + az);
                outputStreamWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}
