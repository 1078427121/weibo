package com.bignerdranch.android.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mess;

public class LostFindActivity extends AppCompatActivity {
    private EditText et1,et2,et3,et4,et5;
    private String s1,s2,s3,s4,s5;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 = (EditText)findViewById(R.id.et4);
        et5 = (EditText)findViewById(R.id.et5);
        bt = (Button)findViewById(R.id.btn_reset);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                s1 = et1.getText().toString();
                s2 = et2.getText().toString();
                s3 = et3.getText().toString();
                s4 = et4.getText().toString();
                s5 = et5.getText().toString();
                if(!s4.equals(s5)) Toast.makeText(LostFindActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                else if(s1.isEmpty()) Toast.makeText(LostFindActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                else if(s2.isEmpty()) Toast.makeText(LostFindActivity.this,"请输入手机号码",Toast.LENGTH_SHORT).show();
                else if(s3.isEmpty()) Toast.makeText(LostFindActivity.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
                else{
                    BASE_URL2 = IP + "wbuser/resetpwd.php?account="+s1+"&password="+s4+"&phonenumber="+s2+"&email="+s3;
                    new DownloadTask().execute();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    if(mess.equals("1")) {
                        dialog("重置成功！",1);
                    }else dialog("信息错误！",0);
                }
            }
        });
    }
    private void dialog(String s, final int flag){
        DialogInterface.OnClickListener dialogon = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                if(flag==1) finish();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.setPositiveButton("确认",dialogon);
        builder.create().show();
    }
}
