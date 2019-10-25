package com.example.profile2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    TextView b1;
    Button b;
    FirebaseAuth fb;

    @Override
    protected void onStart() {
        super.onStart();
        if(fb.getCurrentUser()!=null)
        {
            //login to specific Activity
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fb=FirebaseAuth.getInstance();
        b1=(TextView)findViewById(R.id.register);
        b=findViewById(R.id.button);
        emailEditText=findViewById(R.id.mail);
        passEditText=findViewById(R.id.password);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String u, p;
                u = emailEditText.getText().toString().trim();
                p = passEditText.getText().toString().trim();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (u.length() == 0 || p.length() == 0) {
                    Toast.makeText(MainActivity.this, "Login fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
else{
                fb.signInWithEmailAndPassword(u, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> li;
                                    li = queryDocumentSnapshots.getDocuments();
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        int x = 0;
                                        for (DocumentSnapshot i : li) {
                                            String AA = (String) i.get("AccountType");
                                            String UU = (String) i.get("Username");
                                            if (UU.equals(u)) {
                                                if (AA.equals("Owner")) {
                                                    x = 1;
                                                    // System.out.println("Owner");

                                                }
                                                break;

                                            }

                                        }
                                        if (x == 0) {
                                            Intent i1 = new Intent(MainActivity.this, Mainpage.class);
                                            startActivity(i1);
                                        } else {
                                            Intent i = new Intent(MainActivity.this, Owner.class);
                                            i.putExtra("Username", u);
                                            startActivity(i);
                                        }

                                    }
                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
        // Address the email and password field
        emailEditText = (EditText) findViewById(R.id.mail);
        passEditText = (EditText) findViewById(R.id.password);

    }

//    public void checkLogin(View arg0) {
//
//        final String email = emailEditText.getText().toString();
//        if (!isValidEmail(email)) {
//            //Set error message for email field
//            emailEditText.setError("Invalid Email");
//        }
//
//        final String pass = passEditText.getText().toString();
//        if (!isValidPassword(pass)) {
//            //Set error message for password field
//            passEditText.setError("Password cannot be empty");
//        }
//
//        if(isValidEmail(email) && isValidPassword(pass))
//        {
//            // Validation Completed
//        }
//
//    }
//
//    // validating email id
//    private boolean isValidEmail(String email) {
//        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//
//    // validating password
//    private boolean isValidPassword(String pass) {
//        if (pass != null && pass.length() >= 4) {
//            return true;
//        }
//        return false;
//    }
}
