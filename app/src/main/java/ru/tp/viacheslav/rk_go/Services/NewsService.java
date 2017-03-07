package ru.tp.viacheslav.rk_go.Services;

/**
 * Created by viacheslav on 07.03.17.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import ru.mail.weather.lib.*;

import java.io.IOException;

public class NewsService extends IntentService {

    public final static String NEWS_LOAD_ACTION = "NEWS_LOAD_ACTION";
    public final static String NEWS_ERROR_ACTION = "NEWS_ERROR_ACTION";
    public final static String NEWS_CHANGED_ACTION = "NEWS_CHANGED_ACTION";
    private static final String TAG = NewsService.class.getSimpleName();


    public NewsService() {
        super("NewsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: newsIntent");
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        if (intent != null && NEWS_LOAD_ACTION.equals(intent.getAction())) {
            try {
                String topic = Storage.getInstance(NewsService.this).loadCurrentTopic();
                if(topic==null){
                    topic = ru.mail.weather.lib.Topics.AUTO;
                }
                News news = new NewsLoader().loadNews(topic);
                Storage.getInstance(this).saveNews(news);
                broadcastManager.sendBroadcast(new Intent(NEWS_CHANGED_ACTION));
            } catch (IOException e) {
                broadcastManager.sendBroadcast(new Intent(NEWS_ERROR_ACTION));
            }
        }
    }
}
