package com.bignerdranch.android.success;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

public class Com_and_dz extends AppCompatActivity {
    private class event{
        private String nickname;
        private String thing;
        private String time;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void event(){
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getThing() {
            return thing;
        }

        public void setThing(String thing) {
            this.thing = thing;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    private List<event> mEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_and_dz);
        mEvents = new ArrayList<>();
        init();
        comAdapter adapter = new comAdapter(Com_and_dz.this,R.layout.list_item_event,mEvents);
        ListView listView = (ListView)findViewById(R.id.list_com_dz);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View v,int position,long id) {
                String s1="",s2="",s3="",s6="",s4="",s5="",wbid="";
                BASE_URL2 = IP + "wb/getwbbyid.php?account="+mEvents.get(position).getId();
                new DownloadTask().execute();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                try {
                    JSONObject wbitem = new JSONObject(mess);
                    wbid = wbitem.getString("id");
                    s6=wbitem.getString("authoraccount");
                    s1=wbitem.getString("author");
                    s2=wbitem.getString("contents");
                    s3=wbitem.getString("time");
                    s4=wbitem.getString("dznum");
                    s5=wbitem.getString("plnum");
                } catch (JSONException e) {
                    Log.d("aaa","error");
                }
                Intent i = new Intent(Com_and_dz.this,WbDetail.class);
                i.putExtra("weiboId",Integer.parseInt(wbid)); i.putExtra("nickname",s1); i.putExtra("content",s2);
                i.putExtra("time",s3); i.putExtra("plnum",s5); i.putExtra("userId",s6); i.putExtra("dznum",s4);
                startActivity(i);
            }
        });
    }
    private void init(){
        BASE_URL2 = IP + "wbuser/com_and_dz.php?account="+mUser.getAcount();
        new DownloadTask().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        try {
            String [] t = mess.split("#");
            if(!t[0].equals("")) {
                JSONArray data = new JSONArray(t[0]);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject eventitem = data.getJSONObject(i);
                    event e = new event();
                    e.setNickname(eventitem.getString("nickname"));
                    e.setThing("点赞了您的微博");
                    e.setTime(eventitem.getString("time"));
                    e.setId(eventitem.getString("wbid"));
                    mEvents.add(e);
                }
            }
            if(!t[1].equals("")) {
                JSONArray data = new JSONArray(t[1]);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject eventitem = data.getJSONObject(i);
                    event e = new event();
                    e.setNickname(eventitem.getString("commenter"));
                    e.setThing("评论了您的微博");
                    e.setTime(eventitem.getString("time"));
                    e.setId(eventitem.getString("id"));
                    mEvents.add(e);
                }
            }
            Log.d("aaa",mEvents.size()+"");
        } catch (JSONException e) {
        }
    }
    private class comAdapter extends ArrayAdapter<event> {
        private int resourceId;
        public comAdapter(Context context, int textviewResourceId, List<event> objects){
            super(context,textviewResourceId,objects);
            resourceId = textviewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            event e = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView tv1,tv2,tv3;
            tv1 = (TextView)view.findViewById(R.id.account);
            tv2 = (TextView)view.findViewById(R.id.tv_event);
            tv3 = (TextView)view.findViewById(R.id.Ctime);
            tv1.setText(e.getNickname()); tv2.setText(e.getThing()); tv3.setText(e.getTime());
            return view;
        }
    }
}
