package com.example.gsb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BdEchantillonAdapter {

  static final int VERSION_BDD = 1;
  private static final String NOM_BDD = "echantillon.db";
  static final String TABLE_ECHANTILLONS = "table_echantillons";
  static final String COL_ID = "_id";
  static final int NUM_COL_ID = 0;
  static final String COL_CODE = "CODE";
  static final int NUM_COL_CODE = 1;
  static final String COL_LIBELLE = "LIBELLE";
  static final int NUM_COL_LIBELLE = 2;
  static final String COL_STOCK = "STOCK";
  static final int NUM_COL_STOCK = 3;

  private CreateBdEchantillon	bdEchantillons;
  private Context context;
  private SQLiteDatabase db;
  public BdEchantillonAdapter  (Context	context){
    this.context = context;
    bdEchantillons = new CreateBdEchantillon(context, NOM_BDD, null, VERSION_BDD);
  }

  //si la base n’existe pas, l’objet SQLiteOpenHelper exécute la méthode onCreate
  // si la version de la bse a changé, la méthode onUpgrade sera lancée
  // dans les 2 cas l’appel à getWritableDatabase ou getReadableDatabase renverra  la base
  // de données en cache, nouvellement ouverte, nouvellement créée ou mise à jour
  public BdEchantillonAdapter  open (){
    db = bdEchantillons.getWritableDatabase();
    return this;
  }
  public BdEchantillonAdapter  close (){
    db.close();
    return null;
  }

  public long insererEchantillon (Echantillon unEchantillon){
    //Création d'un ContentValues (fonctionne comme une HashMap)
    ContentValues values = new ContentValues();
    //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne où on veut mettre la valeur)
    values.put(COL_CODE, unEchantillon.getCode());
    values.put(COL_LIBELLE, unEchantillon.getLibelle());
    values.put(COL_STOCK, unEchantillon.getStock());
    //on insère l'objet dans la BDD via le ContentValues
    return db.insert(TABLE_ECHANTILLONS, null, values);
  }

  private Echantillon cursorToEchantillon(Cursor c){ //Cette méthode permet de convertir un cursor en un echantillon
    //si aucun élément n'a été retourné dans la requête, on renvoie null
    if (c.getCount() == 0)
      return null;
    //Sinon
    c.moveToFirst();	//on se place sur le premier élément
    Echantillon unEchantillon = new Echantillon(null,null,null);	//On créé un echantillon
    //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
    unEchantillon.setCode(c.getString(NUM_COL_CODE));
    unEchantillon.setLibelle(c.getString(NUM_COL_LIBELLE));
    unEchantillon.setStock(c.getString(NUM_COL_STOCK));
    c.close(); 	//On ferme le cursor
    return unEchantillon; 	//On retourne l'echantillon
  }

  public Echantillon getEchantillonWithCode(String code){
    //Récupère dans un Cursor les valeurs correspondant à un echantillon grâce à sa designation)
    Cursor c = db.query(TABLE_ECHANTILLONS, new String[] {COL_ID,COL_CODE, COL_LIBELLE, COL_STOCK},
        COL_CODE + " LIKE \"" + code +"\"", null, null, null, null);
    return cursorToEchantillon(c);
  }


  public int updateEchantillon(String ref, Echantillon unEchantillon){
    //La mise à jour d'un echantillon dans la BDD fonctionne plus ou moins comme une insertion
    //il faut simple préciser quel echantillon on doit mettre à jour grâce à sa référence
    ContentValues values = new ContentValues();
    values.put(COL_CODE, unEchantillon.getCode());
    values.put(COL_LIBELLE, unEchantillon.getLibelle());
    values.put(COL_STOCK, unEchantillon.getStock());
    return db.update(TABLE_ECHANTILLONS, values, COL_LIBELLE + " = \"" +ref+"\"", null);
  }

  public int removeEchantillonWithRef(String ref){
    //Suppression d'un echantillon de la BDD grâce à sa référence
    return db.delete(TABLE_ECHANTILLONS, COL_LIBELLE + " = \"" +ref+"\"", null);
  }

  public Cursor getData(){
    return db.rawQuery("SELECT * FROM TABLE_ECHANTILLONS", null);
  }

  // Permet la suppression des données de la BDD
  public int resetBDD(){
    return db.delete(TABLE_ECHANTILLONS, "", null);
  }

}