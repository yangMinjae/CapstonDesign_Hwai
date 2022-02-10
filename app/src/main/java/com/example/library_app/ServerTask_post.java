package com.example.library_app;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;



public class ServerTask_post extends AsyncTask<String, Void, String> {
    private String basic_url= "http://3.36.81.230:8080/api/v1/";
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
    protected String doInBackground (String... params) {
        return post(params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    private String post(String ... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");

            byte[] outputInBytes = params[0].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write( outputInBytes );
            os.close();
            int retCode = conn.getResponseCode();
            if(retCode==200){
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                StringBuffer response = new StringBuffer();
                while((line=br.readLine())!= null) {
                    response.append(line);
                    response.append('\n');
                }
                br.close();
                String res = response.toString();
                Log.d("test1", res);
                conn.disconnect();
                return res;
            }
            else {
                InputStream is = conn.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = br.readLine()) != null) {
                    response.append(line);
                    response.append('\n');
                }
                br.close();
                String res = response.toString();
                Log.d("test2", res);
                conn.disconnect();
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
            return post(params);
        }
    }

}
