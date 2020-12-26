package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {


    EditText emailUser,passwordUser;
    Button register_btn,signin_btn,forgetpassword_btn;
    ProgressBar progressBar;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        emailUser = findViewById(R.id.useremailSignin);
        passwordUser = findViewById(R.id.userpasswordSign);
        signin_btn = findViewById(R.id.button_signin);
        register_btn = findViewById(R.id.button_signup);
        forgetpassword_btn=findViewById(R.id.btn_reset_password);
        progressBar = findViewById(R.id.progressbar_signin);

        mAuth = FirebaseAuth.getInstance();


        forgetpassword_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin.this,Signup_Form.class);
                startActivity(intent);
            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = emailUser.getText().toString();
                String pass = passwordUser.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){
                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                sendtoMain();
                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(Signin.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else {
                    Toast.makeText(Signin.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void sendtoMain() {
        Intent intent = new Intent(Signin.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null){
            Intent intent = new Intent(Signin.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

