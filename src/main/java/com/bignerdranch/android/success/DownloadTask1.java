package com.bignerdranch.android.success;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.bignerdranch.android.success.MainActivity.IP;


/**
 * Created by 10907 on 2019/1/15.
 */

public abstract  class DownloadTask1 extends AsyncTask {

    protected String url;


    protected abstract void updateUI();   //更新UI；
    protected abstract void processData(String jsonData)throws JSONException;//解析数据
    public String message;

    @Override
    protected void onPostExecute(Object msg){
        super.onPostExecute(msg);
        updateUI();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        try{

            Log.i("wuliu",url);
            message=getUrlString(url);
            processData(message);
            Log.d("wuliu","succes to get URL json data");
        }catch (IOException ioe){}
        catch (JSONException joe){};

        return null;
    }


    //获取URL的内容

    public String getUrlString(String urlSpec)throws IOException{
        URL url=new URL(IP+urlSpec);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setRequestProperty("Accept-Charset","UF-8");
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