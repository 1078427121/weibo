package com.bignerdranch.android.success;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="weatherFetchr";
    public static String BASE_URL2,IP = "http://10.0.21.212:444/";
    public static User mUser;
    Button mButton;
    TextView tv1,tv2;
    EditText et1,et2;
    private CheckBox rem_pwd;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref;
    public static String mess=null;
    int status;
    JSONObject jsonBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1= (TextView) findViewById(R.id.tv_register);
        tv2= (TextView) findViewById(R.id.tv_find_psw);
        mButton= (Button) findViewById(R.id.btn_login);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        rem_pwd = (CheckBox)findViewById(R.id.cb);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(et1.getText().toString(),et2.getText().toString());

            }
        });
        tv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,LostFindActivity.class);
                startActivity(i);
            }
        });
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            et1.setText(pref.getString("account",""));
            et2.setText(pref.getString("password",""));
            rem_pwd.setChecked(true);
        }
    }
    private void login(String name,String pwd){
        new download("wbuser/login.php?loginName="+name+"&loginPwd="+pwd).execute();
    }
    private class download extends DownloadTask1{
        public download(String url){
            super.url=url;
        }
        @Override
        protected void updateUI(){
            try {
                if(status==1){
                    editor = pref.edit();
                    if(rem_pwd.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",et1.getText().toString());
                        editor.putString("password",et2.getText().toString());
                    }
                    else editor.clear();
                    editor.commit();
                    JSONObject data = jsonBody.getJSONObject("data");
                    String nickname = data.getString("nickname");
                    String sex  =data.getString("sex");
                    String phonenumber = data.getString("phonenumber");
                    String email = data.getString("email");
                    mUser = new User();
                    mUser.setAcount(et1.getText().toString());
                    mUser.setPassword(et2.getText().toString());
                    mUser.setEmail(email);
                    mUser.setNickname(nickname);
                    mUser.setPhonenumber(phonenumber);
                    mUser.setSex(sex);
                    Intent i = new Intent(MainActivity.this,UserActivity.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
            }
        }
        @Override
        protected void processData(String jsonData)throws JSONException{
            Log.i("wuliu","processdata");
            jsonBody = new JSONObject(jsonData);
            status = jsonBody.getInt("status");
        }
    }
}