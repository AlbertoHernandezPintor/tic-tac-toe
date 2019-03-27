package com.example.a3enraya;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {


    public int jugadores; //Para guardar si hay uno o dos jugadores
    public int[] casillas; //Casillas de juego, 9
    public Partida partida; //Objeto de tipo Partida
    public int figura = 1; //Si elige aspas o circulos
    public int eleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos un array de casillas que identifica cada casilla de juego y la almacena en el array
        casillas = new int[9];
        casillas[0] = R.id.a1;
        casillas[1] = R.id.a2;
        casillas[2] = R.id.a3;
        casillas[3] = R.id.b1;
        casillas[4] = R.id.b2;
        casillas[5] = R.id.b3;
        casillas[6] = R.id.c1;
        casillas[7] = R.id.c2;
        casillas[8] = R.id.c3;
    }

    public void empezarJuego(View vista){
        ImageView imagen;

        //Resetear tablero
        for(int casilla : casillas){
            imagen = (ImageView) findViewById(casilla); //Cojo la imagen de cada casilla
            imagen.setImageResource(R.drawable.casilla); //Cambio la imagen por una en blanco
        }

        //Establecemos los jugadores que van a jugar la partida
        jugadores = 1; //Valor por defecto

        //Si son dos jugadores, se comprueba cual es el botón pulsado que se corresponde con vista
        if(vista.getId() == R.id.dosJugadores){
            jugadores = 2;
        }

        elegirImagen();

        //Elegir dificultad
        RadioGroup dificultad = (RadioGroup) findViewById(R.id.configDificultad);
        int id = dificultad.getCheckedRadioButtonId(); //Almacenamos el id del radio button seleccionado
        int dificultadElegida = 0; //Dificultad por defecto que se corresponde con fácil

        if(id == R.id.normal){
            dificultadElegida = 1;
        }else if(id == R.id.imposible){
            dificultadElegida = 2;
        }

        //Comenzar partida
        partida = new Partida(dificultadElegida);

        //Inhabilitar botones
        ((Button)findViewById(R.id.unJugador)).setEnabled(false);
        ((Button)findViewById(R.id.dosJugadores)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.configDificultad)).setAlpha(0); //Hace que sean invisibles
    }

    public void toque(View vista){
        //Comprobamos que se ha iniciado la partida
        if(partida == null){
            return;
        }

        int casilla = 0;

        //Recorrer el array de casillas
        for(int i = 0; i < casillas.length; i++) {
            if (casillas[i] == vista.getId()) {
                casilla = i; //Almacenamos cual es la casilla pulsada
                break;
            }
        }

        //Comprobamos si la casilla pulsada esta ocupada
        if(partida.comprobarCasilla(casilla) == false){
            return;
        }else {
            marca(casilla);
        }

        int resultado;

        resultado = partida.turno(); //Cambiamos el turno

        if(resultado > 0){
            terminar(resultado);
            return;
        }

        if(jugadores == 1) {
            //La respuesta de la ia
            casilla = partida.ia();

            //Comprobar si la casilla elegida por la ia esta ocupada
            while (partida.comprobarCasilla(casilla) == false) {
                casilla = partida.ia();

            }

            marca(casilla);
            resultado = partida.turno(); //Devolvemos turno a jugador 1


            if (resultado > 0) {
                terminar(resultado);
            }
        }
    }

    private void terminar(int resultado){
        String mensaje;

        if(resultado == 1 && eleccion == 0){
            mensaje = "Ganan círculos";
        }else if(resultado == 1 && eleccion == 1){
            mensaje = "Ganan aspas";
        }else if(resultado == 2 && eleccion == 0){
            mensaje = "Ganan aspas";
        }else if(resultado == 2 && eleccion == 1){
            mensaje = "Ganan círculos";
        }else{
            mensaje = "Empate";
        }

        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        partida = null;
        //Habilitar botones
        ((Button)findViewById(R.id.unJugador)).setEnabled(true);
        ((Button)findViewById(R.id.dosJugadores)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.configDificultad)).setAlpha(1); //Hace que sean invisibles
    }

    private void marca(int casilla){
        ImageView imagen;
        imagen = (ImageView)findViewById(casillas[casilla]);

        //Dependiendo de que jugador sea cambia por círculos o aspas
        if(partida.jugador == 1 && eleccion == 0){
            imagen.setImageResource(R.drawable.circulo);
        }else if(partida.jugador == 1 && eleccion == 1){
            imagen.setImageResource(R.drawable.aspa);
        }else if(partida.jugador == 2 && eleccion == 0){
            imagen.setImageResource(R.drawable.aspa);
        }else{
            imagen.setImageResource(R.drawable.circulo);
        }
    }

    public void elegirImagen(){
        Intent i =  new Intent(this, ElegirTipo.class);

        startActivityForResult(i, figura); //Para lanzar la actividad
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            eleccion = data.getIntExtra("Resultado", 1);
        }
    }
}
