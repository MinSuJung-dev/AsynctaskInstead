package com.example.asynctaskinstead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.asynctaskinstead.databinding.ActivityAsyncTaskBinding;


public class AsyncTaskActivity extends AppCompatActivity {

    ActivityAsyncTaskBinding binding;

    BackgroundTask task;
    BackgroundTask_Bind task_bind;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_async_task);
        binding.setActivity(this);

        progressbar = binding.progress;
    }
    public void btnOnClick(View view) {
        switch (view.getId()) {
            case R.id.executeButton:
                task = (BackgroundTask) new BackgroundTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                task_bind = (BackgroundTask_Bind) new BackgroundTask_Bind().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case R.id.cancelButton:
                task.cancel(true);
                task_bind.cancel(true);
                break;
        }
    }

    int value = 0;
    // 바인딩 미적용
    class BackgroundTask extends AsyncTask<Void , Integer , Integer> {
        @Override
        protected void onPreExecute() {
            value = 0;
            progressbar.setProgress(value);
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            while (isCancelled() == false) {
                value++;
                if (value >= 100) {
                    break;
                } else {
                    publishProgress(value);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }

            return value;
        }
        @Override
        protected void onProgressUpdate(Integer ... values) {
            progressbar.setProgress(values[0].intValue());
        }
        @Override
        protected void onPostExecute(Integer result) {
            progressbar.setProgress(0);
        }
        @Override
        protected void onCancelled() {
            progressbar.setProgress(0);
        }
    }


    // 바인딩 데이터 적용
    class BackgroundTask_Bind extends AsyncTask<Void , Integer , Integer> {
        protected void onPreExecute() {
            binding.setProgressVal(0);
        }

        protected Integer doInBackground(Void ... values) {
            while (isCancelled() == false) {
                int value = binding.getProgressVal() + 1;
                if (value >= 100) {
                    break;
                } else {
                    binding.setProgressVal(value);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }
            return value;
        }

        protected void onPostExecute(Integer result) {
            binding.setProgressVal(0);
        }

        protected void onCancelled() {
            binding.setProgressVal(0);
        }
    }
}
