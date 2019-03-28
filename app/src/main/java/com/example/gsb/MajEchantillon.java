package com.example.gsb;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MajEchantillon extends AppCompatActivity implements View.OnClickListener {

  // AnimatedBackground
  RelativeLayout myLayout;
  AnimationDrawable animationDrawable;

  // TextView
  TextView pt1, pt2, nmbD1;

  // Variables utiles au programme
  String strPt1, strPt2, strNmbD1, strEchantillon, strCode, strQte;
  int intNmbD1;

  // Button
  Button btFinish, btAdd, btSuppr, btListeView;

  // Utile pour les transitions entre les activités
  Intent intent;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maj_echantillon);

    // Mise en plein écran de l'activity
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // AnimatedBackground
    myLayout = this.findViewById(R.id.mainLayout);
    animationDrawable = (AnimationDrawable) myLayout.getBackground();
    animationDrawable.setEnterFadeDuration(4500);
    animationDrawable.setExitFadeDuration(4500);
    animationDrawable.start();

    // Récupération des variables transmises depuis ListeEchantillon
    strEchantillon = (String) getIntent().getSerializableExtra("strEchantillon");
    strCode = (String) getIntent().getSerializableExtra("strCode");
    strQte = (String) getIntent().getSerializableExtra("strQte");

    // Association des différents éléments de la vue aux variables du code de l'activité
    pt1 = this.findViewById(R.id.nD1);
    pt2 = this.findViewById(R.id.pt2);
    nmbD1 = this.findViewById(R.id.nD2);
    btFinish = this.findViewById(R.id.btFinish);
    btAdd = this.findViewById(R.id.btAdd);
    btSuppr = this.findViewById(R.id.btSuppr);
    btListeView = this.findViewById(R.id.btListeView);

    // Les variables récupérés sont placé dans les TextView
    pt1.setText(strEchantillon);
    pt2.setText(strCode);
    nmbD1.setText(strQte);

    // Ajout d'une fonction de réaction aux cliques de l'utilisateur
    btFinish.setOnClickListener(this);
    btAdd.setOnClickListener(this);
    btSuppr.setOnClickListener(this);
    btListeView.setOnClickListener(this);
  }

  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btFinish:
        finish();
        intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        break;
      case R.id.btAdd:
        // Code de vérification et de mise à jour d'un échantillon dans la BDD
        strPt1 = pt1.getText().toString().trim();
        strPt2 = pt2.getText().toString().trim();
        strNmbD1 = nmbD1.getText().toString().trim();

        BdEchantillonAdapter bd = new BdEchantillonAdapter(this);
        bd.open();
        if (!strNmbD1.equals("")) {
          intNmbD1 = Integer.parseInt(strNmbD1);
        }
        if (strPt1.equals("") || strPt2.equals("") || strNmbD1.equals("")) {
          Toast.makeText(getApplicationContext(), "Merci de remplir les différentes valeurs",
              Toast.LENGTH_LONG).show();
        } else if (intNmbD1 > 9999 || intNmbD1 < 0) {
          Toast.makeText(getApplicationContext(), "Le stock saisie n'est pas valide",
              Toast.LENGTH_LONG).show();
        } else if (bd.getEchantillonWithCode(strPt2) == null) {
          new AlertDialog.Builder(MajEchantillon.this).setMessage("L'échantillons n'éxiste pas")
              .show();
        } else {
          Echantillon echan = new Echantillon(strPt2, strPt1, strNmbD1);
          bd.updateEchantillon(strPt1, echan);
          Toast.makeText(getApplicationContext(), "L'échantillon a été mit a jour",
              Toast.LENGTH_LONG).show();
        }
        bd.close();
        break;
      // Code de vérification et de mise à jour d'un échantillon dans la BDD
      case R.id.btSuppr:
        strPt2 = pt2.getText().toString().trim();
        BdEchantillonAdapter bdd = new BdEchantillonAdapter(this);
        bdd.open();
        if (bdd.getEchantillonWithCode(strPt2) != null) {
          bdd.removeEchantillonWithRef(pt1.getText().toString());
          Toast.makeText(getApplicationContext(), "L'échantillon à été supprimée", Toast.LENGTH_LONG).show();
        } else new AlertDialog.Builder(MajEchantillon.this).setMessage("L'échantillons n'éxiste pas").show();
        break;
      case R.id.btListeView:
        intent = new Intent(getBaseContext(), ListeEchantillon.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        break;
      default:
        break;
    }
  }

  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
  }
}
