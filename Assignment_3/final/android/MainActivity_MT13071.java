package com.example.masood.mobileclient;

import android.support.v7.app.ActionBarActivity;

//        Yes, treat it like a GET method in that you are putting your data on the URL. However presumably you have chosen the
//        DELETE verb over GET for a good reason. If this is a
//        REST service, DELETE is used to delete a resource from the server. GET is used to read the resource

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final String USER_AGENT = "Mozilla/5.0";
    TextView getStatus, getResponse;

    public void test(View v) throws Exception {

        getStatus.setText("masood");
        getResponse.setText("response");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStatus = (TextView) findViewById(R.id.tStatusInvisible);
        getResponse = (TextView) findViewById(R.id.tResponseInvisible);
        getStatus.setText("No request sent yet");
        getResponse.setText("No request sent yet");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        new HttpAsyncTask().execute("http://192.168.48.2:8080/Servlet/myServlet");
//        new HttpAsyncTask().execute("http://192.168.48.2:8080/Servlet/myServlet?sn=masood");
//        http://www.mysite.com/mypage.html?var1=value1&var2=value2&var3=value3
    }

    public void onClickRequest(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        if(buttonText.equalsIgnoreCase("get")){
            getResponse.setText("httpget");
            new HttpAsyncTask().execute("http://192.168.48.2:8080/Servlet/myServlet?sn=masood", "get");
        } else if(buttonText.equalsIgnoreCase("put")){
            getResponse.setText("httpPut");
            new HttpAsyncTask().execute("http://192.168.48.2:8080/Servlet/myServlet", "put");
        } else if(buttonText.equalsIgnoreCase("post")){
            getResponse.setText("httpPost");
            new HttpAsyncTask().execute("http://192.168.48.2:8080/Servlet/myServlet", "post");
        } else if(buttonText.equalsIgnoreCase("delete")){
            getResponse.setText("httpDelete");
            new HttpAsyncTask().execute("http://192.168.48.2:8080/Servlet/myServlet?sn=masood", "delete");
        }

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





    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        public void setValues(String s1, String s2){
            getStatus.setText(s1);
            getResponse.setText(s2);
        }

        public String httpGet(String uri){
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {


                response = httpclient.execute(new HttpGet(uri+"&videoName=TheVerge&type=mp4&length=4mins&size=10MB&availability=offline"));
                StatusLine statusLine = response.getStatusLine();
                Log.i("http", "Status check: " + statusLine.getStatusCode());
                Integer statusValue = statusLine.getStatusCode();
                setValues(statusValue.toString(), "httpGet");

                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    getResponse.setText("get");

                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        public String httpPost(String uri) throws Exception{
            String responseString = null;

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("sn", "masood"));
            pairs.add(new BasicNameValuePair("videoName", "TheVerge"));
            pairs.add(new BasicNameValuePair("type", "mp4"));
            pairs.add(new BasicNameValuePair("length", "4Mins"));
            pairs.add(new BasicNameValuePair("size", "10mb"));
            pairs.add(new BasicNameValuePair("availability", "offline"));
            post.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(post);
            Integer statusCode = response.getStatusLine().getStatusCode();
            setValues(statusCode.toString(), "httpGet");


            return responseString;
        }


        public String httpPut(String uri) throws  Exception{
            String responseString = null;

            HttpClient client = new DefaultHttpClient();
            HttpPut put= new HttpPut(uri);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("sn", "masood"));
            pairs.add(new BasicNameValuePair("videoName", "TheVerge"));
            pairs.add(new BasicNameValuePair("type", "mp4"));
            pairs.add(new BasicNameValuePair("length", "4Mins"));
            pairs.add(new BasicNameValuePair("size", "10mb"));
            pairs.add(new BasicNameValuePair("availability", "offline"));
            put.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(put);
            Integer statusCode = response.getStatusLine().getStatusCode();
            setValues(statusCode.toString(), "httpGet");


            return responseString;

        }

        public String httpDelete(String uri) throws IOException {
            String result = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;

            response = httpclient.execute(new HttpDelete(uri+"&videoName=TheVerge&type=mp4&length=4mins&size=10MB&availability=offline"));
            StatusLine statusLine = response.getStatusLine();

            Integer statusValue = statusLine.getStatusCode();
            setValues(statusValue.toString(), "httpGet");


            return result;
        }


        @Override
        protected String doInBackground(String... uri){
            String result = null;

            try {
                if(uri[1].equalsIgnoreCase("get")){
                    result = httpGet(uri[0]);

                }else if(uri[1].equalsIgnoreCase("put")){
                    result = httpPut(uri[0]);
                }else if(uri[1].equalsIgnoreCase("post")){
                    result =  httpPost(uri[0]);
                }else if(uri[1].equalsIgnoreCase("delete")){
                    result = httpDelete(uri[0]);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    } // async task class
}
