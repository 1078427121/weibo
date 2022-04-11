package com.bignerdranch.android.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

public class RegisterActivity extends AppCompatActivity {

    //用户名，密码，再次输入的密码的控件
    private EditText et_nickname,et_user_number,et_psw,et_psw_again,et_email;
    //用户名，密码，再次输入的密码的控件的获取值
    private String nickname,userNumber,psw,pswAgain,userEmail,ssex,account;
    private RadioGroup Sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {

        //从activity_register.xml 页面中获取对应的UI控件
        Button btn_register = (Button) findViewById(R.id.btn_register);
        et_nickname = (EditText)findViewById(R.id.et_nickname);
        et_user_number= (EditText) findViewById(R.id.et_user_number);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_psw_again= (EditText) findViewById(R.id.et_psw_again);
        Sex= (RadioGroup) findViewById(R.id.SexRadio);
        et_email = (EditText) findViewById(R.id.et_email);
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                int sex;
                int sexChoseId = Sex.getCheckedRadioButtonId();
                switch (sexChoseId) {
                    case R.id.mainRegisterRdBtnFemale:
                        sex = 0; ssex="女";
                        break;
                    case R.id.mainRegisterRdBtnMale:
                        sex = 1; ssex="男";
                        break;
                    default:
                        sex = -1;
                        break;
                }
                if(TextUtils.isEmpty(userNumber)){
                    Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(nickname)){
                    Toast.makeText(RegisterActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if (sex<0){
                    Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                }
                else{
                    new download("wbuser/register.php?nickname="+nickname+"&phonenumber="+userNumber+"&password="
                            +psw +"&email="+userEmail+"&sex="+ssex).execute();
                }
            }
        });
    }
    private void dialog(){
        DialogInterface.OnClickListener dialogon = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                RegisterActivity.this.finish();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注册成功！");
        builder.setMessage("请牢记您的账号："+account);
        builder.setPositiveButton("确认",dialogon);
        builder.create().show();
    }
    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        nickname=et_nickname.getText().toString();
        userNumber=et_user_number.getText().toString();
        psw=et_psw.getText().toString();
        pswAgain=et_psw_again.getText().toString();
        userEmail=et_email.getText().toString();
    }
    private class download extends DownloadTask1{
        public download(String url){
            super.url=url;
        }
        @Override
        protected void updateUI(){
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            dialog();
        }
        protected void processData(String jsonData)throws JSONException {
            account = jsonData;
        }
    }
}

