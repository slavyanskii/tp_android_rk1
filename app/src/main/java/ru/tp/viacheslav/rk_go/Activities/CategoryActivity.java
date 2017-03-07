package ru.tp.viacheslav.rk_go.Activities;

/**
 * Created by viacheslav on 07.03.17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.mail.weather.lib.Storage;
import ru.tp.viacheslav.rk_go.R;
import ru.tp.viacheslav.rk_go.Services.NewsService;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Button cat1 = (Button) findViewById(R.id.cat1);
        Button cat2 = (Button) findViewById(R.id.cat2);
        Button cat3 = (Button) findViewById(R.id.cat3);

        cat1.setText(ru.mail.weather.lib.Topics.AUTO);
        cat2.setText(ru.mail.weather.lib.Topics.HEALTH);
        cat3.setText(ru.mail.weather.lib.Topics.IT);


        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.getInstance(CategoryActivity.this).saveCurrentTopic(ru.mail.weather.lib.Topics.AUTO);
                Intent intent = new Intent(CategoryActivity.this, NewsService.class);
                intent.setAction(NewsService.NEWS_LOAD_ACTION);
                startService(intent);
                finish();
            }
        });

        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.getInstance(CategoryActivity.this).saveCurrentTopic(ru.mail.weather.lib.Topics.HEALTH);
                Intent intent = new Intent(CategoryActivity.this, NewsService.class);
                intent.setAction(NewsService.NEWS_LOAD_ACTION);
                startService(intent);
                finish();
            }
        });

        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.getInstance(CategoryActivity.this).saveCurrentTopic(ru.mail.weather.lib.Topics.IT);
                Intent intent = new Intent(CategoryActivity.this, NewsService.class);
                intent.setAction(NewsService.NEWS_LOAD_ACTION);
                startService(intent);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
