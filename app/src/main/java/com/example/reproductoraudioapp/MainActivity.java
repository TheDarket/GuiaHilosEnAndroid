package com.example.reproductoraudioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reproductoraudioapp.utils.AudioAsincrono;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar, btnReiniciar, btnDatos;
    private TextView txvActual, txvFinal;
    private AudioAsincrono audioAsincrono;
    boolean estadoBoton;
    boolean suspender;
    private AudioAsincrono actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suspender = true;
        estadoBoton = true;
        txvActual = findViewById(R.id.txvActual);
        txvFinal = findViewById(R.id.txvFinal);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        btnDatos = findViewById(R.id.btnDatos);


        btnIniciar.setOnClickListener(v -> {
            if (estadoBoton == true) {
                btnIniciar.setText("Pausar");
                estadoBoton = false;
            } else {
                btnIniciar.setText("Reanudar");
                estadoBoton = true;
            }
            iniciar();
        });
        /*public void pulsarBoton (View v){
            if (estadoBoton == true){
                btnIniciar.setText("Pausar");
                estadoBoton = false;
            }else{
                btnIniciar.setText("Reanudar");
                estadoBoton = true;
            }
        }*/

        //reinicia una Activity
        btnReiniciar.setOnClickListener(v -> {
                this.btnReiniciar= null;
                String reiniciar = String.valueOf((Object) null);
                txvActual.setText("00.00");
                txvFinal.setText("00.00");
            });
        btnDatos = findViewById(R.id.btnDatos);
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, datos.class);
                startActivity(intent);
            }
        });

    }

    private void iniciar() {
        if ( audioAsincrono == null ) {
            audioAsincrono = new AudioAsincrono(MainActivity.this, txvActual, txvFinal);
            audioAsincrono.execute();
        } else if ( audioAsincrono.getStatus() == AsyncTask.Status.FINISHED ) {
            audioAsincrono = new AudioAsincrono(MainActivity.this, txvActual, txvFinal);
            audioAsincrono.execute();
        } else if (audioAsincrono.getStatus() == AsyncTask.Status.RUNNING && !audioAsincrono.esPause() ) { // En caso de que este corriendo y no este pausado; entonces se pausa.
            audioAsincrono.pausarAudio();
        } else { // En caso de que este pausado; entonces se debe reanudar
            audioAsincrono.reanudarAudio();
        }
    }

}