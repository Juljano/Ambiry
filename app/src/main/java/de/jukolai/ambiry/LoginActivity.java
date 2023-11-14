package de.jukolai.ambiry;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ImageButton ImageButtonBackButton;
    Button loginButton;
    EditText editTextPassword, editTextEmailAdress;
    FirebaseAuth mAuth;
    TextView textViewForgotPassword;
    ConstraintLayout constraintLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButtonBackButton = findViewById(R.id.imageButtonBackbutton);
        editTextEmailAdress = findViewById(R.id.editTextEmailAdress);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        constraintLogin = findViewById(R.id.constraintLogin);


        textViewForgotPassword.setOnClickListener(v -> {

            View view = getLayoutInflater().inflate(R.layout.dialogpasswordlayout, null);

            Dialog dialog = new Dialog(this);
            dialog.setContentView(view);

            EditText editText;
            Button button;

            button = dialog.findViewById(R.id.DoneButton);
            editText = dialog.findViewById(R.id.resetPasswordEditText);

            button.setOnClickListener(v1 -> resetPassword(editText.getText().toString()));

            dialog.show();


        });


        loginButton.setOnClickListener(v -> userLogin());


        ImageButtonBackButton.setOnClickListener(v -> {
            //Back to RegisterActivity
            onBackPressed();
        });
    }


    private void userLogin() {

        if (editTextEmailAdress.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {

            Snackbar.make(constraintLogin, "Bitte alle Felder ausfüllen!", Snackbar.LENGTH_LONG).show();

            return;

        }

        String password = editTextPassword.getText().toString();
        String email = editTextEmailAdress.getText().toString().trim();

        // Get Firebase Auth-instance
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {

                        Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        finish();

                    } else {

                        // If sign in fails, display a message to the user.
                        Log.w("LOG", "signInWithEmail:failure", task.getException());

                        Snackbar.make(constraintLogin, "Die Anmeldung war nicht erfolgreich!", Snackbar.LENGTH_LONG).show();
                    }

                });


    }

    private void resetPassword(String Mail) {

        FirebaseAuth.getInstance().sendPasswordResetEmail(Mail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        Snackbar.make(constraintLogin, "E-Mail wurde versendet!", Snackbar.LENGTH_LONG).show();
                    }
                });


    }

}


