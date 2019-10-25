package com.example.profile2;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {
    ArrayList<Entry> filterList;
    RecyclerViewAdaptor adaptor;

    public CustomFilter(List<Entry> filterList, RecyclerViewAdaptor recyclerViewAdaptor) {
        this.filterList= (ArrayList<Entry>) filterList;
        this.adaptor=recyclerViewAdaptor;
    }


    public ArrayList<Entry> getFilterList() {
        return filterList;
    }

    public void setFilterList(ArrayList<Entry> filterList) {
        this.filterList = filterList;
    }

    public RecyclerViewAdaptor getAdaptor() {
        return adaptor;
    }

    public void setAdaptor(RecyclerViewAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results=new FilterResults();
        if(charSequence!=null && charSequence.length()>0)
        {
            charSequence=charSequence.toString().toUpperCase();
            ArrayList<Entry> filterModels=new ArrayList<>();
            for(int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getName().toUpperCase().contains(charSequence))
                {
                    filterModels.add(filterList.get(i));


                }
            }
            results.count=filterModels.size();
            results.values=filterModels;

        }
        else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

            return null;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        if(filterResults!=null) {
            adaptor.entryList = (ArrayList<Entry>) filterResults.values;
            adaptor.notifyDataSetChanged();
        }
    }
}
