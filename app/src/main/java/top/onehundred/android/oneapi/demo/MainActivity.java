package top.onehundred.android.oneapi.demo;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.output);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRepos();
            }
        });


    }

    private void getRepos(){
        output.setText("Loading...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("main thread is", Looper.myLooper().toString());
        retrofit.create(RetrofitTest.class)
                .listRepos("lawlight")
                .enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.d("retrofit thread is", Looper.myLooper().toString());
                List<Repo> list = response.body();
                output.setText("");
                for(Repo repo : list){
                    output.append(repo.toString() + "\n");
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                output.setText(t.getMessage());
            }
        });

        RetrofitUtils.create().listRepos("");

    }
}
