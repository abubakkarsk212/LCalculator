package lcal.ltd.popo.com.lcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner inputS,outputS,selectState;
    String[] inputArea ={"Select unit","Ankanam","Bigha","Biswa","Biswansi","Cent","Dhur","Ghumaon","Ground","Gunta","Hectare","Kanal","Kattha","Killa","Kuncham","Lecha","Sq Feet","Sq Centimeters","Sq Inch","Sq Kilometer","Sq Meter","Sq Mile","Sq Yard","Sarsahi (Sq Karam)"};
    String[] outputArea ={"Select unit","Acre","Ankanam","Bigha","Biswa","Biswansi","Cent","Dhur","Ghumaon","Ground","Gunta","Hectare","Kanal","Kattha","Killa","Kuncham","Lecha","Sq Feet","Sq Centimeters","Sq Inch","Sq Kilometer","Sq Meter","Sq Mile","Sq Yard","Sarsahi (Sq Karam)"};
    ArrayList<String> commonList,outputList,stateList;
    EditText inputAreaEt;
    TextView inputT,outputT,inputA,outputA;
    Button reset,convrt;
    String inputAreaUnit = "";
    String outputAreaUnit,outPutAreaForS = "";
    String stateS,stateForStatewise = "";
    double ans;
    RadioButton commonWise,stateWise;
    ArrayAdapter arrayAdapterIp,arrayAdapterOp,arrayAdapterState;
    boolean isClicked = false;
    private static final String myDynamicLink = "http://play.google.com/store/apps/details?id=lcal.ltd.popo.com.lcalculator";
    //Facebook Ads Sdk implementation
    private AdView adView;
    NativeBannerAd nativeBannerAd1;
    NativeAd nativeAd;
    LinearLayout nativeAdContainer,adV;
    private com.facebook.ads.InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        inputS = findViewById(R.id.areaToBeConvert);
        outputS = findViewById(R.id.areaConverted);
        selectState = findViewById(R.id.statesSpinner);
        inputAreaEt = findViewById(R.id.inputET);
        inputT = findViewById(R.id.inputText);
        inputA = findViewById(R.id.inputAns);
        outputT = findViewById(R.id.outputText);
        outputA = findViewById(R.id.outputAns);
        reset = findViewById(R.id.resetB);
        convrt = findViewById(R.id.convertB);
        commonWise = findViewById(R.id.radioButtonCommon);
        stateWise = findViewById(R.id.radioButtonState);
        commonList = new ArrayList<>();
        outputList = new ArrayList<>();
        stateList = new ArrayList<>();


        adView = new AdView(this, getString(R.string.banner_real), AdSize.BANNER_HEIGHT_50);
      LinearLayout  bannerAd = findViewById(R.id.banner_container);
        bannerAd.addView(adView);
        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(MainActivity.this, "Connection issue" + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        //Request load ad
        adView.loadAd();

        interstitialAd =new com.facebook.ads.InterstitialAd(MainActivity.this,getString(R.string.int_real));
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
        nativeBannerAd1= new NativeBannerAd(MainActivity.this, getString(R.string.native_banner_real));
        nativeBannerAd1.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView1 = NativeBannerAdView.render(MainActivity.this, nativeBannerAd1, NativeBannerAdView.Type.HEIGHT_100);
                LinearLayout nativeBannerAdContainer1 = findViewById(R.id.native_banksaxisB3);
                // Add the Native Banner Ad View to your ad container
                nativeBannerAdContainer1.addView(adView1);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeBannerAd1.loadAd();

        nativeAd = new NativeAd(this, getString(R.string.native_banner_real));
        //623848994653940_623855291319977
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
//                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
//                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
//                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
//                Log.d(TAG, "Native ad impression logged!");
            }
        });

