package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class OlvidarContrasena extends AppCompatActivity {

    EditText correo;
    Button volver,enviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasena);

        correo = (EditText)findViewById(R.id.emailOlvidarContra);
        volver = (Button)findViewById(R.id.btnVolver2);
        enviar = (Button)findViewById(R.id.btnEnviarCorreo);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OlvidarContrasena.this,MainActivity.class);
                startActivity(intent);
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correo.getText().toString().isEmpty()){
                    Toast.makeText(OlvidarContrasena.this, "Introduce tu correo", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(correo.getText().toString());
                    Toast.makeText(OlvidarContrasena.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}