package com.example.library_app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerTask extends AsyncTask<String, Void, Void> {
//    private String basic_url="";
//    private String add_url="";
//    private URL url;
//
//    public void makeurl(String add_url){
//        this.add_url=add_url;
//        try{
//            url=new URL(this.basic_url+this.add_url);
//        } catch (Exception e){
//            e.printStackTrace();
//            Log.d("test","LoginTask클래스의 makeurl함수에서 URL객체 생성과 관련된 문제 발생");
//        }
//    }

//    @Override
//    protected Void doInBackground(JSONObject... jsonObjects) {
//        try{
//            HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Content-Type", "application/json");
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            bw.write(jsonObjects.toString());
//            bw.flush();
//            bw.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.d("test","LoginTask클래스의 doInBackground 함수에서 연결 설정 및 write와 관련된 문제 발생");
//        }
//        return null;
//    }

    @Override
    protected Void doInBackground(String... params) {
        try{
            String url = "https://webhook.site/5c50bb61-e3f6-434d-9854-01f7a89f03bf";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");

            byte[] outputInBytes = params[0].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write( outputInBytes );
            os.close();

            int retCode = conn.getResponseCode();

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            br.close();

            String res = response.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
