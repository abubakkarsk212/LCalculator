package lcal.ltd.popo.com.lcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAdListener;

public class About extends AppCompatActivity {
    AdView adView;
    private com.facebook.ads.InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        adView = new AdView(this, getString(R.string.banner_about), AdSize.BANNER_HEIGHT_50);
        LinearLayout bannerAd = findViewById(R.id.banner_container_about);
        bannerAd.addView(adView);
        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(About.this, "Connection issue" + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        //Request load ad
        adView.loadAd();
        interstitialAd =new com.facebook.ads.InterstitialAd(About.this,getString(R.string.interstitial_about));
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }
            @Override
            public void onInterstitialDismissed(Ad ad) {
            }
            @Override
            public void onError(Ad ad, AdError adError) {
            }
            @Override
            public void onAdLoaded(Ad ad) {
            }
            @Override
            public void onAdClicked(Ad ad) {
            }
            @Override
            public void onLoggingImpression(Ad ad) {
            }
        });
        interstitialAd.loadAd();
    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy(); }
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {

        if (interstitialAd.isAdLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AbstractAdListener() {
                @Override
                public void onInterstitialDismissed(Ad ad) {
                    super.onInterstitialDismissed(ad);
                    finish();
                }}); }
        else {
            super.onBackPressed(); }
    }
}
