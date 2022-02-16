package com.example.shieldbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class login_activity extends AppCompatActivity {

    EditText email, password;
    Button forgotPass, login, signup;
    FirebaseAuth mAuth;
    String entered_email, entered_pass;
    Toast invalid_Toast, valid_toast, acc_succ, acc_fail;
    Pattern EMAIL_REGEX;

    Intent intent1, intent2, intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        invalid_Toast = Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT);
        valid_toast = Toast.makeText(this, "valid Data", Toast.LENGTH_SHORT);
//        acc_fail = Toast.makeText(this, "INVALID CREDENTIALS", Toast.LENGTH_LONG);
        acc_succ = Toast.makeText(this, "Login successful", Toast.LENGTH_LONG);

        forgotPass = findViewById(R.id.forgot_password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        intent1 = new Intent(this, registration_activity.class);
        intent2 = new Intent(this, MainActivity.class);
        intent3 = new Intent(this, forgotPass_activity.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent1);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent3);

            }
        });

    }

    private void loginUser() {

        entered_email=email.getText().toString().trim();
        entered_pass=password.getText().toString();

        if(checkNull())
        {
            if (!EMAIL_REGEX.matcher(entered_email).find()) {
                email.setError("invalid email address");
                invalid_Toast.show();
            }
            else
            {
                mAuth.signInWithEmailAndPassword(entered_email, entered_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            acc_succ.show();
                            startActivity(intent2);
                        }
                        else
                        {
                            invalid_Toast.show();
                            email.setError("INVALID email or password");
                            password.setError("INVALID email or password");
                        }
                    }
                });
            }
        }
        else
        {
            invalid_Toast.show();
        }

    }

    private boolean checkNull() {

        boolean f=true;

        if(entered_email.isEmpty())
        {
            email.setError("Email required");
            f=false;
        }
        if(entered_pass.isEmpty())
        {
            password.setError("Password required");
            f=false;
        }
        return f;
    }
}