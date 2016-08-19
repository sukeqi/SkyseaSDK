package com.skysea.sdk.main;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.skysea.android.app.lib.MResource;
import com.skysea.bean.Item;
import com.skysea.interfaces.IDispatcherCallback;
import com.skysea.sdk.R;
import com.skysea.utils.Utils;
import com.skysea.view.FragmentLayoutWithLine;
import com.skysea.view.ViewHolder;

public class PaymentInfoActivity extends FragmentActivity implements
        OnClickListener {

    String userid;
    String gameid;
    String gameserverid;
    String totlesMoney;

    String xb_orderid;
    static IDispatcherCallback callback;

    ImageView back;
    TextView totalMoney;
    FragmentLayoutWithLine checkLine;
    ProgressDialog pd_pay;

    ListView listTab;
    List<Item> datas = new ArrayList<Item>();
    TextView totalMoneys;
    TextView payWay;
    Button paywaybtn;
    private int[] tab_text = {R.id.tab_text1, R.id.tab_text2, R.id.tab_text3, R.id.tab_text4};

    public static String[] tabs = {"银行卡", "支付宝", "微信", "充值卡"};
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getIntentArgs(getIntent());

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setContentView(R.layout.paymentinfos);
            initView();
        } else if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.paymentinfo);
            initViews();
        }
        super.onResume();
    }

    private void initView() {
        listTab = (ListView) findViewById(R.id.tab_list);
        totalMoneys = (TextView) findViewById(R.id.totalMoney);
        payWay = (TextView) findViewById(R.id.payway);
        paywaybtn = (Button) findViewById(R.id.paywaybtn);
        datas.add(new Item("银行卡"));
        datas.add(new Item("支付宝"));
        datas.add(new Item("微信"));
        datas.add(new Item("充值卡"));
        datas.get(0).isSelect = true;
        payWay.setText("确认无误后去" + datas.get(0).name + "付款");
        paywaybtn.setText("去" + datas.get(0).name + "付款");
        final BaseAdapter adapter = new CommonAdapter<Item>(this, datas, R.layout.list_tab) {
            @Override
            public void convert(ViewHolder holder, Item item, int position) {
                holder.setText(R.id.text, item.name);
                if (item.isSelect) {
                    ((TextView) holder.getView(R.id.text)).setTextColor(0xfffa832d);
                    ((TextView) holder.getView(R.id.text)).setBackgroundColor(0xfff2f2f2);
                } else {
                    ((TextView) holder.getView(R.id.text)).setTextColor(0xff8c8c8c);
                    ((TextView) holder.getView(R.id.text)).setBackgroundColor(0xfff2f2f2);
                }
            }
        };
        listTab.setAdapter(adapter);
        listTab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < datas.size(); i++) {
                    if (i == position) {
                        datas.get(i).isSelect = true;
                    } else {
                        datas.get(i).isSelect = false;
                    }
                }
                adapter.notifyDataSetChanged();
                totalMoneys.setText(totalMoneys.getText());

                if (datas.get(0).isSelect) {
                    payWay.setText("确认无误后去" + datas.get(0).name + "付款");
                    paywaybtn.setText("去" + datas.get(0).name + "付款");
                } else if (datas.get(1).isSelect) {
                    payWay.setText("确认无误后去" + datas.get(1).name + "付款");
                    paywaybtn.setText("去" + datas.get(1).name + "付款");
                } else if (datas.get(2).isSelect) {
                    payWay.setText("确认无误后去" + datas.get(2).name + "付款");
                    paywaybtn.setText("去" + datas.get(2).name + "付款");
                } else if (datas.get(3).isSelect) {
                    payWay.setText("确认无误后去" + datas.get(3).name + "付款");
                    paywaybtn.setText("去" + datas.get(3).name + "付款");
                }
            }
        });
    }

    private void getIntentArgs(Intent intent) {
        try {
            userid = intent.getExtras().getString("userid");
            gameid = intent.getExtras().getString("gameid");
            gameserverid = intent.getExtras().getString("gameserverid");
            xb_orderid = intent.getExtras().getString("xb_orderid");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            PaymentInfoActivity.this.finish();
            anim();
        }
        return false;
    }

    static public void setDispatcherCallBack(IDispatcherCallback listener) {
        callback = listener;
    }

    private void initViews() {
        back = (ImageView) findViewById(R.id.back);
        totalMoney = (TextView) findViewById(R.id.totalMoney);
        checkLine = (FragmentLayoutWithLine) findViewById(R.id.checkLine);
        back.setOnClickListener(this);
        for (int i = 0; i < tabs.length; i++) {
            Bundle data = new Bundle();
            data.putString("text", tabs[i]);
            data.putString("userid", userid);
            data.putString("gameid", gameid);
            data.putString("gameserverid", gameserverid);
            data.putString("xb_orderid", xb_orderid);
            data.putString("totlesMoney", totlesMoney);
            if (isFinishing()) {
                return;
            }
            Fragments fragmentses = new Fragments();
            fragmentses.setArguments(data);
            fragments.add(fragmentses);
        }

        checkLine.setScorllToNext(true);
        checkLine.setScorll(true);
        checkLine.setWhereTab(1);
        checkLine.setTabHeight(6, 0xfffa832d);//下划线的高度和颜色
        checkLine.setOnChangeFragmentListener(new FragmentLayoutWithLine.ChangeFragmentListener() {
            @Override
            public void change(int lastPosition, int position, View lastTabView, View currentTabView) {
                ((TextView) lastTabView.findViewById(tab_text[lastPosition])).setTextColor(0xff8c8c8c);//未选中的字体颜色
                ((TextView) currentTabView.findViewById(tab_text[position])).setTextColor(0xfffa832d);//选中的字体颜色
                lastTabView.setBackgroundColor(0xffffffff);//未选中的背景色
                currentTabView.setBackgroundColor(0xffffffff);//选中的背景色

            }
        });
        checkLine.setAdapter(fragments, R.layout.tablayout_nevideo_player, 0x0102);
        checkLine.getViewPager().setOffscreenPageLimit(3);//设置tab数量 4个的话就设置3，比tab数量少1

    }

    @Override
    protected void onDestroy() {
        Utils.dismiss(pd_pay);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                anim();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200) {
            setResult(200, data);
            callback.onFinish(data.getExtras().getString("tradeStatus"));
            PaymentInfoActivity.this.finish();
        }
    }

    private void anim() {
        overridePendingTransition(MResource.getIdByName(
                PaymentInfoActivity.this, "anim", "page_from_alpha"),
                MResource.getIdByName(PaymentInfoActivity.this, "anim",
                        "page_left_alpha"));
    }

}
