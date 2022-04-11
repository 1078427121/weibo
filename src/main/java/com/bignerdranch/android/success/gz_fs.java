package com.bignerdranch.android.success;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class gz_fs extends AppCompatActivity {
    private List<User> mUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gz_fs);
        mUsers = new ArrayList<>();
        init();
        comAdapter adapter = new comAdapter(gz_fs.this,R.layout.list_item_user,mUsers);
        ListView listView = (ListView)findViewById(R.id.list_gz_fs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View v,int position,long id) {
                Intent i = new Intent(gz_fs.this,MyMessage.class);
                i.putExtra("user",mUsers.get(position).getAcount());
                startActivity(i);
            }
        });
    }
    private void init(){
        String index = getIntent().getStringExtra("index");
        BASE_URL2 = IP + "wbuser/getfs_or_gz.php?index="+index+"&account="+mUser.getAcount();
        new DownloadTask().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        try {
            JSONArray data = new JSONArray(mess);
            for(int i=0;i<data.length();i++){
                JSONObject useritem = data.getJSONObject(i);
                User u = new User();
                u.setAcount(useritem.getString("account"));
                u.setNickname(useritem.getString("nickname"));
                mUsers.add(u);
            }
        } catch (JSONException e) {
        }
    }
    private class comAdapter extends ArrayAdapter<User> {
        private int resourceId;
        public comAdapter(Context context, int textviewResourceId, List<User> objects){
            super(context,textviewResourceId,objects);
            resourceId = textviewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            User u = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView tv1,tv2,tv3;
            tv1 = (TextView)view.findViewById(R.id.tv_item_account);
            tv2 = (TextView)view.findViewById(R.id.tv_item_nickname);
            tv1.setText(u.getAcount()); tv2.setText(u.getNickname());
            return view;
        }
    }
}
