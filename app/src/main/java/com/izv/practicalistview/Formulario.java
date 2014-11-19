package com.izv.practicalistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class Formulario extends Activity {
    EditText etNombre, etRaza,etBiografia;
    RadioButton rbPerro,rbGato,rbPajaro,rbConejo;
    Button btAceptar,btCancelar;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogo_alta);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etRaza = (EditText)findViewById(R.id.etRaza);
        etBiografia = (EditText)findViewById(R.id.etBiografia);
        rbConejo = (RadioButton)findViewById(R.id.rbConejo);
        rbPajaro = (RadioButton)findViewById(R.id.rbPajaro);
        rbPerro = (RadioButton)findViewById(R.id.rbPerro);
        rbGato = (RadioButton)findViewById(R.id.rbGato);
        btAceptar = (Button)findViewById(R.id.btAceptar);
        btCancelar = (Button)findViewById(R.id.btCancelar);
        Bundle b = getIntent().getExtras();
        index=0;
        if(b !=null ){
            index = b.getInt("index");
            Mascota m = (Mascota)b.getSerializable("mascota");
            etNombre.setText(m.getNombre());
            etRaza.setText(m.getRaza());
            etBiografia.setText(m.getBiografia());
            if(m.getEspecie().equals(getString(R.string.perro))){rbPerro.setChecked(true);}
            if(m.getEspecie().equals(getString(R.string.gato))){rbGato.setChecked(true);}
            if(m.getEspecie().equals(getString(R.string.conejo))){rbConejo.setChecked(true);}
            if(m.getEspecie().equals(getString(R.string.pajaro))){rbPajaro.setChecked(true);}
        }
        if(savedInstanceState != null){
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cancelar(View v){
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }

    public void aceptar(View v){
        String nombre,especie,raza,biografia;
        especie="";
        nombre = etNombre.getText().toString();
        raza = etRaza.getText().toString();
        biografia = etBiografia.getText().toString();
        if(rbPerro.isChecked())especie = getString(R.string.perro);
        if(rbPajaro.isChecked())especie = getString(R.string.pajaro);
        if(rbConejo.isChecked())especie = getString(R.string.conejo);
        if(rbGato.isChecked())especie = getString(R.string.gato);
        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("nombre",nombre);
        bundle.putString("especie",especie);
        bundle.putString("raza",raza);
        bundle.putString("biografia", biografia);
        bundle.putInt("index",index);
        i.putExtras(bundle);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
