package com.example.asynctaskinstead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.asynctaskinstead.databinding.ActivityRxJavaBinding;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    ActivityRxJavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rx_java);
        binding.setActivity(this);
    }

    public void btnOnClick(View view) {
        switch (view.getId()) {
            case R.id.executeButton:
                BackgroundTask();
                break;
            case R.id.cancelButton:
                isCancel= true;
                break;
        }
    }

    public int value;
    boolean isCancel = false;
    Disposable backgroundtask;

    void BackgroundTask() {
        backgroundtask = Observable.fromCallable(() -> {
            // doInBackground
            value = 0;
            while (isCancel == false) {
                value++;
                if (value >= 100) {
                    break;
                } else {
                    binding.progress.setProgress(value);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            isCancel = false;
            return false;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    // onPostExecute
                    binding.progress.setProgress(0);
                    backgroundtask.dispose();
                });
    }
}
