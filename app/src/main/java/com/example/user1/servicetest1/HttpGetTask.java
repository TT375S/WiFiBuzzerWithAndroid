package com.example.user1.servicetest1;

/**
 * Created by user1 on 2017/03/03.
 */

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static java.sql.Types.NULL;

public final class HttpGetTask extends AsyncTask<URL, Void, String> {
    public ServiceTimer severTimer;

    @Override
    protected String doInBackground(URL... urls) {
        // 取得したテキストを格納する変数
        final StringBuilder result = new StringBuilder();
        // アクセス先URL
        final URL url = urls[0];

        HttpURLConnection con = null;
        try {
            // ローカル処理
            // コネクション取得
            con = (HttpURLConnection) url.openConnection();
            con.connect();

            // HTTPレスポンスコード
            final int status = con.getResponseCode();
            Log.d("ITWORK!", "1");
            if (status == HttpURLConnection.HTTP_OK) {
                Log.d("ITWORK!", "2");
                InputStream in = con.getInputStream();
                byte bodyByte[] = new byte[1024];
                in.read(bodyByte);
                in.close();
                Log.d("bodyByte",new String(bodyByte));
                //音楽再生
                if((new String(bodyByte).indexOf("high")) != -1){
                    severTimer.audioPlay();
                }
                // 通信に成功した
                // テキストを取得する
//                final InputStream in = con.getInputStream();
//                final String encoding = con.getContentEncoding();
//                Log.d("ITWORK!", "2.2");
//                //ここが動かないみたい！
//                final InputStreamReader inReader = new InputStreamReader(in, encoding);
//                Log.d("ITWORK!", "2.25");
//                final BufferedReader bufReader = new BufferedReader(inReader);
//                Log.d("ITWORK!", "2.3");
//                String line = null;
//                Log.d("ITWORK!", "2.4");
//                // 1行ずつテキストを読み込む
//                while((line = bufReader.readLine()) != null) {
//                    result.append(line);
//                }
//                bufReader.close();
//                inReader.close();
//                in.close();
            }
            Log.d("ITWORK!", "3");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                // コネクションを切断
                con.disconnect();
            }
        }
        return result.toString();
    }
}
