package de.jukolai.ambiry;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    TextView textviewExistingAccount;
    EditText editTextPassword, editTextEmailAdress;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ConstraintLayout constraintLayoutRegister;
    private String password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.registerButton);
        textviewExistingAccount = findViewById(R.id.textviewExistingAccount);
        editTextEmailAdress = findViewById(R.id.editTextEmailAdress);
        editTextPassword = findViewById(R.id.editTextPassword);
        constraintLayoutRegister = findViewById(R.id.constraintRegister);


        // if the user has a account then goto login page
        textviewExistingAccount.setOnClickListener(v -> {

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        registerButton.setOnClickListener(v -> {

            email = editTextEmailAdress.getText().toString();
            password = editTextPassword.getText().toString();

            // if a edittext is empty or the password is too short, then get the user a warnung
            if (!TextUtils.isEmpty(editTextEmailAdress.getText()) || !TextUtils.isEmpty(editTextPassword.getText())) {

                if (!(password.length() >= 8) || !email.contains("@")) {

                    Snackbar.make(constraintLayoutRegister, "E-Mail-Adresse richtig oder Passwort zu klein?", Snackbar.LENGTH_LONG).show();

                    return;
                }

                createUserAccount(email, password);
            }
        });


    }


    @Override
    protected void onStart() {

        isUserLoggedin();

        super.onStart();

    }

    private void isUserLoggedin() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void createUserAccount(String email, String password) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Intent intentHome = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intentHome);
                finish();


            } else {


                //get Error-Code
                @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                switch (errorCode) {


                    case "ERROR_INVALID_EMAIL":
                        Toast.makeText(this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                        editTextEmailAdress.setError("The email address is badly formatted.");
                        editTextEmailAdress.requestFocus();
                        break;

                    case "ERROR_WRONG_PASSWORD":
                        Snackbar.make(constraintLayoutRegister, "Das Passwort ist nicht korrekt", Snackbar.LENGTH_LONG).show();
                        editTextPassword.requestFocus();
                        editTextPassword.setText("");
                        break;

                    case "ERROR_USER_MISMATCH":
                        Toast.makeText(this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_REQUIRES_RECENT_LOGIN":
                        Toast.makeText(this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                        Snackbar.make(constraintLayoutRegister, "Es existiert schon ein Account mit ein anderes Passwort", Snackbar.LENGTH_LONG).show();

                        break;

                    case "ERROR_EMAIL_ALREADY_IN_USE":
                        Snackbar.make(constraintLayoutRegister, "Es existiert schon ein Account mit dieser E-Mail-Adresse", Snackbar.LENGTH_LONG).show();
                        editTextEmailAdress.requestFocus();
                        break;

                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                        Toast.makeText(this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_DISABLED":
                        Snackbar.make(constraintLayoutRegister, "Dieser Account wurde gesperrt!", Snackbar.LENGTH_LONG).show();

                        break;

                    case "ERROR_USER_NOT_FOUND":
                        Snackbar.make(constraintLayoutRegister, "Es wurde kein Account gefunden!", Snackbar.LENGTH_LONG).show();

                        break;

                    case "ERROR_WEAK_PASSWORD":
                        Toast.makeText(this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                        editTextPassword.setError("The password is invalid it must 6 characters at least");
                        editTextPassword.requestFocus();
                        break;

                }

            }
        });
    }

}