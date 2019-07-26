package com.example.bmr;

import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.* ;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class check_data extends AppCompatActivity {

    private Button back, edit, delete, OK;
    private EditText name, age, weight, height, bmr;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter = null;
    private String search, search_result;

    private String dname  ;
    private Double dweight , dheight ;
    private int dage , dsex ;

    private String url = "http://10.0.2.2/android_use/search_mysql.php";
    private String update_url = "http://10.0.2.2/android_use/update_data_mysql.php" ;
    private String delete_url = "http://10.0.2.2/android_use/delete_where_mysql.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);

        Bundle bundle = this.getIntent().getExtras();
        search = bundle.getString("user_name");

        Thread thread = new Thread(runnable);
        thread.start();

        back = findViewById(R.id.check_data_back);
        edit = findViewById(R.id.check_data_edit);
        delete = findViewById(R.id.check_data_delete);
        OK = findViewById(R.id.check_data_OK);
        back.setOnClickListener(backOnClick);
        edit.setOnClickListener(editOnClick);
        delete.setOnClickListener(deleteOnClick);
        OK.setOnClickListener(OKOnClick);

        spinner = findViewById(R.id.gender_out);

        name = findViewById(R.id.name_out);
        age = findViewById(R.id.age_out);
        weight = findViewById(R.id.weight_out);
        height = findViewById(R.id.height_out);
        bmr = findViewById(R.id.bmr_out);


        name.setText(search);

        adapter = ArrayAdapter.createFromResource(this, R.array.choose_sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerOnClick);
        spinner.setEnabled(false);

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //use post method
            HttpClient httpClient = new DefaultHttpClient( ) ;
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse;
            List<NameValuePair> var = new ArrayList<NameValuePair>();
            var.add(new BasicNameValuePair("pname", search));

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(var, HTTP.UTF_8) ;
                httpPost.setEntity(urlEncodedFormEntity) ;
                httpResponse = httpClient.execute(httpPost) ;
                HttpEntity resEntity = httpResponse.getEntity();
                search_result = EntityUtils.toString(resEntity);
            } catch (Exception e) {
                Log.e("ERROR : ", e.toString());
            }

            try {
                JSONArray jary = new JSONArray(search_result);
                for (int i = 0; i < jary.length(); i++) {
                    JSONObject jobt = jary.getJSONObject(i);
                    dname = jobt.getString("name");
                    dsex = jobt.getInt("sex");
                    dweight = jobt.getDouble("weight");
                    dheight = jobt.getDouble("height");
                    dage = jobt.getInt("age");
                }
            }catch ( JSONException e) {
                e.printStackTrace() ;
            }

            runOnUiThread(uithread) ;

        }
    };


    private Runnable uithread = new Runnable() {
        public void run() {
            double value = 10 * dweight + 6.25 * dheight - 5 * dage ;
            value = ( dsex == 1 ? value+5 : value-161) ;
            name.setText(dname) ;
            age.setText(String.valueOf(dage)) ;
            weight.setText(String.valueOf(dweight)) ;
            height.setText(String.valueOf(dheight)) ;
            bmr.setText(String.valueOf(value)) ;
            spinner.setSelection(dsex-1) ;
        }
    } ;

    Spinner.OnItemSelectedListener spinnerOnClick = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String s = ( position == 0 ? "male" : "female" ) ;
            dsex = ( position == 0 ? 1 : 2 ) ;
            Toast.makeText(check_data.this , "change gender to : " + s , Toast.LENGTH_SHORT ).show() ;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(check_data.this , "沒有選擇性別" , Toast.LENGTH_SHORT).show() ;
        }
    } ;

    Button.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent() ;
            it.setClass( check_data.this , start_page.class ) ;
            startActivity(it) ;
        }
    } ;

    Button.OnClickListener editOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE) ;
            OK.setVisibility(View.VISIBLE);
            name.setFocusable(true); ;
            name.setFocusableInTouchMode(true);
            name.requestFocus() ;
            age.setFocusable(true) ;
            age.setFocusableInTouchMode(true);
            age.requestFocus() ;
            weight.setFocusable(true) ;
            weight.setFocusableInTouchMode(true);
            weight.requestFocus() ;
            height.setFocusable(true) ;
            height.setFocusableInTouchMode(true);
            height.requestFocus() ;
            spinner.setEnabled(true) ;
        }
    } ;

    Button.OnClickListener deleteOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Thread delete_data = new Thread(deleteData) ;
            delete_data.start() ;
            try {
                delete_data.join() ;
            }catch ( Exception e) {
                Log.i("ERROR : " , e.toString()) ;
            }
            Intent it = new Intent() ;
            it.setClass( check_data.this , start_page.class) ;
            startActivity(it) ;
        }
    } ;

    private Runnable deleteData = new Runnable() {
        @Override
        public void run() {
            String a = name.getText().toString() ;
            String b = age.getText().toString() ;
            int cc = spinner.getSelectedItemPosition() ;
            cc += 1 ;
            String c = String.valueOf(cc) ;

            HttpPost httpPost = new HttpPost(delete_url) ;
            HttpResponse httpResponse ;
            List<NameValuePair> var = new ArrayList<NameValuePair>() ;
            var.add(new BasicNameValuePair("pname", a)) ;
            var.add(new BasicNameValuePair("psex",c )) ;
            var.add(new BasicNameValuePair("page",b)) ;

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(var,HTTP.UTF_8) ;
                httpPost.setEntity(urlEncodedFormEntity) ;
                httpResponse = new DefaultHttpClient().execute(httpPost) ;
            }catch ( Exception e ) {
                Log.e("ERROR : ", e.toString());
            }
        }
    } ;


    Button.OnClickListener OKOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dname = name.getText().toString() ;
            dheight = Double.valueOf(height.getText().toString()).doubleValue() ;
            dweight = Double.valueOf(weight.getText().toString()).doubleValue() ;
            dage = Integer.parseInt(age.getText().toString()) ;

            double value = 10 * dweight + 6.25 * dheight - 5 * dage ;
            value = ( dsex == 1 ? value+5 : value-161) ;

            Thread okToUpdate = new Thread(update_data) ;
            okToUpdate.start() ;

            delete.setVisibility(View.GONE);
            OK.setVisibility(View.GONE) ;
            edit.setVisibility(View.VISIBLE);
            name.setFocusable(false); ;
            age.setFocusable(false) ;
            weight.setFocusable(false) ;
            height.setFocusable(false) ;
            spinner.setEnabled(false);

            bmr.setText(String.valueOf(value)) ;


        }
    } ;

    private Runnable update_data = new Runnable() {
        @Override
        public void run() {

            HttpPost httpPost = new HttpPost(update_url) ;
            HttpResponse httpResponse ;
            List<NameValuePair> var = new ArrayList<NameValuePair>() ;
            var.add(new BasicNameValuePair("psearch" , search)) ;
            var.add(new BasicNameValuePair("pname", dname)) ;
            var.add(new BasicNameValuePair("psex",Integer.toString(dsex))) ;
            var.add(new BasicNameValuePair("pweight",Double.toString(dweight))) ;
            var.add(new BasicNameValuePair("pheight",Double.toString(dheight))) ;
            var.add(new BasicNameValuePair("page",Integer.toString(dage))) ;

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(var,HTTP.UTF_8) ;
                httpPost.setEntity(urlEncodedFormEntity) ;
                httpResponse = new DefaultHttpClient().execute(httpPost) ;
            }catch ( Exception e ) {
                Log.e("ERROR : ", e.toString());
            }
        }
    } ;


}
