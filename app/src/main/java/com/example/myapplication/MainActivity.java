package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText hours;
    Button predict;
    TextView result;
    String url = "https://basicmlapi.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hours = findViewById(R.id.hours);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String res = jsonObject.getString("Scores");
                                    result.setText(res);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }){
                    @Override
                     protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("hours", hours.getText().toString());

                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);

            }
        });

    }
}