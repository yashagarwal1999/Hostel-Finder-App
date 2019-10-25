package com.example.profile2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Mainpage extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdaptor ra;
    FirebaseFirestore fb;
    List<Entry> l;
    ArrayAdapter<String> Aadapter;
    ArrayList<String> ListOFHostels, flist;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hostelfinder, menu);
        ra.copy();
        MenuItem search=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView= (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchResult(query);
                // ra.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                               // ra.getFilter().filter(newText);
                if(newText.equals("")){
                    getAllResult();
                }
                return false;
            }
        });
        //SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                ra.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ra.getFilter().filter(newText);
//                return false;
//            }
//        });
//        SearchView searchView=(SearchView)search.getActionView();
//
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);


       // searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
////                ra.changenow();
////                ra.copy();
//                ra.notifyDataSetChanged();
//                return false;
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                ra.getFilter().filter(s);
//                ra.changenow();
//                return false;
//            }
//        });
//
////        ra.copy();
////        ra.notifyDataSetChanged();
//
//        return true;
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Signout: {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent i = new Intent(Mainpage.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.SRA: {
                sortRent();
                break;
            }
            case R.id.SDA: {

                sortdist();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void sortdist(){
        for(int i=0;i<l.size();i++)
        {
            l.get(i).setR();
        }
        Collections.sort(l);
        ra.notifyDataSetChanged();
}

    private void sortRent() {

//        Collections.sort(l, new Comparator<Entry>() {
//            @Override
//            public int compare(Entry lhs, Entry rhs) {
//                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
//                Integer a=Integer.parseInt(lhs.getRp());Integer b=Integer.parseInt(rhs.getRp());
//                return  a>b? 0:1;
//            }
//        });

        for(int i=0;i<l.size();i++) {
         //   Log.d("L[0]", l.get(i).getRp());
        l.get(i).unsetR();
        }
        Collections.sort(l);


        ra.notifyDataSetChanged();
    }


    void getSearchResult(String str) {


        List<Entry> finallist = new ArrayList<>();
        str=str.toLowerCase();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getName().toLowerCase().contains(str)) {
              //  Log.e("Name",l.get(i).getName() );
                finallist.add(l.get(i));
            }


        }
        l.clear();
      //  Log.e("count", String.valueOf(finallist.size()));
        l.addAll(finallist);
        ra.notifyDataSetChanged();


    }

    void getAllResult(){
        flist = new ArrayList<>();
        l.clear();
        fb.collection("hostellist")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> li;
                        li=queryDocumentSnapshots.getDocuments();
                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            String n="",p="",r="",d="";
                            String add,hf,nb,np,rp,rt,type,bed;
                            for(DocumentSnapshot i:li)

                            {
                                if(i.exists())
                                {
                                    n= (String) i.get("Name");
                                    ListOFHostels.add(n);
                                    p=(String) i.get("Phone");
                                    r=(String) i.getString("Rentp");
                                    d=(String) i.getString("Distance");
                                    add=(String)i.get("Address");
                                    hf=(String)i.get("HostelFor");
                                    nb=(String)i.get("NumberB");
                                    np=(String)i.get("NumberP");
                                    rp=(String)i.get("Rentp");
                                    rt=(String)i.get("Rentt");
                                    type=(String)i.get("Type");
                                    bed=(String)i.get("NoBedrooms");
                                    String uu=(String)i.get("Url");
                                    String lat=(String)i.get("Latitude");
                                    String lon=(String)i.get("Longitute");
                                    Entry entry=new Entry(n,p,r,d,R.drawable.ic_house,add,hf,nb,np,rp,rt,type,bed,uu,lat,lon);
                                    l.add(entry);
                                    recyclerView.setAdapter(ra);
                                    ra.dataChange(Mainpage.this,l);
//                        ra.notifyDataSetChanged();
                                }
                                else
                                    break;

                            }

                        }
//Aadapter=new ArrayAdapter<>(Mainpage.this,android.R.layout.simple_list_item_1,ListOFHostels);
//                recyclerView.setAdapter(Aadapter);
                    }
                });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        fb=FirebaseFirestore.getInstance();
        ListOFHostels=new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        l=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ra=new RecyclerViewAdaptor(Mainpage.this,l);
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(true)
//                .build();
//        fb.setFirestoreSettings(settings);
        fb.collection("hostellist")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            List<DocumentSnapshot> li;
                li=queryDocumentSnapshots.getDocuments();
                if(!queryDocumentSnapshots.isEmpty())
            {
                String n="",p="",r="",d="";
                String add,hf,nb,np,rp,rt,type,bed;
                for(DocumentSnapshot i:li)
//                for(int i=0;i<li.size();i++)
                {
                    if(i.exists())
                    {
                       n= (String) i.get("Name");
                       ListOFHostels.add(n);
                       p=(String) i.get("Phone");
                       r=(String) i.getString("Rentp");
                       d=(String) i.getString("Distance");
                       add=(String)i.get("Address");
                       hf=(String)i.get("HostelFor");
                       nb=(String)i.get("NumberB");
                       np=(String)i.get("NumberP");
                       rp=(String)i.get("Rentp");
                       rt=(String)i.get("Rentt");
                       type=(String)i.get("Type");
                       bed=(String)i.get("NoBedrooms");
                       String uu=(String)i.get("Url");
                       String lat=(String)i.get("Latitude");
                        String lon=(String)i.get("Longitute");
                        Entry entry=new Entry(n,p,r,d,R.drawable.ic_house,add,hf,nb,np,rp,rt,type,bed,uu,lat,lon);
                        l.add(entry);
                        recyclerView.setAdapter(ra);
                        ra.dataChange(Mainpage.this,l);
//                        ra.notifyDataSetChanged();
                    }
                    else
                        break;

                }
                ra.copy();
            }
//Aadapter=new ArrayAdapter<>(Mainpage.this,android.R.layout.simple_list_item_1,ListOFHostels);
//                recyclerView.setAdapter(Aadapter);
            }
        });
    }



}
