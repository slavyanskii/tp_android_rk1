package ru.tp.viacheslav.rk_go.Activities;

/**
 * Created by viacheslav on 07.03.17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Scheduler;
import ru.mail.weather.lib.Storage;
import ru.tp.viacheslav.rk_go.R;
import ru.tp.viacheslav.rk_go.Services.NewsService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver receiver = null;


    private TextView Title;
    private TextView Date;
    private TextView Content;

    private Button InBackground;
    private Button NotBackground;
    private Button Refresh;
    private Button Change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, NewsService.class);
        intent.setAction(NewsService.NEWS_LOAD_ACTION);
        startService(intent);

        Title = (TextView)findViewById(R.id.title);
        Date = (TextView)findViewById(R.id.date);
        Content = (TextView)findViewById(R.id.content);

        Refresh = (Button)findViewById(R.id.refresh);
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsService.class);
                intent.setAction(NewsService.NEWS_LOAD_ACTION);
                startService(intent);
//                onUpdate();
            }
        });

        Change = (Button)findViewById(R.id.change_category);
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        InBackground = (Button)findViewById(R.id.in_background);
        InBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "BackSub");
                Intent intent = new Intent(MainActivity.this, NewsService.class);
                intent.setAction(NewsService.NEWS_LOAD_ACTION);
                Scheduler.getInstance().schedule(MainActivity.this, intent, 60000);
            }
        });

        NotBackground = (Button)findViewById(R.id.no_background);
        NotBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Unsub");
                Intent intent = new Intent(MainActivity.this, NewsService.class);
                intent.setAction(NewsService.NEWS_LOAD_ACTION);
                Scheduler.getInstance().unschedule(MainActivity.this, intent);
            }
        });
        onUpdate();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Start");
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(NewsService.NEWS_CHANGED_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                MainActivity.this.onUpdate();
                Log.d(TAG, "Receive");

            }
        };
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (receiver!= null) {
            LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(receiver);
            receiver = null;
        }
    }

    protected void onUpdate() {
        Log.d(TAG, "Upd");

        News news = Storage.getInstance(MainActivity.this).getLastSavedNews();
        DateFormat news_date = new SimpleDateFormat("dd/MM/yyyy");
        Title = (TextView)findViewById(R.id.title);
        Date = (TextView)findViewById(R.id.date);
        Content = (TextView)findViewById(R.id.content);
        String text;
        if (news == null) {
            text = "Error";
            Title.setText(text);

        } else {
            Title.setText(news.getTitle());
            Date.setText(news_date.format(news.getDate()));
            Content.setText(news.getBody());
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }
}
