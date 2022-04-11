package com.bignerdranch.android.success;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;

public class Add_wb extends AppCompatActivity {
    EditText tv_addwb;
    Button bt_addwb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wb);
        tv_addwb = (EditText)findViewById(R.id.et_addwb);
        bt_addwb = (Button)findViewById(R.id.bt_addwb);
        bt_addwb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String content = tv_addwb.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                BASE_URL2 = IP + "wb/insertwb.php?account="+mUser.getAcount()+"&author="+mUser.getNickname()+"&contents="+content +"&time="+str;
                new DownloadTask().execute();
                tv_addwb.setText("");
                Toast.makeText(Add_wb.this,"发布成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
