package com.example.bmr;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.View;
import android.content.Intent ;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button clear_button ;
    private Button submit_button ;
    private EditText edit_name , edit_age , edit_iheight , edit_iweight ;
    private RadioGroup choose_sex ;
    //private RadioButton cmale , cfemale ;
    String url = "http://10.0.2.2/android_use/insert_to_mysql.php" ;

    String strName ;
    int iAge , iChoose_sex , psex ;
    double iHeight , iWeight , bmr , bmi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set button id and set listener
        clear_button = findViewById(R.id.clear) ;
        clear_button.setOnClickListener(clearOnClick) ;

        submit_button = findViewById(R.id.submit) ;
        submit_button.setOnClickListener(submitOnClick) ;

        edit_name = findViewById(R.id.edit_name) ;
        edit_age = findViewById(R.id.edit_age) ;
        edit_iheight = findViewById(R.id.edit_iheight) ;
        edit_iweight = findViewById(R.id.edit_iweight) ;

        choose_sex = findViewById(R.id.choose_sex) ;
        choose_sex.setOnCheckedChangeListener(csex);

    }

    //psex = 1 is male
    private RadioGroup.OnCheckedChangeListener csex = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group , int checkId ){
            switch ( checkId ) {
                case R.id.cmale : psex = 1 ; break ;
                case R.id.cfemale : psex = 2 ; break ;
            }
        }
    } ;


    //set listener and event handle
    Button.OnClickListener clearOnClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v){
            edit_name.setText("") ;
            edit_age.setText("") ;
            edit_iheight.setText("") ;
            edit_iweight.setText("") ;
        }

    } ;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //use post method
            HttpPost httpPost = new HttpPost(url) ;
            HttpResponse httpResponse ;
            List<NameValuePair> var = new ArrayList<NameValuePair>() ;
            var.add(new BasicNameValuePair("pname",strName)) ;
            var.add(new BasicNameValuePair("psex",Integer.toString(psex))) ;
            var.add(new BasicNameValuePair("pweight",Double.toString(iWeight))) ;
            var.add(new BasicNameValuePair("pheight",Double.toString(iHeight))) ;
            var.add(new BasicNameValuePair("page",Integer.toString(iAge))) ;

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(var,HTTP.UTF_8) ;
                httpPost.setEntity(urlEncodedFormEntity) ;
                httpResponse = new DefaultHttpClient().execute(httpPost) ;
            }catch ( Exception e ) {
                Log.e("ERROR : ", e.toString());
            }
        }

    } ;

    Button.OnClickListener submitOnClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v){

            strName = edit_name.getText().toString() ;
            iAge = Integer.parseInt(edit_age.getText().toString()) ;
            iHeight = Double.valueOf(edit_iheight.getText().toString()).doubleValue() ;
            iWeight = Double.valueOf(edit_iweight.getText().toString()).doubleValue() ;

            iChoose_sex = choose_sex.getCheckedRadioButtonId() ;

            bmr = ( psex == 1 ? 66+13.7*iWeight+5*iHeight-6.8*iAge : 655+9.6*iWeight+1.8*iHeight-4.7*iAge ) ;
            bmi = iWeight / ( iHeight * iHeight / 10000 ) ;

            Thread thread = new Thread(runnable) ;
            thread.start() ;

            //this from book P.359
            Intent intent = new Intent() ;
            intent.setClass(MainActivity.this , out_page.class) ;

            Bundle bundle = new Bundle() ;
            bundle.putString("out_name" , strName) ;
            bundle.putDouble("out_bmr" , bmr ) ;
            bundle.putDouble("out_bmi",bmi) ;
            intent.putExtras(bundle) ;

            startActivity(intent) ;

        }

    } ;





}
