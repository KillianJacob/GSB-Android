package com.example.gsb;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

  // Button
  Button btReset;
  ImageButton ib1, ibFr, ibEs, ibEn;
  Intent intent;

  public void setLocale(String lang) {
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    // intent = new Intent(this, MainActivity.class);
    // startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    btReset = this.findViewById(R.id.btReset);
    ibFr = this.findViewById(R.id.ibFr);
    ibEn = this.findViewById(R.id.ibEn);
    ibEs = this.findViewById(R.id.ibEs);
    ib1 = this.findViewById(R.id.ib1);

    btReset.setOnClickListener(this);
    ibFr.setOnClickListener(this);
    ibEn.setOnClickListener(this);
    ibEs.setOnClickListener(this);
    ib1.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btReset:
        BdEchantillonAdapter bdd = new BdEchantillonAdapter(this);
        bdd.open();
        bdd.resetBDD();
        Toast.makeText(getApplicationContext(), "Base de données reset", Toast.LENGTH_LONG).show();
        bdd.close();
        break;
      case R.id.ib1:
        finish();
        break;
      case R.id.ibFr:
        finish();
        setLocale("fr");
        break;
      case R.id.ibEn:
        finish();
        setLocale("en");
        break;
      case R.id.ibEs:
        finish();
        setLocale("es");
      default:
        break;
    }
  }

  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
  }
}

