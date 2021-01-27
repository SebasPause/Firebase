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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnRegistrar,btnIniciar;
    EditText edCorreo,edContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        edCorreo = (EditText)findViewById(R.id.edCorreo);
        edContrasena = (EditText)findViewById(R.id.edContrasena);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(edCorreo.getText().toString(),edContrasena.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.i("Resultado task:",task.isSuccessful()+"");
                                Log.w("Mal","createUserWithEmail:failure",task.getException());

                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                    intent.putExtra("email",task.getResult().getUser().getEmail());
                                    startActivity(intent);
                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("Error");
                                    builder.setMessage("Se ha producido un error: "+task.getException());
                                    builder.setPositiveButton("Aceptar",null);
                                    builder.create().show();
                                }
                            }
                        });
            }
        });


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(edCorreo.getText().toString(),edContrasena.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithCustomToken:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    Intent intent = new Intent(MainActivity.this,Logueado.class);
                                    intent.putExtra("email",task.getResult().getUser().getEmail());
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithCustomToken:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        //Compruebo si existe el usuario
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    public void onIniciar(View view){

    }


}