//         Request an ad
        nativeAd.loadAd();

        commonWise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    isClicked = true;
                    if(commonList.size() != 0){
                        clearInputAdapter();
                        arrayAdapterOp.notifyDataSetChanged();
                        outputS.setAdapter(null);
                    }
                        commonList.add(new String("Acre"));
                        commonList.add(new String("Hectare"));
                        commonList.add(new String("Sq Feet"));
                        commonList.add(new String("Sq Centimeters"));
                        commonList.add(new String("Sq Inch"));
                        commonList.add(new String("Sq Kilometer"));
                        commonList.add(new String("Sq Meter"));
                        commonList.add(new String("Sq Mile"));
                        commonList.add(new String("Sq Yard"));

                        arrayAdapterIp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, commonList);
                        arrayAdapterOp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, commonList);
                        inputS.setAdapter(arrayAdapterIp);
                        outputS.setAdapter(arrayAdapterOp);

                }
                else{
                    isClicked = false;
                    clearInputAdapter();
                    arrayAdapterOp.notifyDataSetChanged();
                    outputS.setAdapter(null);
                }
            }
        });
        stateWise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    stateList.add(new String("Andhra Pradesh"));
                    stateList.add(new String("Assam"));
                    stateList.add(new String("Bihar"));
                    stateList.add(new String("Gujarat"));
                    stateList.add(new String("Haryana"));
                    stateList.add(new String("Himachal Pradesh"));
                    stateList.add(new String("Jammu & Kashmir"));
                    stateList.add(new String("Jharkhand"));
                    stateList.add(new String("Karnataka"));
                    stateList.add(new String("Kerala"));
                    stateList.add(new String("Madhya Pradesh"));
                    stateList.add(new String("Maharashtra"));
                    stateList.add(new String("Punjab"));
                    stateList.add(new String("Rajasthan"));
                    stateList.add(new String("Tamil Nadu"));
                    stateList.add(new String("Tripura"));
                    stateList.add(new String("Uttar Pradesh"));
                    stateList.add(new String("Uttarakhand"));
                    stateList.add(new String("West Bengal"));

                    arrayAdapterState = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,stateList);
                    selectState.setAdapter(arrayAdapterState);
                    selectState.setVisibility(View.VISIBLE);

//                    if(stateS.equals("Andhra Pradesh")) {


