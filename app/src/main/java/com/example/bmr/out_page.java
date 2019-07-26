package com.example.bmr;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.* ;

import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


// this page has no database access
public class out_page extends AppCompatActivity {

    private TextView out_name , out_bmi , out_bmr ;
    private Button back_to_title ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_page);

        out_name = findViewById(R.id.out_page_out_name) ;
        out_bmi = findViewById(R.id.out_page_out_bmi) ;
        out_bmr = findViewById(R.id.out_page_out_bmr) ;
        back_to_title = findViewById(R.id.back_to_main) ;

        back_to_title.setOnClickListener(backOnClick);

        Bundle bundle = this.getIntent().getExtras() ;
        String name = bundle.getString("out_name") ;
        String bmr = Double.toString(bundle.getDouble("out_bmr")) ;
        String bmi = Double.toString(bundle.getDouble("out_bmi")) ;

        out_name.setText(name) ;
        out_bmi.setText(bmi) ;
        out_bmr.setText(bmr) ;

    }

    Button.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent() ;
            it.setClass( out_page.this , start_page.class) ;

            startActivity(it) ;
        }
    } ;

}
