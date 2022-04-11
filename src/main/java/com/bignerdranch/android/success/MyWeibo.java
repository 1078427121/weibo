package com.bignerdranch.android.success;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

public class MyWeibo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<weibo> mWeibos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_weibo);
        recyclerView = (RecyclerView) findViewById(R.id.list_mywb);
        initData();
        recyclerView.addItemDecoration(new DividerItemDecoration(MyWeibo.this,DividerItemDecoration.VERTICAL));//设置分割线
        recyclerView.setLayoutManager(new LinearLayoutManager(MyWeibo.this)); //设置布局管理器
        recyclerView.setAdapter(new MyAdapter(MyWeibo.this,mWeibos));    //设置Adapter
    }
    private void initData() {
        mWeibos = new ArrayList<>();
        BASE_URL2 = IP + "wb/getmywbs.php?account="+mUser.getAcount();
        new DownloadTask().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        try {
            JSONArray data = new JSONArray(mess);
            for(int i=0;i<data.length();i++){
                JSONObject wbitem = data.getJSONObject(i);
                weibo wb = new weibo();
                wb.setWeiboId(wbitem.getInt("id"));
                wb.setUserId(wbitem.getString("authoraccount"));
                wb.setNickName(wbitem.getString("author"));
                wb.setContent(wbitem.getString("contents"));
                wb.setTime(wbitem.getString("time"));
                wb.setDznum(wbitem.getInt("dznum"));
                wb.setPlnum(wbitem.getInt("plnum"));
                mWeibos.add(wb);
            }
        } catch (JSONException e) {
        }
    }
}
