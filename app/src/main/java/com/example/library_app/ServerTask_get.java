package com.example.library_app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServerTask_get extends AsyncTask<Void, Void, String> {
    private String basic_url= "http://3.36.81.230:8080/api/v1/";
    private String add_url="";
    private URL url;
    protected int status;
    protected int rtndStatus;

    public ServerTask_get(String add_url, int status){
        this.add_url=add_url;
        this.status = status;
        try{
            this.url= new URL(this.basic_url+this.add_url);
            Log.d("testM", this.url.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return post();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private String post() {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Accept","application/json");

            int retCode = conn.getResponseCode();
            if(retCode==this.status){
                rtndStatus=retCode;
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
                conn.disconnect();
                return res;
            }
            else {
                rtndStatus=retCode;
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
                conn.disconnect();
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
            return post();
        }
    }

}
