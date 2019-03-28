package com.example.gsb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListeEchantillonAdapter extends BaseAdapter {

  Context context;
  String[] ech, lib, nmb;
  LayoutInflater inflater;
  TextView tvEch, tvLib, tvNmb;
  String strEch, strLib;

  public ListeEchantillonAdapter(Context context, String[] ech, String[] lib, String[] nmb) {
    this.context = context;
    this.ech = ech;
    this.lib = lib;
    this.nmb = nmb;
    inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_liste_echantillon, null);
    }
    tvEch = convertView.findViewById(R.id.ech);
    tvLib = convertView.findViewById(R.id.lib);
    tvNmb = convertView.findViewById(R.id.nmb);

    strEch = "#"+ech[position];
    strLib = lib[position];
    strLib = strLib.substring(0, 1).toUpperCase() + strLib.substring(1);

    tvEch.setText(strEch);
    tvLib.setText(strLib);
    tvNmb.setText(nmb[position]);

    return convertView;
  }

  public int getCount() {
    return ech.length;
  }

  public Object getItem(int position) {
    return lib[position] + "/"  + ech[position] + "/" + nmb[position];
  }

  public long getItemId(int position) {
    return position;
  }
}