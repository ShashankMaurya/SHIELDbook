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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class registration_activity extends AppCompatActivity {

    String valid_email, pass;
    EditText email2, name, password2, confirmPassword;
    Button signup2;
    Pattern EMAIL_REGEX;
    Toast invalid_Toast, valid_toast, acc_fail, acc_succ;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email2 = findViewById(R.id.email2);
        name = findViewById(R.id.name);
        password2 = findViewById(R.id.password2);
        confirmPassword = findViewById(R.id.confirm_password);

        signup2 = findViewById(R.id.signup2);

        mAuth = FirebaseAuth.getInstance();

        EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        invalid_Toast = Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT);
        valid_toast = Toast.makeText(this, "valid Data", Toast.LENGTH_SHORT);
        acc_fail = Toast.makeText(this, "Account not created", Toast.LENGTH_LONG);
        acc_succ = Toast.makeText(this, "Account created successfully", Toast.LENGTH_LONG);

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerNewUser();
//                valid_toast.show();

            }
        });

    }

    private void registerNewUser() {

        boolean flag_pass = false, flag_email = false;

//        email2.setError(null);
//        name.setError(null);
//        password2.setError(null);
//        confirmPassword.setError(null);
//
//        if(email2.getText().toString().isEmpty())
//        {
//            email2.setError("Email Required");
//            invalid_Toast.show();
////            return;
//        }
//        if(name.getText().toString().isEmpty())
//        {
//            name.setError("Name Required");
//            invalid_Toast.show();
////            return;
//        }
//        if(password2.getText().toString().isEmpty())
//        {
//            password2.setError("Password Required");
//            invalid_Toast.show();
////            return;
//        }
//        if(confirmPassword.getText().toString().isEmpty())
//        {
//            confirmPassword.setError("Confirmation Required");
//            invalid_Toast.show();
////            return;
//        }
        if(checkNull()) {
            if (!EMAIL_REGEX.matcher(email2.getText().toString().trim()).find()) {
                email2.setError("invalid email address");
                invalid_Toast.show();
//            return;
            } else {
                valid_email = email2.getText().toString().trim();
                flag_email = true;
            }
            if (!password2.getText().toString().equals(confirmPassword.getText().toString())) {
                confirmPassword.setError("Does not match with the Password");
                invalid_Toast.show();
//            return;
            }
            else {
                pass = password2.getText().toString().trim();
                flag_pass = true;
            }

//            valid_email = email2.getText().toString().trim();
//            pass = password2.getText().toString().trim();

//        signup2.setText(valid_email);
//            valid_toast.show();

            if(flag_email && flag_pass)
            {
                mAuth.createUserWithEmailAndPassword(valid_email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            acc_succ.show();
                            finish();
                        }
                        else
                        {
                            acc_fail.show();
                        }

                    }
                });
            }

        }
        else{
            invalid_Toast.show();
        }
    }

    private boolean checkNull()
    {
        boolean f = true;
        if(email2.getText().toString().isEmpty())
        {
            email2.setError("Email Required");
//            invalid_Toast.show();
//            return;
            f = false;
        }
        if(name.getText().toString().isEmpty())
        {
            name.setError("Name Required");
//            invalid_Toast.show();
//            return;
            f=false;
        }
        if(password2.getText().toString().isEmpty())
        {
            password2.setError("Password Required");
//            invalid_Toast.show();
//            return;
            f=false;
        }
        if(confirmPassword.getText().toString().isEmpty())
        {
            confirmPassword.setError("Confirmation Required");
//            invalid_Toast.show();
//            return;
            f=false;
        }
        return f;
    }
}