package com.example.cristian.appestudiante.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Cristian on 19/02/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ListaObjetos> titulos;
    private HashMap<String, List<ListaObjetos>> items;

    public ExpandableListAdapter(Context context, ArrayList<ListaObjetos> titulos, HashMap<String, List<ListaObjetos>> items) {
        this.context = context;
        this.titulos = titulos;
        this.items = items;
    }

    @Override
    public int getGroupCount() {
        return titulos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.get(titulos.get(groupPosition).getTitulo()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return titulos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(titulos.get(groupPosition).getTitulo()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        ListaObjetos t = (ListaObjetos) getGroup(groupPosition);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_grupo_expandable, null);
        }

        TextView txtTitulo = (TextView) view.findViewById(R.id.txtGroupExpandable);
        ImageView imagen = (ImageView) view.findViewById(R.id.imageGroupExpandable);
        txtTitulo.setText(t.getTitulo());
        imagen.setImageResource(t.getImagen());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ListaObjetos t = (ListaObjetos) getChild(groupPosition, childPosition);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_expandable, null);
        }

        TextView txtTitulo = (TextView) view.findViewById(R.id.txtItemExpandable);
        ImageView imagen = (ImageView) view.findViewById(R.id.imageItemExpandable);

        txtTitulo.setText(t.getTitulo());
        imagen.setImageResource(t.getImagen());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
