package com.chape.mynet;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chape.mynet.NetWork.Gan.Gan;
import com.chape.mynet.NetWork.Gan.Ganki;
import com.chape.mynet.NetWork.Network;
import com.chape.mynet.utils.NetEvent;
import com.chape.mynet.utils.NetworkChangeReceiver;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NetEvent{
    /**
     * 网络状态
     */
    private int netMobile;

    private TextView mtvNet;

    /**
     * 监控网络的广播
     */
    private NetworkChangeReceiver netBroadcastReceiver;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtvNet = (TextView) findViewById(R.id.tv_net);
        Ganki ganki=Network.initRetrofit().create(Ganki.class);
        Call<Gan> call=ganki.getGan(2,2);
        call.enqueue(new Callback<Gan>() {
            @Override
            public void onResponse(Call<Gan> call, Response<Gan> response) {
                try {
                    System.out.println(response.body().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Gan> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetworkChangeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            /**
             * 设置监听
             */
            netBroadcastReceiver.setNetEvent(this);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    private void isNetConnect() {
        mtvNet.append("\n" + new Date());
        switch (netMobile) {
            case 1://wifi

                mtvNet.append("\n当前网络类型:wifi");
                break;
            case 0://移动数据

                mtvNet.append("\n当前网络类型:移动数据");
                break;
            case -1://没有网络

                mtvNet.append("\n当前无网络");
                break;
        }

        mtvNet.append("\n------------------------------------------------------------------------------------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(netBroadcastReceiver!=null){
            unregisterReceiver(netBroadcastReceiver);
        }
    }
}
