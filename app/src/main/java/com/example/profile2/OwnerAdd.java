package com.example.profile2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OwnerAdd extends AppCompatActivity {
    ImageView imageH;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;


    LocationListener locationListener;
    String Username;
    private StorageReference storageReference;
    EditText name,add,dist,rp,rt;
    EditText phone;
    TextView SelectedImage;
    Spinner hf,nb,bed,type,np;
    Button SUBMIT,choose,LOC,Record;
    String URLIMAGE;
    int rrq=123;
   static   int COUNTER=0;
    Task<Uri> urlTask;
    private Uri filepath;
    double lat,lon;
     Map<String,String> map;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==rrq && data.getData()!=null)
        {
            filepath=data.getData();
            SelectedImage.setText(data.getDataString());
        }

    }
    public void upload()
    {

        if(filepath!=null)
        {

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading Image..");
            progressDialog.show();
            StorageReference sr= FirebaseStorage.getInstance().getReference();
            Random rand = new Random();
           COUNTER=rand.nextInt(1000000);
            String IMAGEPATH="images/"+Username+ String.valueOf(COUNTER)  +".jpg";
COUNTER++;
            final StorageReference imgpoint=sr.child(IMAGEPATH);
//            imgpoint.putFile(filepath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
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
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double p=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                            progressDialog.setMessage(((int)p+"% Uploaded.."));
//                        }
//                    });

            UploadTask uploadTask=imgpoint.putFile(filepath);

             urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    else
                    {
                        progressDialog.dismiss();
                    }

                    // Continue with the task to get the download URL
                    return imgpoint.getDownloadUrl();
                }
            });




        }
        else
        {
            Toast.makeText(this, "Error No file selected", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic
        return false;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add);
        Intent it=getIntent();
        lat=0.0;
        lon=0.0;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



Record=findViewById(R.id.LOcation);
Record.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fetchlastLocation();
        Toast.makeText(OwnerAdd.this, "Cuurent GPS Location recorded as Hostel Location", Toast.LENGTH_SHORT).show();
    }
});
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Username=(String)it.getSerializableExtra("Username");
        name=findViewById(R.id.NameH);
        add=findViewById(R.id.add);
        dist=findViewById(R.id.dist);
        rp=findViewById(R.id.rp);
        rt=findViewById(R.id.rt);
        hf=findViewById(R.id.hf);
        SelectedImage=findViewById(R.id.SelectedImage);
        nb=findViewById(R.id.nb);
        bed=findViewById(R.id.bed);
        np=findViewById(R.id.np);
        type=findViewById(R.id.type);
        phone=findViewById(R.id.phone1);
        SUBMIT=findViewById(R.id.SUBMIT);
        choose=findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showfileChoser();
            }
        });
        SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String ADDRESS=add.getText().toString();
                String DISTNACE=dist.getText().toString();
                String RENTP=rp.getText().toString();
                String RENTT=rt.getText().toString();
                String HF=hf.getSelectedItem().toString();
                String NB=nb.getSelectedItem().toString();
                String BED=bed.getSelectedItem().toString();
                String NP=np.getSelectedItem().toString();
                String TY=type.getSelectedItem().toString();
                String PHone=phone.getText().toString();
                String SELECTEDIMAGE=SelectedImage.getText().toString();
                boolean Px=true;
                if(PHone.length()!=10)
                {
                    Toast.makeText(OwnerAdd.this, "Phone number should be 10 digits", Toast.LENGTH_SHORT).show();
                }
               else if(!(Name.isEmpty() && ADDRESS.isEmpty() && DISTNACE.isEmpty() && RENTP.isEmpty() && RENTT.isEmpty() && HF.isEmpty() &&
                        NB.isEmpty() && BED.isEmpty() && NP.isEmpty() && TY.isEmpty() && PHone.isEmpty() && !SELECTEDIMAGE.equals("No image selected")))
                {
                    if(!internetIsConnected())
                    {
                        Px=false;
                        Toast.makeText(OwnerAdd.this, "Internet Not available", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    upload();}
    if(Px) {
        urlTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    URLIMAGE = downloadUri.toString();

                    Toast.makeText(OwnerAdd.this, "Image Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                    submitdata();
                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(OwnerAdd.this, "Image Uploaded Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

                }

            }
        });

    }

    private void fetchlastLocation() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},101);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    currentLocation = location;
                    Toast.makeText(OwnerAdd.this, currentLocation.getLatitude() + " " +
                            currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                   lon= currentLocation.getLongitude();
                    lat = currentLocation.getLatitude();

                }
            }
        });

    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }


    public void submitdata()
    {

        String Name=name.getText().toString();
        String ADDRESS=add.getText().toString();
        String DISTNACE=dist.getText().toString();
        String RENTP=rp.getText().toString();
        String RENTT=rt.getText().toString();
        String HF=hf.getSelectedItem().toString();
        String NB=nb.getSelectedItem().toString();
        String BED=bed.getSelectedItem().toString();
        String NP=np.getSelectedItem().toString();
        String TY=type.getSelectedItem().toString();
        String PHone=phone.getText().toString();
        String SELECTEDIMAGE=SelectedImage.getText().toString();
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        map =new HashMap<>();


//        Map<String,String> map=new HashMap<>();
        map.put("Name",Name);
        map.put("Address",ADDRESS);
        map.put("Distance",DISTNACE);
        map.put("Rentp",RENTP);
        map.put("Rentt",RENTT);
        map.put("HostelFor",HF);
        map.put("NumberB",NB);
        map.put("NoBedrooms",BED);
        map.put("NumberP",NP);
        map.put("Type",TY);
        map.put("Phone",PHone);
        map.put("Url",URLIMAGE);
        map.put("Latitude",Double.toString(lat));
        map.put("Longitude",Double.toString(lon));



        db.collection("hostellist").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(OwnerAdd.this, "Hostel Added Succesfully", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(OwnerAdd.this,Owner.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OwnerAdd.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                map.clear();
            }
        });
    }
    public void showfileChoser()
    {
        Intent fileimage=new Intent();
        fileimage.setType("image/*");
        fileimage.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(fileimage,"Select an image"),rrq);
    }
}
