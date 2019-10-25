package com.example.profile2;

import java.io.Serializable;

public class Entry implements Serializable,Comparable<Entry> {
 private String name,phone;
 String rent;
String add,hf,nb,np,rp,rt,type,bed,dist;
 String distance,url,lat,lon;
    private int image;
 int R=0,A=0;
    public Entry(String n,String p,String r,String d,int i,String a,String h,String NB,String NP,String RP,String RT,String Ty,String b,String u,String la,String lo) {
        this.name=n;
        this.phone="Phone: "+p;
                     this.rent="Rent: "+r;
                     this.distance="Distance: "+ d;
                     this.image=i;
                     this.dist=d;
                     this.add=a;
                     this.hf=h;
                     this.nb=NB;
                     this.np=NP;
                     this.rp=RP;
                     this.rt=RT;
                     this.type=Ty;
                     this.bed=b;
                        this.url=u;
                        this.lat=la;
                        this.lon=lo;



    }

public void setUrl(String u)
{
    this.url=u;
}
public String getUrl()
{
    return url;
}


public String getLat() {
        return lat;
    }
    public String getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRent() {
        return rent;
    }

    public String getAdd() {
        return add;
    }

    public String getHf() {
        return hf;
    }

    public String getNb() {
        return nb;
    }

    public String getNp() {
        return np;
    }

    public String getRp() {
        return rp;
    }

    public String getRt() {
        return rt;
    }

    public String getType() {
        return type;
    }

    public String getBed() {
        return bed;
    }

    public String getDistance() {
        return distance;
    }
    public String getdist() {
        return dist;
    }

    public int getImage() {
        return image;
    }


    @Override
    public int compareTo(Entry entry) {
        Double a;
        Double b;
        if(R==0) {
            a = Double.valueOf(entry.getRp());

            b = Double.valueOf(this.getRp());
        }
        else
        {
            a = Double.valueOf(entry.getdist());

            b = Double.valueOf(this.getdist());
        }
        if(a<b)return 1;
        else if (a>b)return -1;
        return 0;
    }

    public void setR() {
        R=1;
    }

    public void unsetR() {
        R=0;
    }
}
