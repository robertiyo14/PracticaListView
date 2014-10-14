package com.izv.practicalistview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Principal extends Activity {
    private ArrayList<Mascota> mascotas = new ArrayList<Mascota>();
    private Adaptador ad;
    String nombres[] = {"Nerón","Zira","Puerta"};
    String especies[] = {"Perro","Gato","Conejo"};
    String razas[]={"Pastor alemán","Otra","Angora"};
    String biografias[]={"Biografía del perro","Biografía del gato","Biografía del conejo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        initComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.action_anadir){
            anadir();
        }
        return super.onContextItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.long_clic, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        if(id==R.id.action_borrar){
            mascotas.remove(index);
            ordenar();
        }else if(id==R.id.action_modificar){
            modificar(index);
        }
        return super.onContextItemSelected(item);
    }


    public boolean modificar(final int index){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.modificar);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_alta, null);
        final EditText et1, et2;
        final RadioButton perro, gato, conejo, pajaro;
        et1 = (EditText)vista.findViewById(R.id.etNombre);
        et2 = (EditText)vista.findViewById(R.id.etRaza);
        et1.setText(mascotas.get(index).getNombre());
        et2.setText(mascotas.get(index).getRaza());
        perro = (RadioButton) vista.findViewById(R.id.rbPerro);
        gato = (RadioButton) vista.findViewById(R.id.rbGato);
        conejo = (RadioButton) vista.findViewById(R.id.rbConejo);
        pajaro = (RadioButton) vista.findViewById(R.id.rbPajaro);
        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String c = et1.getText().toString()+" "+et2.getText().toString();
                        c=c.trim();
                        if(c.length()>0){
                            String especie ="";
                            if(perro.isChecked()){especie=getString(R.string.perro);}
                            if(gato.isChecked()){especie=getString(R.string.gato);}
                            if(conejo.isChecked()){especie=getString(R.string.conejo);}
                            if(pajaro.isChecked()){especie=getString(R.string.pajaro);}
                            Mascota m=new Mascota(et1.getText().toString(),especie,et2.getText().toString());
                            mascotas.set(index, m);
                            ordenar();
                        }

                    }
                });
        alert.setNegativeButton("cancelar",null);
        alert.show();
        //tostada("Elemento añadido");
        return true;

    }

    private void initComponents(){
        for (int i = 0; i < nombres.length; i++) {
            String s=nombres[i];
            String s1=especies[i];
            String s2=razas[i];
            Mascota m = new Mascota(s,s1,s2);
            mascotas.add(m);
        }
        ad= new Adaptador(this, R.layout.lista_detalle,mascotas);
        final ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = view.getTag();
                Adaptador.ViewHolder vh = (Adaptador.ViewHolder) o;
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                //alert.setTitle(getResources().getString(R.string.new_event));
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View vista = inflater.inflate(R.layout.info, null);
                alert.setView(vista);
                //TextView nombre, especie, raza, biografia;
                TextView nombre=(TextView)findViewById(R.id.tvNo);
                //especie=(TextView)findViewById(R.id.tvEsp);
                //raza=(TextView)findViewById(R.id.tvRaz);
                //biografia=(TextView)findViewById(R.id.tvBio);
                nombre.setText(mascotas.get(i).getNombre());
                //especie.setText("hola");
                //raza.setText("hola");
                //biografia.setText("hola");
                alert.show();
            }
        });

        registerForContextMenu(lv);
        ordenar();
    }

    private boolean anadir(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.anadir);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_alta, null);

        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final EditText et1, et2;
                        final ImageView ivImagen;
                        RadioButton perro, gato, conejo, pajaro;
                        et1 = (EditText) vista.findViewById(R.id.etNombre);
                        et2 = (EditText) vista.findViewById(R.id.etRaza);
                        ivImagen = (ImageView) vista.findViewById(R.id.ivImagen);
                        perro = (RadioButton) vista.findViewById(R.id.rbPerro);
                        gato = (RadioButton) vista.findViewById(R.id.rbGato);
                        conejo = (RadioButton) vista.findViewById(R.id.rbConejo);
                        pajaro = (RadioButton) vista.findViewById(R.id.rbPajaro);
                        String nombre = et1.getText().toString();
                        String especie ="";
                        String raza = et2.getText().toString();
                        if(perro.isChecked()){especie=getString(R.string.perro);}
                        if(gato.isChecked()){especie=getString(R.string.gato);}
                        if(conejo.isChecked()){especie=getString(R.string.conejo);}
                        if(pajaro.isChecked()){especie=getString(R.string.pajaro);}
                        Mascota m = new Mascota(nombre,especie,raza);
                        mascotas.add(m);
                        ordenar();

                    }
                });
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
        //tostada("Elemento añadido");
        return true;
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void ordenar(){
        Collections.sort(mascotas);
        ad.notifyDataSetChanged();
    }
}
