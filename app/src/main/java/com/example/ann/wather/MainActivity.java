package com.example.ann.wather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.list;


public class MainActivity extends AppCompatActivity{
    private EditText tTrans;
    private TextView tResult;
    private Button btTranslate;

    private Gson gson = new GsonBuilder().create();

    private final String KEY = "trnsl.1.1.20170204T100119Z.4cfbceb022d57915.c672169baf48fcaa7d05af50ab3798748a3ee1e2";
    private final String URL = "https://translate.yandex.net/";

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();


    private Link intf = retrofit.create(Link.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tTrans = (EditText) findViewById(R.id.etTranslate);
        tResult = (TextView) findViewById(R.id.result);
        btTranslate = (Button) findViewById(R.id.btnTranslate);

        btTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mapJson = new HashMap<String, String>();
                mapJson.put("key", KEY);
                mapJson.put("lang", "en-ru");
                mapJson.put("text", tTrans.getText().toString());


                Call<Object> call = intf.translate(mapJson);
                    try {
                    Response<Object> response = call.execute();

                    Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
                    for (Map.Entry e : map.entrySet()) {
                        System.out.println(e.getKey() + " " + e.getValue());
                    }

                } catch (IOException e) {
                }
            }

        });
    }}







