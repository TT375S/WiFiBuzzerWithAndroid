package com.example.user1.servicetest1;
        import android.content.Intent;
        import android.content.res.AssetFileDescriptor;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //音楽の再生。本来、prepareとplayに分けるべきだが面倒なのでとりあえずこうしている。
    private void audioPlay(){
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
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

// 再生する
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button buttonStart = (Button) findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //警告音の再生
                //audioPlay();


                Intent intent = new Intent(getApplication(), ServiceTimer.class);
                startService(intent);
            }
        });

        Button buttonStop = (Button) findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Serviceの停止
                Intent intent = new Intent(getApplication(), ServiceTimer.class);
                stopService(intent);
            }
        });
    }
}