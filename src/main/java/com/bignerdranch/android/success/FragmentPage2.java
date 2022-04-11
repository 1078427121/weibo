package com.bignerdranch.android.success;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bignerdranch.android.success.MainActivity.BASE_URL2;
import static com.bignerdranch.android.success.MainActivity.IP;
import static com.bignerdranch.android.success.MainActivity.mUser;
import static com.bignerdranch.android.success.MainActivity.mess;

/**
 * Created by 10907 on 2019/1/11.
 */
public class FragmentPage2 extends Fragment{
    private RecyclerView recyclerView;
    private List<weibo> mWeibos;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        System.out.println("onCreateView");
        if(rootView == null){
            rootView=inflater.inflate(R.layout.fragment_2, null);
        }

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
        initData();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));//设置分割线
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //设置布局管理器
        recyclerView.setAdapter(new MyAdapter(getActivity(),mWeibos));    //设置Adapter
    }
    private void initData() {
        mWeibos = new ArrayList<>();
        BASE_URL2 = IP + "wb/getfollowwbs.php?account="+mUser.getAcount();
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
            Log.d("aaaaccount",mUser.getAcount());
            Log.d("aaadata",data.length()+"");
            Log.d("aaawb",mWeibos.size()+"");
        } catch (JSONException e) {

        }
    }
}
