package com.example.shieldbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class forgotPass_activity extends AppCompatActivity {

    EditText email3;
    Button send_link;

    Pattern EMAIL_REGEX;

    FirebaseAuth mAuth;

    String entered_email;

    Toast invalid_Toast, reset_mail_sent, reset_failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email3=findViewById(R.id.forgot_pass_email);
        send_link=findViewById(R.id.send_link);

        mAuth = FirebaseAuth.getInstance();

        invalid_Toast = Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT);
        reset_mail_sent = Toast.makeText(this, "reset mail sent", Toast.LENGTH_LONG);
        reset_failed = Toast.makeText(this, "password reset failed", Toast.LENGTH_LONG);

        EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        send_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgotPassword();

            }
        });

    }

    private void forgotPassword() {

        entered_email=email3.getText().toString().trim();

        if(entered_email.isEmpty())
        {
            email3.setError("Email required");
            invalid_Toast.show();
        }
        else if(!EMAIL_REGEX.matcher(entered_email).find())
        {
            email3.setError("invalid email address");
            invalid_Toast.show();
        }
        else
        {
            mAuth.sendPasswordResetEmail(entered_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        reset_mail_sent.show();
                        finish();
                    }
                    else
                    {
                        reset_failed.show();
                        email3.setError("email might not be registered");
                    }
                }
            });
        }

    }
}