package com.bignerdranch.android.success;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

public class MyMessage extends AppCompatActivity {
    private TextView tv_account,tv_wbnum,tv_fsnum,tv_gznum,tv_sex,tv_phone,tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        String user = getIntent().getStringExtra("user");
        BASE_URL2 = IP + "wbuser/getmsg.php?account="+user;
        new DownloadTask().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        tv_account = (TextView)findViewById(R.id.tv_account);
        tv_fsnum = (TextView)findViewById(R.id.tv_fs);
        tv_gznum = (TextView)findViewById(R.id.tv_gz);
        tv_wbnum = (TextView)findViewById(R.id.tv_wbnum);
        tv_sex = (TextView)findViewById(R.id.tv_sex);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_email = (TextView)findViewById(R.id.tv_email);
        try {
            JSONObject jsonBody = new JSONObject(mess);
            String []t = jsonBody.getString("num").split("#");
            tv_wbnum.setText(t[0]); tv_gznum.setText(t[1]); tv_fsnum.setText(t[2]);
            JSONObject data = jsonBody.getJSONObject("data");
            tv_account.setText(user);
            tv_sex.setText(data.getString("sex"));
            tv_phone.setText(data.getString("phonenumber"));
            tv_email.setText(data.getString("email"));
        } catch (JSONException e) {
        }
    }

}
