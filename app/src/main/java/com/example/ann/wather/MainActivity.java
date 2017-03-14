package com.example.ann.wather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
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
    private final String KEY = "trnsl.1.1.20170204T100119Z.4cfbceb022d57915.c672169" +
            "baf48fcaa7d05af50ab3798748a3ee1e2";
    private final String URL = "https://translate.yandex.net/";
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();
    private Link intf = retrofit.create(Link.class);
    TextView tnIN, tnOUT;
    final int RU = 1;
    final int EN = 2;
    final int DE = 3;
    final int RUq = 4;
    final int ENq = 5;
    final int DEq = 6;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tnIN = (TextView) findViewById(R.id.tnIN);
        tnOUT = (TextView) findViewById(R.id.tnOUT);

        registerForContextMenu(tnIN);
        registerForContextMenu(tnOUT);

        tTrans = (EditText) findViewById(R.id.etTranslate);
        tResult = (TextView) findViewById(R.id.result);
        btTranslate = (Button) findViewById(R.id.btnTranslate);
        btTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Создаем запрос
                Map<String, String> mapJson = new HashMap<String, String>();
                mapJson.put("key", KEY);
                mapJson.put("lang", tnIN.getText().toString() + "-" + tnOUT.getText().toString());
                mapJson.put("text", tTrans.getText().toString());

                //Отправляем асинхронный запрос
                Call<Object> call = intf.translate(mapJson);
                call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response){
                            if (response.isSuccessful()){

                                // Парсим Json
                                Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
                              for (Map.Entry e : map.entrySet()) {
                                if(e.getKey().equals("text")) {

                                    //Выводим ответ.
                                    tResult.setText(e.getValue().toString());
                                }
                              }
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                        }
                });
            }




        });

 }

@Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()) {
            case R.id.tnIN:
                menu.add(0, RU, 0, "RUS");
                menu.add(0, EN, 0, "EN");
                menu.add(0, DE, 0, "DE");
                break;
            case R.id.tnOUT:
                menu.add(0, RUq, 0, "RUS");
                menu.add(0, ENq, 0, "EN");
                menu.add(0, DEq, 0, "DE");
                break;
        }
    }

@Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // пункты меню для tvColor
            case RU:
                tnIN.setText("ru");
                break;
            case EN:
                tnIN.setText("en");
                break;
            case DE:
                tnIN.setText("de");
                break;
            // пункты меню для tvSize
            case RUq:
                tnOUT.setText("ru");
                break;
            case ENq:
                tnOUT.setText("en");
                break;
            case DEq:
                tnOUT.setText("de");
                break;
        }
        return super.onContextItemSelected(item);
    }
}



   //Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);

