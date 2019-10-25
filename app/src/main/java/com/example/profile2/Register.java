package com.example.profile2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
EditText name,mail,mobile,pass,rpass,ph;
Button signup;
Spinner spin;
FirebaseAuth fb;
public void check(View v)
{
    mobile=(EditText)findViewById(R.id.mobile);
    String phh = mobile.getText().toString().trim();
    if(phh.length()<10)mobile.setError("Enter Valid Phone Number");
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mobile=(EditText)findViewById(R.id.mobile);

        name=(EditText)findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.password);
        rpass=findViewById(R.id.rpassword);
        ph=findViewById(R.id.mobile);
        signup=findViewById(R.id.signup);
        spin=findViewById(R.id.Atype);
        fb=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phh = mobile.getText().toString().trim();
                if(phh.length()<10)mobile.setError("Enter Valid Phone Number");
                String e=mail.getText().toString().trim();
//                if(!(e.contains(('@'))))mail.setError("Enter Valid  Email");
                String p=pass.getText().toString().trim();
                final String u=mail.getText().toString().trim();
                String rp=rpass.getText().toString().trim();
                final String nn=name.getText().toString();
                final String pp=ph.getText().toString();
//                if(phh==null || pp==null || nn==null || e==null || rp==null)
//                {
//                    Toast.makeText(Register.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(Register.this,MainActivity.class);
//                    startActivity(i);
//
//                }
                if(phh.length()==0   || pp.length()==0)
                {
//                    Toast.makeText(Register.this, "Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(Register.this,MainActivity.class);
//                    startActivity(i);
                    ph.setError("Phone Number cannot be empty");
                }
                if(nn.length()==0 )
                {
//                    Toast.makeText(Register.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    name.setError("Name cannot be empty");
//
                }

                if(e.length()==0)
                {
//                    Toast.makeText(Register.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(Register.this,MainActivity.class);
//                    startActivity(i);
                    mail.setError("Email cannot be empty");
                }
                if(p.length()==0 || rp.length()==0)
                {
//                    Toast.makeText(Register.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(Register.this,MainActivity.class);
//                    startActivity(i);
                    pass.setError("Password cannot be empty");
                }
                if(!(p.equals(rp)))
                {
                    Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else if(p.length()!=0 && e.length()!=0 && nn.length()!=0 && phh.length()==10)
                {
                    final ProgressBar progressBar = null;
//                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    db.collection("Users").whereEqualTo("Username",e).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task!=null)
                            {
                                Toast.makeText(Register.this, "User Already registered", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(Register.this,MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });

                    fb.createUserWithEmailAndPassword(u,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
//                                progressBar.setVisibility(View.GONE);
                                 String AA="User";
                                String ATYPE=spin.getSelectedItem().toString();
                                if(ATYPE.equals("Hostel Owner"))
                                {
                                    AA="Owner";
                                }
//                                User user=new User(u,nn,pp,AA);
                                Map<String,String> user=new HashMap<>();
                                user.put("Name",nn);
                                user.put("Username",u);
                                user.put("Phone",pp);
                                user.put("AccountType",AA);
                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                db.collection("Users").document(u).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Register.this, "User Signup Successful", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Register.this, "User Signup Unsuccessful", Toast.LENGTH_SHORT).show();
                                        Log.d("TAG",e.toString());
                                    }
                                });
                                Intent intent=new Intent(Register.this,MainActivity.class);
                                startActivity(intent);

                            }





                            }


                    });

                }


            }
        });
    }
}