//                    }
//                    else {
//
//                    }
                }
                else {
                    stateList.clear();
                    arrayAdapterState.notifyDataSetChanged();
                    selectState.setAdapter(null);
                    selectState.setVisibility(View.GONE);
                }
            }

        });



        inputS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputAreaUnit = commonList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        outputS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(outputList.size() != 0){
                    outPutAreaForS = outputList.get(i);
                }
                else {
                    outputAreaUnit = commonList.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                stateS = statesW[i];
                if(i == 0){
                    if(commonList.size() !=0){
                        clearInputAdapter();
                    }
                    stateForStatewise = "Andhra Pradesh";
                    commonList.add(new String("Ankanam"));
                    commonList.add(new String("Guntha"));
                    commonList.add(new String("Kuncham"));


                    outputList.add(new String("Acre"));
                    outputList.add(new String("Hectare"));
                    outputList.add(new String("Sq Feet"));
                    outputList.add(new String("Sq Centimeters"));
                    outputList.add(new String("Sq Inch"));
                    outputList.add(new String("Sq Kilometer"));
                    outputList.add(new String("Sq Meter"));
                    outputList.add(new String("Sq Mile"));
                    outputList.add(new String("Sq Yard"));
                    loadInputAdapter();
                    arrayAdapterOp = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,outputList);
                    outputS.setAdapter(arrayAdapterOp);
                }
                else if(i == 1){
                    stateForStatewise = "Assam";
                    clearInputAdapter();
                    commonList.add(new String("Bigha"));
                    commonList.add(new String("Kattha"));
                    commonList.add(new String("Lecha"));
                    loadInputAdapter();
                }
                else if(i == 2){
                    stateForStatewise = "Bihar";
                    clearInputAdapter();
                    commonList.add(new String("Bigha"));
                    commonList.add(new String("Dhur"));
                    commonList.add(new String("Kattha"));
                    loadInputAdapter();
                }
                else if(i == 3){
                    stateForStatewise = "Gujarat";
                    clearInputAdapter();
                    commonList.add(new String("Bigha"));
                    loadInputAdapter();
                }
                else if(i == 4){
                    stateForStatewise = "Haryana";
                    clearInputAdapter();
                    commonList.add(new String("Bigha Pakka"));
                    commonList.add(new String("Bigha Kachha"));
                    commonList.add(new String("Biswa Pakka"));
                    commonList.add(new String("Biswa Kachha"));
                    commonList.add(new String("Ghumaon"));
                    commonList.add(new String("Killa"));
                    commonList.add(new String("Kanal"));
                    commonList.add(new String("Sarsahi (Square Karam)"));
                    loadInputAdapter();
                }
                else if(i == 5){
                    stateForStatewise = "Himachal Pradesh";
                    clearInputAdapter();
                    commonList.add(new String("Bigha HP-1"));
                    commonList.add(new String("Bigha HP-2"));
                    commonList.add(new String("Biswa HP-1"));
                    commonList.add(new String("Biswa HP-2"));
                    commonList.add(new String("Ghumaon"));
                    commonList.add(new String("Kanal"));
                    commonList.add(new String("Sarsahi (Square Karam)"));
                    loadInputAdapter();
                }
                else if(i == 6){
                    stateForStatewise = "Jammu & Kashmir";
                    clearInputAdapter();
                    commonList.add(new String("Kanal"));
                    loadInputAdapter();
                }
                else if(i == 7){
                    stateForStatewise = "Jharkhand";
                    clearInputAdapter();
                    commonList.add(new String("Dhur"));
                    loadInputAdapter();
                }
                else if(i == 8){
                    stateForStatewise = "Karnataka";
                    clearInputAdapter();
                    commonList.add(new String("Ankanam"));
                    commonList.add(new String("Cent"));
                    commonList.add(new String("Guntha"));
                    loadInputAdapter();
                }
                else if(i == 9){
                    stateForStatewise = "Kerala";
                    clearInputAdapter();
                    commonList.add(new String("Cent"));
                    loadInputAdapter();
                }
                else if(i == 10){
                    stateForStatewise = "Madhya Pradesh";
                    clearInputAdapter();
                    commonList.add(new String("Bigha"));
                    commonList.add(new String("Kattha"));
                    loadInputAdapter();
                }
                else if(i == 11){
                    stateForStatewise = "Maharashtra";
                    clearInputAdapter();
                    commonList.add(new String("Guntha"));
                    loadInputAdapter();
                }
                else if(i == 12) {
                    stateForStatewise = "Punjab";
                    clearInputAdapter();
                    commonList.add(new String("Bigha Pakka"));
                    commonList.add(new String("Bigha Kachha"));
                    commonList.add(new String("Biswa Pakka"));
                    commonList.add(new String("Biswa Kachha"));
                    commonList.add(new String("Ghumaon"));
                    commonList.add(new String("Killa"));
                    commonList.add(new String("Kanal"));
                    commonList.add(new String("Sarsahi (Square Karam)"));
                    loadInputAdapter();
                }
                else if(i == 13) {
                    stateForStatewise = "Rajasthan";
                    clearInputAdapter();
                    commonList.add(new String("Bigha Rajasthan-1"));
                    commonList.add(new String("Bigha Rajasthan-2"));
                    commonList.add(new String("Biswa (Upper parts)"));
                    commonList.add(new String("Biswa (Lower parts)"));
                    loadInputAdapter();
                }
                else if(i == 14) {
                    stateForStatewise = "Tamil Nadu";
                    clearInputAdapter();
                    commonList.add(new String("Cent"));
                    commonList.add(new String("Ground"));
                    loadInputAdapter();
                }
                else if(i == 15) {
                    stateForStatewise = "Tripura";
                    clearInputAdapter();
                    commonList.add(new String("Dhur"));
                    loadInputAdapter();
                }
                else if(i == 16) {
                    stateForStatewise = "Uttar Pradesh";
                    clearInputAdapter();
                    commonList.add(new String("Bigha Pakka"));
                    commonList.add(new String("Bigha Kachha"));
                    commonList.add(new String("Biswa Pakka"));
                    commonList.add(new String("Biswa Kachha"));
                    loadInputAdapter();
                }
                else if(i == 17) {
                    stateForStatewise = "Uttarakhand";
                    clearInputAdapter();
                    commonList.add(new String("Bigha Uttarkhand-1"));
                    commonList.add(new String("Bigha Uttarkhand-2"));
                    commonList.add(new String("Biswa Uttarkhand-1"));
                    commonList.add(new String("Biswa Uttarkhand-2"));
                    loadInputAdapter();
                }
                else if(i == 18) {
                    stateForStatewise = "West Bengal";
                    clearInputAdapter();
                    commonList.add(new String("Bigha"));
                    commonList.add(new String("Chatak"));
                    commonList.add(new String("Decimal"));
                    loadInputAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        convrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (commonWise.isChecked() == false && stateWise.isChecked() == false) {
                    Toast.makeText(MainActivity.this, "Please select Common Unit or State Wise Unit", Toast.LENGTH_SHORT).show();
                } else {
                    String value = inputAreaEt.getText().toString();
                    if (value.equals("")) {
                        Toast.makeText(MainActivity.this, "Please enter some value", Toast.LENGTH_SHORT).show();
                    } else {


                        double a = Double.parseDouble(value);
                        // Acre--- Acre,Hectre....
                        if (inputAreaUnit.equals("Acre")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 0.4 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 43560 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 40468564.224 * a;
                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 6272640 * a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.0040468564 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 4046.8564224 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.0015625 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 4840 * a;
                            }

                        }
                        //Hectre--Acre,Hectre...
                        if (inputAreaUnit.equals("Hectare")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 2.47105 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 107639 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 100000000 * a;
                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 15500031 * a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.01 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 10000 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.00386102 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 11959.9 * a;
                            }

                        }
                        //Sq feet -- Acre,Hectre....
                        if (inputAreaUnit.equals("Sq Feet")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 0.0000229 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 0.00000929 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 929 * a;
                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 144 * a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.0000000929 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 0.092903 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.0000000358 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 0.11111111 * a;
                            }

                        }
                        //Sq cm --- Acre,Hectre....
                        if (inputAreaUnit.equals("Sq Centimeters")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 0.0000000247 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 0.00000001 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 0.001076 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 0.155 * a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.0000000001 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 0.0001 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.0000000000386 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 0.0001195 * a;
                            }

                        }
                        //Sq Inch -- Acre,Hectre...
                        if (inputAreaUnit.equals("Sq Inch")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 0.000000159 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 0.0000000645 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 0.0069444 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 6.4516 * a;
                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.000000000645 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 0.00064516 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.000000000249 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 0.0007716 * a;
                            }
                        }
                        //Sq Km -- Acre,Hectre...
                        if (inputAreaUnit.equals("Sq Kilometer")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 247.10538 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 100 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 10763910 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 1000000000 * a;
                                ans = ans * 10;
                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 1550003100 * a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 1000000 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.3861 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 1195990 * a;
                            }
                        }
                        //Sq m -- Acre,Hectre...
                        if (inputAreaUnit.equals("Sq Meter")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 0.000247 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 0.0001 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 10.7639 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 10000 * a;

                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 1550 * a;
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.000001 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.0000003861 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 1.19599 * a;
                            }
                        }

                        //Sq mile -- Acre,Hectre...
                        if (inputAreaUnit.equals("Sq Mile")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 640 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 258.998 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 27878400 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 258998810 * a;
                                ans = ans * 100;

                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 401448960 * a;
                                ans = ans * 10;
                                //check
                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 1550003100 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 1000000 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = 1195990 * a;
                            }
                        }
                        //Sq yard -- Acre,Hectre...
                        if (inputAreaUnit.equals("Sq Yard")) {
                            if (outputAreaUnit.equals("Acre")) {
                                ans = 0.00020661157 * a;

                            } else if (outputAreaUnit.equals("Hectare")) {
                                ans = 0.00008361 * a;
                            } else if (outputAreaUnit.equals("Sq Feet")) {
                                ans = 9 * a;
                            } else if (outputAreaUnit.equals("Sq Centimeters")) {
                                ans = 8361.27 * a;


                            } else if (outputAreaUnit.equals("Sq Inch")) {
                                ans = 1296 * a;


                            } else if (outputAreaUnit.equals("Sq Kilometer")) {
                                ans = 0.0000008361 * a;
                            } else if (outputAreaUnit.equals("Sq Meter")) {
                                ans = 0.83612 * a;
                            } else if (outputAreaUnit.equals("Sq Mile")) {
                                ans = 0.0000003228 * a;
                            } else if (outputAreaUnit.equals("Sq Yard")) {
                                ans = a;
                            }
                        }

                        //For state wise
                        //For AP
                        if (stateForStatewise.equals("Andhra Pradesh")) {
                            if (inputAreaUnit.equals("Ankanam")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.001652893 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000668902 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 72 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 668901887 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 10368.066 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000668916 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 6.689018878 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000258264 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 8 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Guntha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.025 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.0101171 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1089 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1011715.085 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 156817.0036 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000101174 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 101.1714105 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000390625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 121 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Kuncham")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.1 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.040468564 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 4356 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 4046860.339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 627268.0145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 404.6856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 484 * a;
                                }
                            }
                        }
                        //For Assam state
                        if (stateForStatewise.equals("Assam")) {
                            if (inputAreaUnit.equals("Bigha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.330578512 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.133780378 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 14400 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 13378050.71 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 2073613.271 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.001337833 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 1337.803776 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000516529 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1600 * a;
                                }
                            }

                            // Kattha
                            if (inputAreaUnit.equals("Kattha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.066115702 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.026756076 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 2880 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 2675610.141 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 414722.6542 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000267567 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 267.5607551 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000103306 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 320 * a;
                                }
                            }
                            //Lecha
                            if (inputAreaUnit.equals("Lecha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.003305785 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.001337804 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 144 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 133780.5071 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 20736.13271 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000133783 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 13.378803776 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000516529 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 16 * a;
                                }
                            }
                        }
