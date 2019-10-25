package com.example.profile2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class details extends AppCompatActivity {
//    FirebaseFirestore fb;
//    List<Entry> li;
    int Pick=234;
    ImageView imageH;
    private StorageReference storageReference;
    TextView name,add,dist,rp,rt,hf,bed,nb,np,type;
    Button phone,Loc;


    private Uri filepath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Pick  &&  data.getData()!=null)
        {
    filepath=data.getData();

        }
    }

//    public void upload() {
//
//        if (filepath != null) {
//            final ProgressDialog progressDialog=new ProgressDialog(this);
//            progressDialog.setTitle("Uploading..");
//            progressDialog.show();
//            StorageReference riversRef = storageReference.child("images/rivers1.jpg");
//
//            riversRef.putFile(filepath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG);
//                        }
//                    })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG);
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double p=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                    progressDialog.setMessage(((int)p+"% Uploaded.."));
//                }
//            });
//        }
//        else
//        {
//            Toast.makeText(this, "Error No file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void showfileChooser()
//    {
//        Intent i=new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//
//        startActivityForResult(Intent.createChooser(i,"Select an image"),Pick);
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        fb=FirebaseFirestore.getInstance();
//        li=new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Loc=findViewById(R.id.LOCATION);
storageReference= FirebaseStorage.getInstance().getReference();
//        choose=findViewById(R.id.choose);
        imageH=findViewById(R.id.imageH);
        FirebaseStorage storage=FirebaseStorage.getInstance();


//        img=findViewById(R.id.img);
//        up=findViewById(R.id.up);
//choose.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        showfileChooser();
//    }
//});
//up.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        upload();
//    }
//});
        name=findViewById(R.id.NameH);
        add=findViewById(R.id.add);
        dist=findViewById(R.id.dist);
        rp=findViewById(R.id.rp);
        rt=findViewById(R.id.rt);
        hf=findViewById(R.id.hf);
        nb=findViewById(R.id.nb);
        bed=findViewById(R.id.bed);
        np=findViewById(R.id.np);
        type=findViewById(R.id.type);
        phone=findViewById(R.id.phone);
        final Entry e;
            Intent i=getIntent();
            e=(Entry)i.getSerializableExtra("Entry");
        name.setText(e.getName().toString());
        add.setText(e.getAdd().toString());
        String z=e.getDistance().toString();
//        StorageReference storeref = storage.getReferenceFromUrl(e.getUrl());
//        try {
//            final File localfile=File.createTempFile("images","jpg");
//            storeref.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
//                    imageH.setImageBitmap(bitmap);
//
//                }
//            });
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

//StorageReference pathrefernce= IMAGEREF.child("images/")

Glide.with(getApplicationContext()).load(e.getUrl()).into(imageH);



        dist.setText(z.substring(9));
        rp.setText(e.getRp().toString());
        rt.setText(e.getRt().toString());
        hf.setText(e.getHf().toString());
        nb.setText(e.getNb().toString());
        bed.setText(e.getBed().toString());
        np.setText(e.getNp().toString());
        type.setText(e.getType().toString());
        phone.setText(e.getPhone().toString());
        Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iloc=new Intent(details.this,MapsActivity.class);
                iloc.putExtra("name", e.getName());
                startActivity(iloc);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no=e.getPhone().toString();
                no=no.substring(7);
                Uri u=Uri.parse("tel:"+no);
                Intent ni=new Intent(Intent.ACTION_DIAL,u);
                try{
                    startActivity(ni);


                } catch (SecurityException ex){
                    Toast.makeText(details.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
