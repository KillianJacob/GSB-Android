package com.example.gsb;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

  private static int SPLASH_TIME_OUT = 1200;

  ImageView imageLogo;
  ImageView imageEnosi;
  Animation animation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent homeIntent = new Intent (SplashActivity.this, MainActivity.class);
        startActivity(homeIntent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
      }
    },SPLASH_TIME_OUT);

    // Animated logo
    imageLogo = this.findViewById(R.id.imageLogo);
    animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_anim);
    imageLogo.startAnimation(animation);


    // Animated logo
    imageEnosi = this.findViewById(R.id.imageEnosi);
    animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_anim2);
    imageEnosi.startAnimation(animation);
  }
}
