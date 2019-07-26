package com.example.bmr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.* ;



public class listview_adapter extends BaseAdapter {

    private Context context ;
    private String[] show_name ;
    private LayoutInflater inflater ;


    static class ViewHolder {
        LinearLayout border ;
        TextView Name ;
    }

    //ViewAdapter
    public listview_adapter(String[] data , LayoutInflater inflater) {
        this.show_name = data ;
        this.inflater = inflater ;
    }

    @Override
    public int getCount() {
        return show_name.length ;
    }

    @Override
    public Object getItem(int position) {
        return show_name[position] ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        ViewHolder holder ;
        if ( convertView == null ) {
            holder = new ViewHolder() ;
            convertView = inflater.inflate(R.layout.listview_itemstyle,null) ;
            holder.Name = (TextView) convertView.findViewById(R.id.show_name) ;
            holder.border = (LinearLayout) convertView.findViewById(R.id.border) ;
            convertView.setTag(holder) ;
        }else {
            holder = (ViewHolder) convertView.getTag() ;
        }

        holder.Name.setText(show_name[position]) ;

        return convertView ;
    }

}