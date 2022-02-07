package com.example.library_app;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class ServerTask_post extends AsyncTask<String, Void, String> {
    private String basic_url= "http://13.209.186.225:8080/api/v1/";
    private String add_url="";
    private URL url;

    public ServerTask_post(String add_url){
        this.add_url=add_url;
        try{
            this.url= new URL(this.basic_url+this.add_url);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

            return res;

        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Log.d("abcd", s);
    }
}
