package com.bignerdranch.android.success;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.mess;

public class DownloadTask extends AsyncTask {
    @Override
    protected void onPostExecute(Object msg){
        super.onPostExecute(msg);

    }
    @Override
    protected String doInBackground(Object[] objects) {
        try{
            mess= new WeatherFetchr().getJsonDate();
            Log.d("aaa",mess);
        }catch (IOException ioe){
        }
        return null;
    }
}
class WeatherFetchr {
    public String getJsonDate()throws IOException{
        return getUrlString(BASE_URL2);
    }
    //获取URL的内容
    public String getUrlString(String urlSpec)throws IOException{
        URL url=new URL(urlSpec);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setRequestProperty("Accept-Charset","UTF-8");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        try {
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+":with"+urlSpec);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return new String(out.toByteArray());
        }finally {
            connection.disconnect();
        }
    }
}