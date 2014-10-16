package com.izv.practicalistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rober on 07/10/2014.
 */
public class Adaptador extends ArrayAdapter<String> {

    private Context contexto;
    private ArrayList<Mascota> mascotas;
    private int recurso;
    static LayoutInflater i;
    static String[] e;

    public static class ViewHolder{
        public TextView tv1, tv2, tv3, tv4;
        public int posicion;
        public ImageView iv;
    }

    public void setE(String[] e){
        this.e =e;
    }

    public Adaptador(Context context, int resource, ArrayList mascotas) {
        super(context, resource, mascotas);
        this.contexto=context;
        this.mascotas=mascotas;
        this.recurso=resource;
        this.i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if(convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tv1=(TextView)convertView.findViewById(R.id.tvNombre);
            vh.tv2=(TextView)convertView.findViewById(R.id.tvEspecie);
            vh.tv3=(TextView)convertView.findViewById(R.id.tvRaza);
            //vh.tv4=(TextView)convertView.findViewById(R.id.tvBiografia);
            vh.iv = (ImageView)convertView.findViewById(R.id.ivImagen);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }
        //String s=vh.tv1.getText().toString();
        vh.posicion = position;
        vh.tv1.setText(mascotas.get(position).getNombre());
        vh.tv2.setText(mascotas.get(position).getEspecie());
        vh.tv3.setText(mascotas.get(position).getRaza());
        //vh.tv4.setText(mascotas.get(position).getBiografia());

        if(mascotas.get(position).getEspecie().equals(e[0])){
            vh.iv.setImageResource(R.drawable.perro1);
        }
        if(mascotas.get(position).getEspecie().equals(e[1])){
            vh.iv.setImageResource(R.drawable.gato);
        }
        if(mascotas.get(position).getEspecie().equals(e[2])){
            vh.iv.setImageResource(R.drawable.conejo);
        }
        if(mascotas.get(position).getEspecie().equals(e[3])){
            vh.iv.setImageResource(R.drawable.pajaro1);
        }
        vh.iv.setTag(position);
        return convertView;

    }
}
