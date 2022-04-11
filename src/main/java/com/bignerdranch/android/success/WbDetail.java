package com.bignerdranch.android.success;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

public class WbDetail extends AppCompatActivity {
    String nickName,content,Time,plnum,userid,dznum;
    int id;
    TextView nickname,time,dz,pl;
    EditText et,comet;
    Button combt,bt_gz;
    ImageView ImageDz;
    private List<comment> mCommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_detail);
        id = getIntent().getIntExtra("weiboId",0); nickName = getIntent().getStringExtra("nickname");
        content = getIntent().getStringExtra("content"); Time = getIntent().getStringExtra("time");
        plnum = getIntent().getStringExtra("plnum"); dznum = getIntent().getStringExtra("dznum");
        userid = getIntent().getStringExtra("userId");
        nickname = (TextView)findViewById(R.id.myNickName);
        time = (TextView)findViewById(R.id.time);
        dz = (TextView)findViewById(R.id.dz);
        pl = (TextView)findViewById(R.id.pl);
        et = (EditText)findViewById(R.id.et);
        comet = (EditText)findViewById(R.id.comet);
        combt = (Button)findViewById(R.id.com_bt);
        bt_gz = (Button)findViewById(R.id.bt_gz);
        ImageDz=(ImageView)findViewById(R.id.ImageDz);

        nickname.setText(nickName); time.setText(Time);
        pl.setText(plnum); dz.setText(dznum); et.setText(content);
        BASE_URL2 = IP + "follow/searchfollow.php?fed="+userid+"&fer="+mUser.getAcount();
        new DownloadTask().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        if(userid.equals(mUser.getAcount())){
            bt_gz.setText(""); bt_gz.setEnabled(false);
        }
        if(mess.equals("1")){
            bt_gz.setText("已关注"); bt_gz.setEnabled(false);
        }
        mCommentList = new ArrayList<>();
        init();
        updateUI();
        bt_gz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BASE_URL2 = IP + "follow/insertfollow.php?fed="+userid+"&fer="+mUser.getAcount();
                new DownloadTask().execute();
                bt_gz.setText("已关注");
                bt_gz.setEnabled(false);
            }
        });
        dz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                BASE_URL2 = IP + "dz/insertdz.php?wbid="+id+"&account="+mUser.getAcount()+"&time="+str+"&nickname="+mUser.getNickname();
                new DownloadTask().execute();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                if(mess.equals("1")) {
                    int i = Integer.parseInt(dznum);
                    i++; dznum = i + "";
                    dz.setText(dznum);
                    BASE_URL2 = IP + "wb/adddz.php?id=" + id+"&dznum="+dznum;
                    new DownloadTask().execute();
                    Toast.makeText(WbDetail.this, "点赞成功", Toast.LENGTH_SHORT).show();
                }else if(mess.equals("0")){
                    Toast.makeText(WbDetail.this, "您已点赞过此微博", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(WbDetail.this, "操作错误，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageDz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                BASE_URL2 = IP + "dz/insertdz.php?wbid="+id+"&account="+mUser.getAcount()+"&time="+str+"&nickname="+mUser.getNickname();
                new DownloadTask().execute();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                if(mess.equals("1")) {
                    int i = Integer.parseInt(dznum);
                    i++; dznum = i + "";
                    dz.setText(dznum);
                    BASE_URL2 = IP + "wb/adddz.php?id=" + id+"&dznum="+dznum;
                    new DownloadTask().execute();
                    Toast.makeText(WbDetail.this, "点赞成功", Toast.LENGTH_SHORT).show();
                }else if(mess.equals("0")){
                    Toast.makeText(WbDetail.this, "您已点赞过此微博", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(WbDetail.this, "操作错误，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        combt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String com = comet.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                BASE_URL2 = IP + "comment/insertcomment.php?id="+id+"&commenter="+mUser.getNickname()+"&contents="+com+"&time="+str;
                new DownloadTask().execute();
                comment c = new comment();
                c.setWeiboId(id); c.setAuthor(mUser.getNickname());
                c.setContent(com); c.setTime(str);
                mCommentList.add(c);
                updateUI();
                comet.setText("");
                int i = Integer.parseInt(plnum); i++;
                pl.setText(i+"");
                BASE_URL2 = IP + "wb/addpl.php?id="+id+"&plnum="+i;
                new DownloadTask().execute();
                Toast.makeText(WbDetail.this,"发表成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUI(){
        comAdapter adapter = new comAdapter(WbDetail.this,R.layout.list_item_comment,mCommentList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
    private void init(){
        BASE_URL2 = IP + "comment/getcomments.php?wbId="+id;
        new DownloadTask().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        try {
            JSONArray data = new JSONArray(mess);
            for(int i=0;i<data.length();i++){
                JSONObject comitem = data.getJSONObject(i);
                comment c = new comment();
                c.setAuthor(comitem.getString("commenter"));
                c.setContent(comitem.getString("contents"));
                c.setTime(comitem.getString("time"));
                mCommentList.add(c);
            }
        } catch (JSONException e) {

        }
    }
    private class comAdapter extends ArrayAdapter<comment>{
        private int resourceId;
        public comAdapter(Context context, int textviewResourceId, List<comment> objects){
            super(context,textviewResourceId,objects);
            resourceId = textviewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            comment c = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView tv1,tv2,tv3;
            tv1 = (TextView)view.findViewById(R.id.tv1);
            tv2 = (TextView)view.findViewById(R.id.tv2);
            tv3 = (TextView)view.findViewById(R.id.tv3);
            tv1.setText(c.getAuthor()); tv2.setText(c.getContent()); tv3.setText(c.getTime());
            return view;
        }
    }
}
