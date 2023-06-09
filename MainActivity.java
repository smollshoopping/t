package com.example.pangleads;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAd;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAdLoadListener;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerRequest;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerSize;
import com.bytedance.sdk.openadsdk.api.init.PAGConfig;
import com.bytedance.sdk.openadsdk.api.init.PAGSdk;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest;

public class MainActivity extends AppCompatActivity {

    private PAGInterstitialAd pangInterstitialAd;
    private PAGBannerAd bannerAd;
    private FrameLayout express_container;
    private static long startTime = 0;

    private static PAGConfig buildNewConfig(Context context) {
        return new PAGConfig.Builder()
                .appId("8025677")
                .appIcon(R.mipmap.ic_launcher)
                .debugLog(true)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BannerADS();
        InterstitialAd();

        Button ads=this.findViewById(R.id.ads);
        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ads.setEnabled(false);
                if (pangInterstitialAd != null) {

                    pangInterstitialAd.setAdInteractionListener(new PAGInterstitialAdInteractionListener() {
                        @Override
                        public void onAdShowed() {
                            Log.d("TAG", "Callback --> onAdShowed");

                            ads.setEnabled(true);

                        }

                        @Override
                        public void onAdClicked() {
                            Log.d("TAG", "Callback --> onAdClicked");

                        }

                        @Override
                        public void onAdDismissed() {
                            Log.d("TAG", "Callback --> onAdDismissed");


                        }
                    });

                    pangInterstitialAd.show(MainActivity.this);
                    pangInterstitialAd = null;


                } else {
                    //TToast.show(PAGInterstitialActivity.this, "Please load the ad first !");

                }
            }
        });

    }



    private void BannerADS(){
        PAGConfig pAGInitConfig = buildNewConfig(this);
        PAGSdk.init(this, pAGInitConfig, new PAGSdk.PAGInitCallback() {
            @Override
            public void success() {
                //load pangle ads after this method is triggered.
                Log.i("TAG", "pangle init success: ");
            }

            @Override
            public void fail(int code, String msg) {
                Log.i("TAG", "pangle init fail: " + code);
            }
        });

        express_container = this.findViewById(R.id.express_container);
        express_container.removeAllViews();
        PAGBannerSize bannerSize = PAGBannerSize.BANNER_W_320_H_50;
        PAGBannerRequest bannerRequest = new PAGBannerRequest(bannerSize);
        PAGBannerAd.loadAd("980099802", bannerRequest, new PAGBannerAdLoadListener() {
            @Override
            public void onError(int code, String message) {
                //TToast.show(MainActivity.this, "load error : " + code + ", " + message);
                express_container.removeAllViews();
            }

            @Override
            public void onAdLoaded(PAGBannerAd bannerAd) {
                if (bannerAd == null) {
                    return;
                }
                bannerAd = bannerAd;
                //TToast.show(MainActivity.this, "Load Ad Success");
                bindAdListener(bannerAd);

                if (bannerAd != null) {
                    express_container.addView(bannerAd.getBannerView());
                }
                startTime = System.currentTimeMillis();
                //TToast.show(MainActivity.this, "load success!");


            }
        });
    }
    private static void bindAdListener(PAGBannerAd ad) {
        ad.setAdInteractionListener(new PAGBannerAdInteractionListener() {
            @Override
            public void onAdShowed() {
                //TToast.show(MainActivity.this, "Ad showed");
            }

            @Override
            public void onAdClicked() {
                //TToast.show(MainActivity.this, "Ad clicked");
            }

            @Override
            public void onAdDismissed() {
                Log.e("ExpressView", "Ad dismissed" + (System.currentTimeMillis() - startTime));
                //TToast.show(MainActivity.this, "Ad dismissed");
            }
        });
    }

    private void InterstitialAd(){

        PAGConfig pAGInitConfig = buildNewConfig(this);
        PAGSdk.init(this, pAGInitConfig, new PAGSdk.PAGInitCallback() {
            @Override
            public void success() {
                //load pangle ads after this method is triggered.
                Log.i("TAG", "pangle init success: ");
            }

            @Override
            public void fail(int code, String msg) {
                Log.i("TAG", "pangle init fail: " + code);
            }
        });

        PAGInterstitialRequest request = new PAGInterstitialRequest();
        PAGInterstitialAd.loadAd("980088188",
                new PAGInterstitialRequest(),
                new PAGInterstitialAdLoadListener() {
                    @Override
                    public void onError(int code, String message) {
                        Log.e("TAG", "Callback --> onError: " + code + ", " + String.valueOf(message));
                        //TToast.show(PAGInterstitialActivity.this, message);
                    }

                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onAdLoaded(PAGInterstitialAd pagInterstitialAd) {
                        //TToast.show(PAGInterstitialActivity.this, "InterstitialAd loaded");
                        pangInterstitialAd = pagInterstitialAd;

                    }
                });

    }
}