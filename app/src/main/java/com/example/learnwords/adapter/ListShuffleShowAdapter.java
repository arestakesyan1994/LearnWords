package com.example.learnwords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.learnwords.R;

import java.util.ArrayList;

public class ListShuffleShowAdapter extends BaseAdapter implements SectionIndexer {
    Context context;
    ArrayList<String> ID;
    ArrayList<String> EN;
    ArrayList<String> HY;

    public ListShuffleShowAdapter(Context context, ArrayList<String> ID, ArrayList<String> EN, ArrayList<String> HY) {
        this.context = context;
        this.ID = ID;
        this.EN = EN;
        this.HY = HY;
    }

    public int getCount() {
        return EN.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View child, ViewGroup parent) {
        final Word word;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.item_shuffle_show, null);
            word = new Word();
            word.id_TextView = (TextView) child.findViewById(R.id.index);
            word.en_TextView = (TextView) child.findViewById(R.id.showEn);
            word.hy_TextView = (TextView) child.findViewById(R.id.showHy);
            child.setTag(word);
        } else {

            word = (Word) child.getTag();
        }
        word.id_TextView.setText(ID.get(position));
        word.en_TextView.setText(EN.get(position));
        word.hy_TextView.setText(HY.get(position));
        return child;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int i) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    public class Word {
        TextView id_TextView;
        TextView en_TextView;
        TextView hy_TextView;
    }
}
