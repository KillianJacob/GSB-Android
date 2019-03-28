package com.example.gsb;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  // Utile pour les transitions entre les activités
  Intent intent;

  // AnimatedBackground
  RelativeLayout myLayout;
  AnimationDrawable animationDrawable;

  // Button
  Button bt1,bt2,bt3;
  ImageButton ibSetting;

  //BdEchantillonAdapter echantillonBdd;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Mise en plein écran de l'activity
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // test de la BD
    //testEchantillonBd();

    // AnimatedBackground
    myLayout = this.findViewById(R.id.mainLayout);
    animationDrawable = (AnimationDrawable) myLayout.getBackground();
    animationDrawable.setEnterFadeDuration(4500);
    animationDrawable.setExitFadeDuration(4500);
    animationDrawable.start();

    // Association des différents éléments de la vue aux variables du code de l'activité
    bt1 = this.findViewById(R.id.bt1);
    bt2 = this.findViewById(R.id.bt2);
    bt3 = this.findViewById(R.id.bt3);
    ibSetting = this.findViewById(R.id.ibSettings);

    // Ajout d'une fonction de réaction aux cliques de l'utilisateur
    bt1.setOnClickListener(this);
    bt2.setOnClickListener(this);
    bt3.setOnClickListener(this);
    ibSetting.setOnClickListener(this);
  }

  @Override
  protected void onResume() {

    super.onResume();
    setContentView(R.layout.activity_main);

    // Mise en plein écran de l'activity
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // test de la BD
    //testEchantillonBd();

    // AnimatedBackground
    myLayout = this.findViewById(R.id.mainLayout);
    animationDrawable = (AnimationDrawable) myLayout.getBackground();
    animationDrawable.setEnterFadeDuration(4500);
    animationDrawable.setExitFadeDuration(4500);
    animationDrawable.start();

    // Association des différents éléments de la vue aux variables du code de l'activité
    bt1 = this.findViewById(R.id.bt1);
    bt2 = this.findViewById(R.id.bt2);
    bt3 = this.findViewById(R.id.bt3);
    ibSetting = this.findViewById(R.id.ibSettings);

    // Ajout d'une fonction de réaction aux cliques de l'utilisateur
    bt1.setOnClickListener(this);
    bt2.setOnClickListener(this);
    bt3.setOnClickListener(this);
    ibSetting.setOnClickListener(this);
  }

  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt1:
        intent = new Intent(getBaseContext(), SaisieEchantillon.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        break;
      case R.id.bt2:
        intent = new Intent(getBaseContext(), ListeEchantillon.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        break;
      case R.id.bt3:
        intent = new Intent(getBaseContext(), MajEchantillon.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        break;
      case R.id.ibSettings:
        intent = new Intent(getBaseContext(), SettingsActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        break;
      default:
        break;
    }
  }
  // Fonction qui permet de terminer l'activité via un fadeout
  // Aucune utilité dans cette activité
  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
  }

  /*
  public void testEchantillonBd(){
    //Création d'une instance de la classe unEchantillonsBDD
    echantillonBdd = new BdEchantillonAdapter(this);

    //Création d'un Echantillon
    Echantillon unEchantillon = new Echantillon("code1", "lib1", "10");

    //On ouvre la base de données pour écrire dedans
    echantillonBdd.open();
    //On insère l'echantillon que l'on vient de créer
    echantillonBdd.insererEchantillon(unEchantillon);
    System.out.println("insertion echantillon");

    //Pour vérifier que l'on a bien créé un Echantillon dans la BDD
    //on extrait l’echantillon de la BDD grâce à la désignation de l'echantillon que l'on a créé précédemment
    Echantillon unEchantillonFromBdd = echantillonBdd.getEchantillonWithCode("code1");
    //Si un unEchantillon est retourné (donc si le unEchantillon à bien été ajouté à la BDD)
    if(unEchantillonFromBdd != null){
      //On affiche les infos de l’Echantillon dans un Toast
      Toast.makeText(this, unEchantillonFromBdd.getCode(), Toast.LENGTH_LONG).show();
      //On modifie le titre de l’Echantillon
      unEchantillonFromBdd.setCode("code2");
      //Puis on met à jour la BDD
      echantillonBdd.updateEchantillon(unEchantillonFromBdd.getLibelle(), unEchantillonFromBdd);
    }
    else {
      Toast.makeText(this, "Echantillon non trouvé", Toast.LENGTH_LONG).show();
    }

    //On extrait l’Echantillon de la BDD grâce à sa nouvelle désignation
    unEchantillonFromBdd = echantillonBdd.getEchantillonWithCode("code2");
    //S'il existe un Echantillon possédant cette désignation dans la BDD
    if(unEchantillonFromBdd != null){
      //On affiche les nouvelles info de l’Echantillon pour vérifié que la désignation de l’Echantillon a bien été maj
      Toast.makeText(this, unEchantillonFromBdd.getCode(), Toast.LENGTH_LONG).show();
      //on supprime le unEchantillon de la BDD grâce à son ID
      echantillonBdd.removeEchantillonWithRef(unEchantillonFromBdd.getLibelle());
    }


    //On essait d'extraire de nouveau l’Echantillon de la BDD toujours grâce à sa nouvelle désignation
    unEchantillonFromBdd = echantillonBdd.getEchantillonWithCode("code2");
    //Si aucun unEchantillon n'est retourné
    if(unEchantillonFromBdd == null){
      //On affiche un message indiquant que l’Echantillon n'existe pas dans la BDD
      Toast.makeText(this, "Cet echantillon n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
    }
    else{ 	//Si l'Echantillon existe (mais normalement il ne devrait pas)
      //on affiche un message indiquant que l’Echantillon existe dans la BDD
      Toast.makeText(this, "Cet echantillon existe dans la BDD", Toast.LENGTH_LONG).show();
    }
    echantillonBdd.close();
  }
  */
}
