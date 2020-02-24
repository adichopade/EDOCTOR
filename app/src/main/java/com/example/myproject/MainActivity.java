package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.symptom_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
        Spinner spinner3=(Spinner)findViewById(R.id.spinner3);
        Spinner spinner4=(Spinner)findViewById(R.id.spinner4);
        Spinner spinner5=(Spinner)findViewById(R.id.spinner5);
        Spinner spinner6=(Spinner)findViewById(R.id.spinner6);

        final String ab = spinner1.getSelectedItem().toString();
        final String bc = spinner2.getSelectedItem().toString();
        final String cde = spinner3.getSelectedItem().toString();
        final String de = spinner4.getSelectedItem().toString();
        final String ef = spinner5.getSelectedItem().toString();
        final String fg = spinner6.getSelectedItem().toString();


        Button predictDiseaseButton=(Button) findViewById(R.id.button);
        predictDiseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Symptoms symptoms=new Symptoms(
                        ab,
                        bc,
                        cde,
                        de,
                        ef,
                        fg
                );
                getClient(symptoms);

            }
        });

    }

    private void getClient(Symptoms symptoms) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = null;



        retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:9090/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIInterface apiInterface=retrofit.create(APIInterface.class);
        Call<Symptoms> call=apiInterface.predict(symptoms);


        call.enqueue(new Callback<Symptoms>() {
            @Override
            public void onResponse(Call<Symptoms> call, Response<Symptoms> response) {
                Toast.makeText(MainActivity.this,"Disease"+response.body().getDisease(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Symptoms> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure",Toast.LENGTH_SHORT).show();

            }
        });


    }




}
