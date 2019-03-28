package com.example.gsb;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListeEchantillon extends AppCompatActivity implements View.OnClickListener{


  // AnimatedBackground
  RelativeLayout myLayout;
  AnimationDrawable animationDrawable;

  // Feedback de l'objet sélectionné
  TextView tv;

  // Déclaration des ArrayList
  private final ArrayList<String> ech = new ArrayList<>();
  private final ArrayList<String> lib = new ArrayList<>();
  private final ArrayList<String> nmb = new ArrayList<>();

  // Déclaration des variables utiles
  ImageButton ibFinish, ibMaj;
  Intent intent;
  GridView gridView;
  String[] test;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_liste_echantillon);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    myLayout = this.findViewById(R.id.mainLayout);
    ibFinish = this.findViewById(R.id.ibFinish);
    ibMaj = this.findViewById(R.id.ibMaj);
    ibFinish.setOnClickListener(this);
    ibMaj.setOnClickListener(this);

    animationDrawable = (AnimationDrawable) myLayout.getBackground();
    animationDrawable.setEnterFadeDuration(4500);
    animationDrawable.setExitFadeDuration(4500);
    animationDrawable.start();

    // GridView
    tv = findViewById(R.id.feedback);

    gridView = this.findViewById(R.id.gd);


    BdEchantillonAdapter echantillonBdd = new BdEchantillonAdapter(this);

    echantillonBdd.open();

    Cursor data = echantillonBdd.getData();

    int index = 0;

    while (data.moveToNext()) {

      ech.add(data.getString(1));
      lib.add(data.getString(2));
      nmb.add(data.getString(3));
      index++;
    }

    echantillonBdd.close();

    String[] echArray = ech.toArray(new String[0]);
    String[] libArray = lib.toArray(new String[0]);
    String[] nmbArray = nmb.toArray(new String[0]);

    final ListeEchantillonAdapter gridAdapter = new ListeEchantillonAdapter(ListeEchantillon.this, echArray, libArray, nmbArray);
    gridView.setAdapter(gridAdapter);
    gridView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        test = gridView.getItemAtPosition(position).toString().split("/");
        tv.setText(test[0]);
      }
    });
  }

  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ibFinish:
        finish();
        break;
      case R.id.ibMaj:
        if(tv.getText() == "")
          (new AlertDialog.Builder(ListeEchantillon.this)).setMessage("Merci de sélectionner un échantillon").show();
        else{
          intent = new Intent(getBaseContext(), MajEchantillon.class);
          String[] data = test;
          intent.putExtra("strEchantillon", data[0]);
          intent.putExtra("strCode", data[1]);
          intent.putExtra("strQte", data[2]);
          finish();
          startActivity(intent);

        }
      default:
        break;
    }
  }

  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
  }
}