//For Bihar
                        if (stateForStatewise.equals("Bihar")) {

                            if (inputAreaUnit.equals("Bigha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.252928526 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 272255 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 25292877.12 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 3920425.091 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00252934 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2529.285263 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000976563 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3025 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Dhur")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.0015625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000632321 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 68.0625 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 63232.19279 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 9801.062727 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000632335 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 6.323213158 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000244141 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 7.5625 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Kattha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.03125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.012646426 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1361.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1264643.856 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 196021.2545 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000126467 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 126.4642632 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000488281 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 151.25 * a;
                                }
                            }
                        }

                        //For Gujarat--
                        if (stateForStatewise.equals("Gujarat")) {

                            if (inputAreaUnit.equals("Bigha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.4 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.161874257 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 17424 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 16187441.35 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 2509072.058 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.001618778 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 1618.742568 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1936 * a;
                                }
                            }
                        }
//For Haryana
                        if (stateForStatewise.equals("Haryana")) {

                            if (inputAreaUnit.equals("Bigha Pakka")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.252928526 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 272255 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 25292877.12 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 3920425.091 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00252934 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2529.285263 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000976563 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3025 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Bigha Kachha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.208332645 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.08430923 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 9074.97 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 8430931.168 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1306804.044 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000843111 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 843.0923007 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00032552 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1008.33 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Pakka")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.03125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.012646426 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1361.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1264643.856 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 196021.2545 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000126467 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 126.4642632 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000488281 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 151.25 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Kachha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.010416736 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004215503 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 453.753 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 421550.739 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 65340.85018 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000042156 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 42.1550331 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000162761 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 50.417 * a;
                                }
                            }

                            //
                            if (inputAreaUnit.equals("Ghumaon")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 1 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.404685642 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 43560 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 40468603.39 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 6272680.145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.004046945 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 4046.856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 4840 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Killa")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 1 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.404685642 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 43560 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 40468603.39 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 6272680.145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.004046945 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 4046.856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 4840 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Kanal")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.050585705 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 5445 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 5058575.423 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 784085.0181 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000505868 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 505.8570526 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000195313 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 605 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Sarsahi (Square Karam)")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.000694444 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000281032 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 30.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 28103.1968 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 4356.027879 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000281038 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2.810316959 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000108507 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3.361111111 * a;
                                }
                            }
                        }
                        //Himachal Pradesh
                        if (stateForStatewise.equals("Himachal Pradesh")) {

                            if (inputAreaUnit.equals("Bigha HP-1")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.2 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.080937128 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 8712 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 8093720.677 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1254536.029 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000809389 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 809.3712842 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0003125 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 968 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Bigha HP-2")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.185950413 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.075251462 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 8100 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 7525153.522 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1166407.465 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000752531 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 752.5146238 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000290548 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 900 * a;
                                }
                            }

                            if (inputAreaUnit.equals("Biswa HP-1")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.01 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004046856 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 435.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 404686.0339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 62726.80145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 40.46856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 48.4 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa HP-2")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.009297521 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.003762573 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 405 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 376257.6761 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 58320.37325 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000376266 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 37.62573119 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000145274 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 45 * a;
                                }
                            }

                            //
                            if (inputAreaUnit.equals("Ghumaon")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 1 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.404685642 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 43560 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 40468603.39 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 6272680.145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.004046945 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 4046.856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 4840 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Kanal")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.050585705 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 5445 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 5058575.423 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 784085.0181 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000505868 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 505.8570526 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000195313 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 605 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Sarsahi (Square Karam)")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.000694444 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000281032 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 30.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 28103.1968 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 4356.027879 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000281038 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2.810316959 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000108507 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3.361111111 * a;
                                }
                            }
                        }
                        //Jammu & Kashmir
                        if (stateForStatewise.equals("Jammu & Kashmir")) {
                            //
                            if (inputAreaUnit.equals("Kanal")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.050585705 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 5445 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 5058575.423 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 784085.0181 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000505868 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 505.8570526 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000195313 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 605 * a;
                                }
                            }

                        }
                        //Jharkhand
                        if (stateForStatewise.equals("Jharkhand")) {
                            //
                            if (inputAreaUnit.equals("Dhur")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.0015625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000632321 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 68.0625 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 63232.19279 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 9801.062727 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000632335 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 6.323213158 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000244141 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 7.5625 * a;
                                }
                            }

                        }

