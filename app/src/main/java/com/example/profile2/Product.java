package com.example.profile2;

public class Product {
    String add,dist,hf,name,nb,np,phone,rp,rt,type,bed;

    public Product(String a,String b,String c,String d,String e,String f,String g,String h,String i,String j)
    {
        this.add=a;
        this.dist=b;
        this.bed=j;
        this.hf=c;
        this.name=d;
        this.nb=e;
        this.np=f;
        this.rp=g;
        this.rt=h;
        this.type=i;

    }
    public String getAdd() {
        return add;
    }

    public String getDist() {
        return dist;
    }

    public String getHf() {
        return hf;
    }

    public String getName() {
        return name;
    }

    public String getNb() {
        return nb;
    }

    public String getNp() {
        return np;
    }

    public String getPhone() {
        return phone;
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
}
