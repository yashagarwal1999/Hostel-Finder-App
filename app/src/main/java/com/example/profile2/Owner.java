package com.example.profile2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Owner extends AppCompatActivity {
Button AddH,Signout;
String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
    AddH=findViewById(R.id.AddHostel);
    Intent i=getIntent();
        Username=(String)i.getSerializableExtra("Username");
    AddH.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Owner.this,OwnerAdd.class);
            intent.putExtra("Username",(String)Username);
            startActivity(intent);

        }
    });
Signout=findViewById(R.id.Signout);
Signout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent I=new Intent(Owner.this,MainActivity.class);
        startActivity(I);
    }
});
    }
}
