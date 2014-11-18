package com.izv.practicalistview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
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
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Principal extends Activity {

    /***********************************************************************************/
    /***********************************************************************************/
    /*                                  VARIABLES                                      */
    /***********************************************************************************/
    /***********************************************************************************/

    private ArrayList<Mascota> mascotas = new ArrayList<Mascota>();
    private Adaptador ad;
    /*String nombres[] = {"Nerón","Zira","Puerta"};
    String especies[] = {"Perro","Gato","Conejo"};
    String razas[]={"Pastor alemán","Otra","Angora"};
    String biografias[]={"Biografía del perro","Biografía del gato","Biografía del conejo"};*/

    /***********************************************************************************/
    /***********************************************************************************/
    /*                                  MÉTODOS ON...                                  */
    /***********************************************************************************/
    /***********************************************************************************/

    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        if(id==R.id.action_borrar){
            mascotas.remove(index);
            ordenar();
            guardar();
        }else if(id==R.id.action_modificar){
            modificar(index);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        initComponents();
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.long_clic, menu);
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


    /***********************************************************************************/
    /***********************************************************************************/
    /*                               MÉTODOS DE LOS BOTONES                            */
    /***********************************************************************************/
    /***********************************************************************************/

    private boolean anadir(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.anadir);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_alta, null);

        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final EditText et1, et2,et3;
                        final ImageView ivImagen;
                        RadioButton perro, gato, conejo, pajaro;
                        et1 = (EditText) vista.findViewById(R.id.etNombre);
                        et2 = (EditText) vista.findViewById(R.id.etRaza);
                        et3 = (EditText) vista.findViewById(R.id.etBiografia);
                        ivImagen = (ImageView) vista.findViewById(R.id.ivImagen);
                        perro = (RadioButton) vista.findViewById(R.id.rbPerro);
                        gato = (RadioButton) vista.findViewById(R.id.rbGato);
                        conejo = (RadioButton) vista.findViewById(R.id.rbConejo);
                        pajaro = (RadioButton) vista.findViewById(R.id.rbPajaro);
                        String nombre = et1.getText().toString();
                        String especie ="";
                        String raza = et2.getText().toString();
                        String biografia= et3.getText().toString();
                        if(perro.isChecked()){especie=getString(R.string.perro);}
                        if(gato.isChecked()){especie=getString(R.string.gato);}
                        if(conejo.isChecked()){especie=getString(R.string.conejo);}
                        if(pajaro.isChecked()){especie=getString(R.string.pajaro);}
                        Mascota m = new Mascota(nombre,especie,raza);
                        m.setBiografia(biografia);
                        mascotas.add(m);
                        ordenar();
                        guardar();

                    }
                });
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
        //tostada("Elemento añadido");
        return true;
    }

    public boolean modificar(final int index){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.modificar);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_alta, null);
        final EditText et1, et2, et3;
        final RadioButton perro, gato, conejo, pajaro;
        et1 = (EditText)vista.findViewById(R.id.etNombre);
        et2 = (EditText)vista.findViewById(R.id.etRaza);
        et3 = (EditText)vista.findViewById(R.id.etBiografia);
        et1.setText(mascotas.get(index).getNombre());
        et2.setText(mascotas.get(index).getRaza());
        et3.setText(mascotas.get(index).getBiografia());
        perro = (RadioButton) vista.findViewById(R.id.rbPerro);
        gato = (RadioButton) vista.findViewById(R.id.rbGato);
        conejo = (RadioButton) vista.findViewById(R.id.rbConejo);
        pajaro = (RadioButton) vista.findViewById(R.id.rbPajaro);
        if(mascotas.get(index).getEspecie().equals(getString(R.string.perro))){perro.setChecked(true);}
        if(mascotas.get(index).getEspecie().equals(getString(R.string.gato))){gato.setChecked(true);}
        if(mascotas.get(index).getEspecie().equals(getString(R.string.conejo))){conejo.setChecked(true);}
        if(mascotas.get(index).getEspecie().equals(getString(R.string.pajaro))){pajaro.setChecked(true);}
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
                            m.setBiografia(et3.getText().toString());
                            mascotas.set(index, m);
                            ordenar();
                            guardar();
                        }

                    }
                });
        alert.setNegativeButton("cancelar",null);
        alert.show();
        //tostada("Elemento añadido");
        return true;

    }

    /***********************************************************************************/
    /***********************************************************************************/
    /*                                   OTROS MÉTODOS                                 */
    /***********************************************************************************/
    /***********************************************************************************/

    private void initComponents(){
        /*for (int i = 0; i < nombres.length; i++) {
            String s=nombres[i];
            String s1=especies[i];
            String s2=razas[i];
            Mascota m = new Mascota(s,s1,s2);
            m.setBiografia(biografias[i]);
            mascotas.add(m);
        }*/
        ad= new Adaptador(this, R.layout.lista_detalle,mascotas);
        String[] e = {getString(R.string.perro),getString(R.string.gato),getString(R.string.conejo),getString(R.string.pajaro)};
        ad.setE(e);
        final ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                //alert.setTitle(getResources().getString(R.string.new_event));
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View vista = inflater.inflate(R.layout.info, null);
                alert.setView(vista);
                TextView nombre, especie, raza, biografia;
                nombre=(TextView)vista.findViewById(R.id.tvNo);
                especie=(TextView)vista.findViewById(R.id.tvEsp);
                raza=(TextView)vista.findViewById(R.id.tvRaz);
                biografia=(TextView)vista.findViewById(R.id.tvBio);
                nombre.setText(mascotas.get(i).getNombre().toString());
                especie.setText(mascotas.get(i).getEspecie());
                raza.setText(mascotas.get(i).getRaza());
                biografia.setText(mascotas.get(i).getBiografia());
                alert.show();
            }
        });

        registerForContextMenu(lv);
        ordenar();
    }

    private void ordenar(){
        Collections.sort(mascotas);
        ad.notifyDataSetChanged();
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle savingInstanceState) {
        super.onSaveInstanceState(savingInstanceState);
        savingInstanceState.putSerializable("mascotas", mascotas);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mascotas = (ArrayList<Mascota>) savedInstanceState.getSerializable("mascotas");
        ad= new Adaptador(this, R.layout.lista_detalle,mascotas);
        String[] e = {getString(R.string.perro),getString(R.string.gato),getString(R.string.conejo),getString(R.string.pajaro)};
        ad.setE(e);
        final ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        ad.notifyDataSetChanged();
    }

    public void guardar(){
        try{
            FileOutputStream fosxml= new FileOutputStream(new File(getExternalFilesDir(null),"mascotas.xml"));
            XmlSerializer docxml = Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            docxml.startTag(null, "mascotas");
            for (Mascota m : mascotas) {
                docxml.startTag(null, "mascota");
                docxml.startTag(null, "nombre");
                docxml.text(m.getNombre());
                docxml.endTag(null, "nombre");
                docxml.startTag(null, "especie");
                docxml.text(m.getEspecie());
                docxml.endTag(null, "especie");
                docxml.startTag(null, "raza");
                docxml.text(m.getRaza());
                docxml.endTag(null, "raza");
                docxml.startTag(null, "biografia");
                docxml.text(m.getBiografia());
                docxml.endTag(null, "biografia");
                docxml.endTag(null, "mascota");
            }
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        }catch (Exception e){
        }


    }

    public void cargar(){

    }

}
