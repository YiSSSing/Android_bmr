package com.example.bmr;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity ;
import android.os.Bundle ;
import android.content.Intent ;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.* ;
import android.view.View ;
import android.view.ViewGroup ;
import org.json.* ;

//for multithread use
import android.os.Handler ;
import android.os.HandlerThread ;

//for internet use and data stream
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class start_page extends AppCompatActivity {


    private Button create_new ;
    public String database_return ;
    public String[] output_data ;
    public ArrayList<String> output = new ArrayList<String>() ;

    private ListView listview ;
    private LayoutInflater inflater ;


    private Runnable mutiThread;

    {
        mutiThread = new Runnable() {
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2/android_use/android_bmr_login.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();         //start connection


                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                        String box = "", line = null;
                        while ((line = bufReader.readLine()) != null) box += line + "\n";
                        inputStream.close();
                        database_return = box;
                    }
                } catch (Exception e) {
                    database_return = e.toString();
                }

                try {
                    JSONArray jary = new JSONArray(database_return);
                    for (int i = 0; i < jary.length(); i++) {
                        JSONObject jobt = jary.getJSONObject(i);
                        String name = jobt.getString("name");
                        /*
                        int sex_id = jobt.getInt("sex");
                        String sex = (sex_id == 1 ? "male" : "female");
                        String weight = String.valueOf(jobt.getDouble("weight"));
                        String height = String.valueOf(jobt.getDouble("height"));
                        String age = String.valueOf(jobt.getInt("age"));
                        */
                        output.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        output_data = new String[output.size()];
                        for (int i = 0; i < output.size(); i++)
                            output_data[i] = output.get(i);
                        listview = findViewById(R.id.listview) ;
                        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
                        listview_adapter adapter = new listview_adapter(output_data,inflater) ;
                        listview.setAdapter(adapter) ;
                        listview.setOnItemClickListener(listviewOnClick);
                    }
                }) ;
            }
        } ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Thread thread = new Thread(mutiThread);
        thread.start();

        create_new = findViewById(R.id.create_button);
        create_new.setOnClickListener(create_newOnClick) ;

    }

    private AdapterView.OnItemClickListener listviewOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent , View view , int position , long id ) {
            Intent intent = new Intent() ;
            intent.setClass( start_page.this , check_data.class ) ;

            Bundle bundle = new Bundle() ;
            bundle.putString("user_name",output_data[position]) ;

            intent.putExtras(bundle) ;
            startActivity(intent) ;
        }
    } ;


    Button.OnClickListener create_newOnClick = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent toBMR = new Intent() ;
            toBMR.setClass( start_page.this , MainActivity.class) ;
            startActivity(toBMR) ;
        }
    } ;


}
