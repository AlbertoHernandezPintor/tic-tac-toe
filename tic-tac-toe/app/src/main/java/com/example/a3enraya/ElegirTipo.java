package com.example.a3enraya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ElegirTipo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elegir_figura);
    }

    public void elegir(View vista){
        if(vista.getId() == R.id.botonCirculo){
            Intent i = getIntent();
            i.putExtra("Resultado", 0);
            setResult(RESULT_OK, i);
            finish();
        }else{
            Intent i = getIntent();
            i.putExtra("Resultado", 1);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
