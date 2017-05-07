package hk.com.sunnyng.androidasynchttpdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AsyncTask";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
    }


    public void startAsyncTask(View v) {
        MyAsyncTask task = new MyAsyncTask();
        task.execute("https://www.als.ogcio.gov.hk/lookup?q=fortress%20tower");
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String dataString = "";
            StringBuilder builder = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String data = "";
                while ((data = reader.readLine()) != null){
                    builder.append(data);
                }
            }
            catch (Exception e) {
                Log.e(TAG, "loadContent: " + e.getMessage());
                e.printStackTrace();
            }
            dataString = builder.toString();
            return dataString;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.append("Start loading page ... \n");
            tv.append("====================== \n");

        }

        @Override
        protected void onPostExecute(String dataString) {
            super.onPostExecute(dataString);
            tv.append(dataString);
            tv.append("\n======================");
            tv.append("\nFinished loading page!");


        }


    }
}
