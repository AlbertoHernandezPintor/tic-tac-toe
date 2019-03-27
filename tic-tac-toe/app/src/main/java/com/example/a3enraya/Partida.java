package com.example.a3enraya;

import android.widget.RadioGroup;

import java.util.Random;

public class Partida {
    public final int dificultad;
    public int jugador;
    private int[] casillasOcupadas;
    private final int [][] combinaciones = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public Partida (int dificultad){
        this.dificultad = dificultad;
        jugador = 1;
        casillasOcupadas = new int[9];

        for(int i = 0; i < casillasOcupadas.length; i++){
            casillasOcupadas[i] = 0;
        }
    }

    public boolean comprobarCasilla(int casilla){
        if(casillasOcupadas[casilla] != 0){
            return false;
        }else{
            casillasOcupadas[casilla] = jugador;
            return true;
        }
    }

    public int ia(){
        int casilla;

        casilla = casillaClave(2); //Miramos a ver si la ia puede hacer dos en raya
        if(casilla != -1){
            return casilla;
        }

        if(dificultad > 0){
            casilla = casillaClave(1); //La ia mira a ver si el humano puede ganar
            if(casilla != -1){
                return casilla;
            }
        }

        if(dificultad == 2){
            if(casillasOcupadas[0] != 0 || casillasOcupadas[2] != 0 || casillasOcupadas[6] != 0 || casillasOcupadas[8] != 0){
                if(casillasOcupadas[4] == 0){
                    return 4;
                }
            }

            if((casillasOcupadas[0] != 0 && casillasOcupadas[8] != 0) || (casillasOcupadas[2] != 0 || casillasOcupadas[6] != 0)){
                if(casillasOcupadas[7] == 0) {
                    return 7;
                }else if(casillasOcupadas[5] == 0){
                    return 5;
                }else if(casillasOcupadas[1] == 0){
                    return 1;
                }else if(casillasOcupadas[3] == 0){
                    return 3;
                }
            }


            if(casillasOcupadas[0] == 0){
                return 0;
            }else if(casillasOcupadas[2] == 0){
                return 2;
            }else if(casillasOcupadas[6] == 0){
                return 6;
            }else{
                return 8;
            }
        }

        Random casillaAleatoria = new Random();

        casilla = casillaAleatoria.nextInt(9); //Número aleatorio del 0 al 8

        return casilla;
    }

    public int turno(){
        boolean empate = true; //Para el empate
        boolean ultimoMovimiento = true;

        for(int i = 0; i < combinaciones.length; i++){
            for(int j : combinaciones[i]){
                //Comprueba si uno de los jugadores ha ganado
                if(casillasOcupadas[j] != jugador){
                    ultimoMovimiento = false;
                }

                if(casillasOcupadas[j] == 0){
                    empate = false;
                }
            }
            //Uno de los jugadores ha ganado
            if(ultimoMovimiento){
                return jugador;
            }
            ultimoMovimiento = true;
        }

        //Comprobar si hemos empatado
        if(empate){
            return 3;
        }

        jugador++;

        if(jugador > 2){
            jugador = 1;
        }

        return 0; //No ha ocurrido nada especial
    }

    public int casillaClave(int jugadorTurno){
        int casilla = -1;
        int numeroDeCasillas = 0;

        for(int i = 0; i < combinaciones.length; i++){
            for(int j : combinaciones[i]){
                if(casillasOcupadas[j] == jugadorTurno){
                    numeroDeCasillas++;
                }else if(casillasOcupadas[j] == 0){
                    casilla = j;
                }
            }
            if(numeroDeCasillas == 2 && casilla != -1){
                return casilla;
            }

            casilla = -1;
            numeroDeCasillas = 0;
        }

        return -1;
    }
}
