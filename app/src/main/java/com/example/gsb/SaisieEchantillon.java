package com.example.gsb;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SaisieEchantillon extends AppCompatActivity implements View.OnClickListener {

  RelativeLayout myLayout;
  AnimationDrawable animationDrawable;

  TextView nd1, pt2, nD2;
  String strnd1, strPt2, strnD2;
  int intnD2, intnd1;

  Button bt1, bt2;

  BdEchantillonAdapter echantillonBdd;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_saisie_echantillon);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    myLayout = this.findViewById(R.id.mainLayout);

    animationDrawable = (AnimationDrawable) myLayout.getBackground();
    animationDrawable.setEnterFadeDuration(4500);
    animationDrawable.setExitFadeDuration(4500);
    animationDrawable.start();

    nd1 = findViewById(R.id.nD1);
    pt2 = findViewById(R.id.pt2);
    nD2 = findViewById(R.id.nD2);

    bt1 = findViewById(R.id.btAdd);
    bt2 = findViewById(R.id.btFinish);

    bt1.setOnClickListener(this);
    bt2.setOnClickListener(this);

  }

  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btAdd:
        strnd1 = nd1.getText().toString();
        strPt2 = pt2.getText().toString();
        strnD2 = nD2.getText().toString();
        if (!strnd1.equals("")){
          intnd1 = Integer.parseInt(strnd1);
        }
        if (!strnD2.equals("")){
          intnD2 = Integer.parseInt(strnD2);
        }

        if (strnd1.equals("") || strPt2.equals("") || strnD2.equals("")) {
          Toast.makeText(getApplicationContext(), "Merci de remplir les différentes valeurs", Toast.LENGTH_LONG).show();
        } else if (intnd1 > 9999 || intnd1 < 0){
          Toast.makeText(getApplicationContext(), "Veuillez saisir un code valide", Toast.LENGTH_LONG).show();
        } else if (!strPt2.matches("[a-z]+")) {
          Toast.makeText(getApplicationContext(), "Veuillez saisir un libellé valide", Toast.LENGTH_LONG).show();
        } else if (intnD2 > 9999 || intnD2 < 0) {
          Toast.makeText(getApplicationContext(), "Veuillez saisir un stock valide", Toast.LENGTH_LONG).show();
        } else { AjoutEchantillon(strnd1, strPt2, strnD2);
        Toast.makeText(getApplicationContext(), intnd1 +" + "+ strPt2 +" + "+ intnD2 , Toast.LENGTH_LONG).show();
        }
        break;
      case R.id.btFinish:
        finish();
        break;
      default:
        break;
    }
  }

  public void AjoutEchantillon(String code, String libelle, String stock) {
    echantillonBdd = new BdEchantillonAdapter(this);

    //Création d'un Echantillon
    Echantillon unEchantillon = new Echantillon(code, libelle, stock);

    //On ouvre la base de données pour écrire dedans
    echantillonBdd.open();
    //On insère l'echantillon que l'on vient de créer
    echantillonBdd.insererEchantillon(unEchantillon);
    System.out.println("insertion echantillon");

    Toast.makeText(getApplicationContext(), "L'échantillon suivant à été ajouté "
        + "code :  " + code + " + "
        + "libelle : " + libelle + " + "
        + "stock : " + stock, Toast.LENGTH_LONG).show();
  }

  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
  }
}
