package com.example.asynctaskinstead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.asynctaskinstead.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
    }

    public void btnOnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_AsyncTask:
                intent = new Intent(this, AsyncTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_RxJava:
                intent = new Intent(this, RxJavaActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_Coroutine:
                intent = new Intent(this, CoroutineActivity.class);
                startActivity(intent);
                break;
        }
    }

}
