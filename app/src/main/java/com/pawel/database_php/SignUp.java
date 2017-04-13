package com.pawel.database_php;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button sign_up_button;
    private EditText sign_up_email,sign_up_password,sign_up_repeat_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        sign_up_email= (EditText) findViewById(R.id.sign_up_email);
        sign_up_email.setText("lidner12@gmail.com");

        sign_up_password= (EditText) findViewById(R.id.sign_up_password);
        sign_up_password.setText("szlendak1992");

        sign_up_repeat_password= (EditText) findViewById(R.id.sign_up_repeat_password);
        sign_up_repeat_password.setText("szlendak1992");

        sign_up_button = (Button) findViewById(R.id.sign_up_button);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = sign_up_email.getText().toString().trim();
                String password = sign_up_password.getText().toString().trim();
                String password_repeat = sign_up_repeat_password.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(password_repeat)) {
                    Toast.makeText(getApplicationContext(), "Passwords are different!", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Użytkownik "+task.getResult().getUser().getEmail().toString()+" zarejestrowany!",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            sign_up_email.setText(task.getException().toString());

                        }

                    }
                });
            }
        });



    }
}
