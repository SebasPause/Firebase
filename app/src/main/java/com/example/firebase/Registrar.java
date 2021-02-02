package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrar extends AppCompatActivity {

    EditText correo,contrasena;
    Button volver,guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        correo = (EditText)findViewById(R.id.editTextTextEmailAddress);
        contrasena = (EditText)findViewById(R.id.editTextTextPassword);
        volver = (Button)findViewById(R.id.btnVolver);
        guardar = (Button)findViewById(R.id.btnRegistrarse);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty()) {
                    Toast.makeText(Registrar.this, "Introduce correo y/o contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                            .addOnCompleteListener(Registrar.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.i("Resultado task:", task.isSuccessful() + "");
                                    Log.w("Mal", "createUserWithEmail:failure", task.getException());

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Registrar.this, MainActivity.class);
                                        intent.putExtra("email", task.getResult().getUser().getEmail());
                                        startActivity(intent);
                                    } else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registrar.this);
                                        builder.setTitle("Error");
                                        builder.setMessage("Se ha producido un error: " + task.getException());
                                        builder.setPositiveButton("Aceptar", null);
                                        builder.create().show();
                                    }
                                }
                            });
                }
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Registrar.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}