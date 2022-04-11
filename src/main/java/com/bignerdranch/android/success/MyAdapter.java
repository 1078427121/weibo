package com.bignerdranch.android.success;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private Context context;
    private List<weibo> mWeibos;


    public MyAdapter(Context context, List<weibo> mWeibos) {
        this.context = context;
        this.mWeibos = mWeibos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_weibo,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String s1;
        final String s2;
        final String s3;
        final String[] s4 = new String[1];
        final String s5;
        final String s6;
        final int id;
        s1 = mWeibos.get(position).getNickName(); s2 = mWeibos.get(position).getContent();
        s3 = mWeibos.get(position).getTime(); s4[0] = mWeibos.get(position).getDznum()+"";
        s5 = mWeibos.get(position).getPlnum()+""; s6 = mWeibos.get(position).getUserId();
        id = mWeibos.get(position).getWeiboId();
        holder.nickname.setText(s1); holder.et.setText(s2);
        holder.time.setText(s3); holder.dz.setText(s4[0]); holder.pl.setText(s5);
        holder.dz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                BASE_URL2 = IP + "dz/insertdz.php?wbid="+mWeibos.get(position).getWeiboId()+"&account="+mUser.getAcount()+"&time="+str+"&nickname="+mUser.getNickname();
                new DownloadTask().execute();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                if(mess.equals("1")) {
                    int i = Integer.parseInt(s4[0]);
                    i++;
                    s4[0] = i + "";
                    holder.dz.setText(s4[0]);
                    BASE_URL2 = IP + "wb/adddz.php?id=" + mWeibos.get(position).getWeiboId()+"&dznum="+s4[0];
                    new DownloadTask().execute();
                    Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
                }else if(mess.equals("0")){
                    Toast.makeText(context, "您已点赞过此微博", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "操作错误，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.ImageDz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                BASE_URL2 = IP + "dz/insertdz.php?wbid="+mWeibos.get(position).getWeiboId()+"&account="+mUser.getAcount()+"&time="+str+"&nickname="+mUser.getNickname();
                new DownloadTask().execute();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                if(mess.equals("1")) {
                    int i = Integer.parseInt(s4[0]);
                    i++;
                    s4[0] = i + "";
                    holder.dz.setText(s4[0]);
                    BASE_URL2 = IP + "wb/adddz.php?id=" + mWeibos.get(position).getWeiboId()+"&dznum="+s4[0];
                    new DownloadTask().execute();
                    Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
                }else if(mess.equals("0")){
                    Toast.makeText(context, "您已点赞过此微博", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "操作错误，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(context,WbDetail.class);
                i.putExtra("weiboId",id); i.putExtra("nickname",s1); i.putExtra("content",s2);
                i.putExtra("time",s3); i.putExtra("plnum",s5); i.putExtra("userId",s6); i.putExtra("dznum", s4[0]);
                context.startActivity(i);
            }
        });
    }
    public void DzBtn(){

    }
    @Override
    public int getItemCount() {
        return mWeibos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nickname,time,dz,pl;
        ImageView ImagePl,ImageDz;
        EditText et;
        public MyViewHolder(View itemView) {
            super(itemView);
            nickname = (TextView)itemView.findViewById(R.id.myNickName);
            time = (TextView)itemView.findViewById(R.id.time);
            dz = (TextView)itemView.findViewById(R.id.dz);
            pl = (TextView)itemView.findViewById(R.id.pl);
            et = (EditText)itemView.findViewById(R.id.et);
            ImagePl=(ImageView)itemView.findViewById(R.id.ImagePl);
            ImageDz=(ImageView)itemView.findViewById(R.id.ImageDz);
        }
    }
}
