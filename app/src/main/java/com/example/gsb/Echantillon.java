package com.example.gsb;

public class Echantillon {
  protected String code;
  protected String libelle;
  protected String stock;

  // L'objet "Echantillon" est composer de 3 variables String

  public Echantillon(String unCode, String unLibelle, String unStock){
    this.code = unCode;
    this.libelle = unLibelle;
    this.stock = unStock;
  }

  public String getCode(){
    return this.code;
  }
  public void setCode(String code){
    this.code = code;
  }

  public String getLibelle(){
    return this.libelle;
  }
  public void setLibelle(String libelle){
    this.libelle = libelle;
  }

  public String getStock(){
    return this.stock;
  }
  public void setStock(String stock){
    this.stock = stock;
  }
}
