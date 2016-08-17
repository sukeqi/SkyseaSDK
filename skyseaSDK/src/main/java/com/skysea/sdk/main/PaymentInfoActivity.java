package com.skysea.sdk.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skysea.android.app.lib.MResource;
import com.skysea.app.BaseActivity;
import com.skysea.async.AutoCancelController;
import com.skysea.async.AutoCancelServiceFramework;
import com.skysea.async.Cancelable;
import com.skysea.bean.CItem;
import com.skysea.bean.OrderInfo;
import com.skysea.exception.ResponseException;
import com.skysea.interfaces.IDispatcherCallback;
import com.skysea.sdk.R;
import com.skysea.utils.UtilTools;
import com.skysea.utils.Utils;

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
    RadioGroup paymentTab;
    RadioButton bankCard;
    RadioButton AliPay;
    RadioButton Wechat;
    RadioButton rechargeCart;
    ImageView checkLine;
    ViewPager viewpager;
    ProgressDialog pd_pay;
    private MainPagerAdapter adapter;

    public static String[] tabs = {"银行卡", "支付宝", "微信", "充值卡"};
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.paymentinfo);
        getIntentArgs(getIntent());
        initViews();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
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
        paymentTab = (RadioGroup) findViewById(R.id.paymentTab);
        bankCard = (RadioButton) findViewById(R.id.bankCard);
        AliPay = (RadioButton) findViewById(R.id.AliPay);
        Wechat = (RadioButton) findViewById(R.id.Wechat);
        rechargeCart = (RadioButton) findViewById(R.id.rechargeCard);
        checkLine = (ImageView) findViewById(R.id.checkLine);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        back.setOnClickListener(this);
        totlesMoney = (String) totalMoney.getText();

        paymentTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.bankCard:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.AliPay:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.Wechat:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.rechargeCard:
                        viewpager.setCurrentItem(3);
                        break;
                }
            }
        });
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

        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(pageListener);
        viewpager.setCurrentItem(1);
        AliPay.setChecked(true);
        initTabStrip();
        initData();

    }

    private void initData() {
        //请求接口数据,请求成功之后调用以下方法
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager fm;
        private ArrayList<Fragment> fragments;

        public MainPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }


    /**
     * 切换监听
     */
    int lastPosition = 0;
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            selectTab(position);
            TranslateAnimation animation = new TranslateAnimation(lastPosition * (UtilTools.getScreenSize()[0] / 4),
                    position * (UtilTools.getScreenSize()[0] / 4), 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            checkLine.startAnimation(animation);
            lastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 选择对应的tab页
     *
     * @param position
     */
    private void selectTab(int position) {
        switch (position) {
            case 0:
                bankCard.setChecked(true);
                break;
            case 1:
                AliPay.setChecked(true);
                break;
            case 2:
                Wechat.setChecked(true);
                break;
            case 3:
                rechargeCart.setChecked(true);
                break;
        }
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

    private void initTabStrip() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.line);
        //屏幕宽度
        int winWidth = UtilTools.getScreenSize()[0];
        //图片宽度
        int bitmapWidth = bitmap.getWidth();
        //计算出的偏移量
        int offset = (winWidth / 4 - bitmapWidth) / 2;

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        checkLine.setImageMatrix(matrix);
        checkLine.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = checkLine.getLayoutParams();
        layoutParams.width = UtilTools.getScreenWidth(getApplicationContext()) / 4;
        checkLine.setLayoutParams(layoutParams);

    }


}
