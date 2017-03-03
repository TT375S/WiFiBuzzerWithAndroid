package com.example.user1.servicetest1;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.net.MalformedURLException;
import java.net.URL;

public class ServiceTimer extends Service {

    private Timer timer = null;
    private int count = 0;

    //音楽の再生。本来、prepareとplayに分けるべきだが面倒なのでとりあえずこうしている。
    public void audioPlay(){
        // インタンスを生成
        MediaPlayer mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        String filePath = "sample_music.mp3";

        try {
            // assetsから mp3 ファイルを読み込み
            AssetFileDescriptor afdescripter = getAssets().openFd(filePath);
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            //何故かここだと読み込めないのでコメントアウト
            //setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
// 再生する
        mediaPlayer.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "onStartCommand");

        //このscheduleの中身しか定期実行されない。
        timer = new Timer();
        timer.schedule( new TimerTask(){
            @Override
            public void run(){
                Log.d( "TestService" , "count = "+ count );

                if(count == 5){
                    Log.d( "HIT! TestService" , "count = "+ count );
                }
                count++;
            }
        }, 0, 1000);

        //httpをGET--------ここから
        //HttpGetTaskを実行
        HttpGetTask httpGetTask = new HttpGetTask();
        httpGetTask.severTimer = this;
        try {
            httpGetTask.execute(new URL("http://192.168.0.31/state"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //httpをGET-------ここまで

        //とりあえずのserviceで音楽再生のテスト
        //audioPlay();
        Log.d( "START! TestService" , "count = "+ count );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("service", "onDestroy");
        super.onDestroy();
        // timer cancel
        if( timer != null ){
            timer.cancel();
            timer = null;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // エラーになるので、とりあえず入れてありますが使いません
        return null;
    }
}