//For Karnataka state
                        if (stateForStatewise.equals("Karnataka")) {
                            //Ankanam
                            if (inputAreaUnit.equals("Ankanam")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.001652893 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000668902 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 72 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 66890.25353 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 10368.06636 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000668916 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 6.689018878 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000258264 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 8 * a;
                                }
                            }

                            // Cent
                            if (inputAreaUnit.equals("Cent")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.01 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004046856 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 435.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 404686.0339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 62726.80145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 40.46856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 48.4 * a;
                                }
                            }
                            //Guntha
                            if (inputAreaUnit.equals("Guntha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.025 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.010117141 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1089 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1011715.085 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 156817.0036 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000101174 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 101.1714105 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000390625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 121 * a;
                                }
                            }
                        }
                        //Kerala
                        if (stateForStatewise.equals("Kerala")) {
                            // Cent
                            if (inputAreaUnit.equals("Cent")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.01 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004046856 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 435.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 404686.0339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 62726.80145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 40.46856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 48.4 * a;
                                }
                            }
                        }
                        //Madhya Pradesh
                        if (stateForStatewise.equals("Madhya Pradesh")) {
                            // Bigha
                            if (inputAreaUnit.equals("Bigha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.275481405 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.111483369 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 11999.97 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 11148347.72 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1728006.739 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.001114858 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 1114.833693 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00043044 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1333.33 * a;
                                }
                            }
                            // Kattha
                            if (inputAreaUnit.equals("Kattha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.013774105 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.005574182 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 600 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 557418.7794 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 86400.55296 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000055743 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 55.74182398 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000021522 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 66.66666667 * a;
                                }
                            }
                        }
                        //Maharashtra
                        if (stateForStatewise.equals("Maharashtra")) {
                            //Guntha
                            if (inputAreaUnit.equals("Guntha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.025 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.010117141 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1089 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1011715.085 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 156817.0036 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000101174 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 101.1714105 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000390625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 121 * a;
                                }
                            }
                        }
                        //Punjab
                        if (stateForStatewise.equals("Punjab")) {

                            if (inputAreaUnit.equals("Bigha Pakka")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.252928526 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 272255 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 25292877.12 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 3920425.091 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00252934 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2529.285263 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000976563 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3025 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Bigha Kachha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.208332645 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.08430923 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 9074.97 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 8430931.168 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1306804.044 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000843111 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 843.0923007 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00032552 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1008.33 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Pakka")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.03125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.012646426 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1361.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1264643.856 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 196021.2545 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000126467 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 126.4642632 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000488281 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 151.25 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Kachha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.010416736 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004215503 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 453.753 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 421550.739 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 65340.85018 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000042156 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 42.1550331 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000162761 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 50.417 * a;
                                }
                            }

                            //
                            if (inputAreaUnit.equals("Ghumaon")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 1 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.404685642 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 43560 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 40468603.39 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 6272680.145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.004046945 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 4046.856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 4840 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Killa")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 1 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.404685642 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 43560 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 40468603.39 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 6272680.145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.004046945 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 4046.856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 4840 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Kanal")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.050585705 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 5445 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 5058575.423 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 784085.0181 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000505868 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 505.8570526 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000195313 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 605 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Sarsahi (Square Karam)")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.000694444 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.000281032 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 30.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 28103.1968 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 4356.027879 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00000281038 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2.810316959 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000108507 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3.361111111 * a;
                                }
                            }
                        }
                        //Rajasthan
                        if (stateForStatewise.equals("Rajasthan")) {

                            if (inputAreaUnit.equals("Bigha Rajasthan-1")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.252928526 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 272255 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 25292877.12 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 3920425.091 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00252934 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2529.285263 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000976563 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3025 * a;
                                }
                            }
                            if (inputAreaUnit.equals("Bigha Rajasthan-2")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.4 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.161874257 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 17424 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 16187441.35 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 2509072.058 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.001618778 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 1618.742568 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1936 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa (Upper parts)")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.03125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.012646426 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1361.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1264643.856 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 196021.2545 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000126467 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 126.4642632 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000488281 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 151.25 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa (Lower parts)")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.02 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.008093713 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 871.2 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 809372.0677 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 125453.6029 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000809389 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 80.93712842 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00003125 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 96.8 * a;
                                }
                            }

                        }
                        //Tamil Nadu
                        if (stateForStatewise.equals("Kerala")) {
                            // Cent
                            if (inputAreaUnit.equals("Cent")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.01 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004046856 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 435.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 404686.0339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 62726.80145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 40.46856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 48.4 * a;
                                }
                            }
                            // Ground
                            if (inputAreaUnit.equals("Ground")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.055096419 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.02229673 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 2400 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 2229675.118 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 345602.2119 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000222972 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 222.9672959 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000860882 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 266.6666667 * a;
                                }
                            }
                        }
                        //Tripura
                        if (stateForStatewise.equals("Tripura")) {
                            // Dhur
                            if (inputAreaUnit.equals("Dhur")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.0000826446 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.0000334451 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 3.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 3344.512677 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 518.4033178 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000000334458 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 0.334450944 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000000129132 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 0.4 * a;
                                }
                            }

                        }
                        //Uttar Pradesh
                        if (stateForStatewise.equals("Uttar Pradesh")) {

                            if (inputAreaUnit.equals("Bigha Pakka")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.625 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.252928526 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 272255 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 25292877.12 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 3920425.091 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.00252934 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 2529.285263 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000976563 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 3025 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Bigha Kachha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.208332645 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.08430923 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 9074.97 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 8430931.168 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1306804.044 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000843111 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 843.0923007 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00032552 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1008.33 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Pakka")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.03125 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.012646426 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 1361.25 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 1264643.856 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 196021.2545 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000126467 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 126.4642632 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000488281 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 151.25 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Kachha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.010416736 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004215503 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 453.753 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 421550.739 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 65340.85018 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000042156 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 42.1550331 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000162761 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 50.417 * a;
                                }
                            }

                        }
                        //Uttarakhand
                        if (stateForStatewise.equals("Uttarakhand")) {

                            if (inputAreaUnit.equals("Bigha Uttarakhand-1")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.2 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.080937128 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 8712 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 8093720.677 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1254536.029 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000809389 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 809.3712842 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0003125 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 968 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Bigha Uttarakhand-2")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.185950413 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.075251462 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 8100 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 7525153.522 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 1166407.465 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.000752531 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 752.5146238 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000290548 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 900 * a;
                                }
                            }
                            if (inputAreaUnit.equals("Biswa Uttarakhand-1")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.01 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004046856 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 435.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 404686.0339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 62726.80145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 40.46856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 48.4 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Biswa Uttarakhand-2")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.009297521 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.003762573 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 405 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 376257.6761 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 58320.37325 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000376266 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 37.62573119 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.0000145274 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 45 * a;
                                }
                            }
                        }
                        //West Bengal
                        if (stateForStatewise.equals("West Bengal")) {
                            //
                            if (inputAreaUnit.equals("Bigha")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.330578512 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.133780378 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 14400 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 13378050.71 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 2073613.271 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.001337833 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 1337.803776 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000516529 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 1600 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Chatak")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.004132231 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.001672255 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 180 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 167225.6338 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 25920.16589 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000167229 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 16.72254719 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.00000645661 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 20 * a;
                                }
                            }
                            //
                            if (inputAreaUnit.equals("Decimal")) {
                                if (outPutAreaForS.equals("Acre")) {
                                    ans = 0.01 * a;

                                } else if (outPutAreaForS.equals("Hectare")) {
                                    ans = 0.004046856 * a;
                                } else if (outPutAreaForS.equals("Sq Feet")) {
                                    ans = 435.6 * a;
                                } else if (outPutAreaForS.equals("Sq Centimeters")) {
                                    ans = 404686.0339 * a;


                                } else if (outPutAreaForS.equals("Sq Inch")) {
                                    ans = 62726.80145 * a;


                                } else if (outPutAreaForS.equals("Sq Kilometer")) {
                                    ans = 0.0000404694 * a;
                                } else if (outPutAreaForS.equals("Sq Meter")) {
                                    ans = 40.46856421 * a;
                                } else if (outPutAreaForS.equals("Sq Mile")) {
                                    ans = 0.000015625 * a;
                                } else if (outPutAreaForS.equals("Sq Yard")) {
                                    ans = 48.4 * a;
                                }
                            }
                        }
                        inputT.setText("Area in " + inputAreaUnit);
                        if (isClicked) {
                            outputT.setText("Area in " + outputAreaUnit);
                        } else {
                            outputT.setText("Area in " + outPutAreaForS);
                        }
                        inputA.setText(a + "");
                        outputA.setText(ans + "");
                    }
                }
            }

        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputT.setText("");
                inputA.setText("");
                outputT.setText("");
                outputA.setText("");
                inputAreaEt.setText("");
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.menu_item_share);
        menu.add("Share").setIcon(R.drawable.ic_share_black_24dp).setShowAsAction(1);
        menu.add("About");
        menu.add("Privacy policy");
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement

        if (item.getTitle().equals("Share")) {
            Intent sendIntent = new Intent();
            String msg = "Hey!\nYour friend is using Land Area Converter an useful app to convert one unit to another unit on the go.\n\nConvert state wise and common standard units to one another\nWhy dont you try this useful app\n\nTry at:\n " + myDynamicLink;
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (item.getTitle().equals("About")) {
            Intent i = new Intent(MainActivity.this,About.class);
            startActivity(i);
        }
        if (item.getTitle().equals("Privacy policy")) {
            Intent i = new Intent(MainActivity.this,PrivacyPolicyAc.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadInputAdapter(){
        arrayAdapterIp = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,commonList);
        inputS.setAdapter(arrayAdapterIp);
    }
    public void clearInputAdapter(){
        commonList.clear();
        arrayAdapterIp.notifyDataSetChanged();
        inputS.setAdapter(null);
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
    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdContainer = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adV = (LinearLayout) inflater.inflate(R.layout.native_ad_fb, nativeAdContainer, false);
        nativeAdContainer.addView(adV);

        // Add the AdChoices icon
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(MainActivity.this, nativeAd, true);
        adChoicesContainer.addView(adChoicesView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = adV.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adV.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adV.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adV.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adV.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adV.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adV.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        clickableViews.add(nativeAdMedia);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adV,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

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
