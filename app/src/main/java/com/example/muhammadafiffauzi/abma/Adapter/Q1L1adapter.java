package com.example.muhammadafiffauzi.abma.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.R;

import java.util.List;

public class Q1L1adapter extends ArrayAdapter<Question1Model> {
    private Activity context;
    private List<Question1Model> question1Models;

    public Q1L1adapter(Activity context, List<Question1Model> question1Models) {
        super(context, R.layout.histori_list_layout);
        this.context = context;
        this.question1Models = question1Models;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.histori_list_layout, null, true);
        TextView shis = (TextView) listViewItem.findViewById(R.id.scoreHis);
        TextView dhis = (TextView) listViewItem.findViewById(R.id.dateHis);

        Question1Model question1Model = question1Models.get(position);

        shis.setText(question1Model.getQuest1Score());
        dhis.setText(question1Model.getDate());

        return listViewItem;

    }
}
