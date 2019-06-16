package lcal.ltd.popo.com.lcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAdListener;

public class PrivacyPolicyAc extends AppCompatActivity {
    WebView lView;
    AdView adView;
    private com.facebook.ads.InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        adView = new AdView(this, getString(R.string.banner_privacy), AdSize.BANNER_HEIGHT_50);
        LinearLayout bannerAd = findViewById(R.id.banner_containerprivacy);
        bannerAd.addView(adView);
        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(PrivacyPolicyAc.this, "Connection issue" + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        //Request load ad
        adView.loadAd();
        interstitialAd =new com.facebook.ads.InterstitialAd(PrivacyPolicyAc.this,getString(R.string.int_privacy));
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

        lView = findViewById(R.id.lrweb);
        lView.setWebViewClient(new MyBrowser());
        String url = "https://abubakkarsk212.wixsite.com/website";
        lView.getSettings().setLoadsImagesAutomatically(true);
        lView.getSettings().setJavaScriptEnabled(true);
        lView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        lView.loadUrl(url);
    }
    class MyBrowser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {

            view.loadUrl(url);
            return true;
        }

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
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy(); }
        super.onDestroy();
    }
}
