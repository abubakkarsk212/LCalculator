package lcal.ltd.popo.com.lcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }
    @Override
    protected void onResume() {

        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);

//                        if(deepLink == null) {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);

//                        }else {
//                            Intent i1 = new Intent("axisbank",deepLink);
//
//                            startActivity(i1);
//                        }
                    finish();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        t.start();
        super.onResume();




    }
}
