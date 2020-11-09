package com.example.learnwords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.learnwords.R;

import java.util.ArrayList;

public class ListSwitchAdapter extends BaseAdapter implements SectionIndexer {
    Context context;
    ArrayList<String> ID;
    ArrayList<String> EN;
    ArrayList<String> HY;
    ArrayList<String> PRON;
    ArrayList<String> EXAMPLE;

    public ListSwitchAdapter(Context context, ArrayList<String> ID, ArrayList<String> EN, ArrayList<String> HY, ArrayList<String> PRON, ArrayList<String> EXAMPLE) {
        this.context = context;
        this.ID = ID;
        this.EN = EN;
        this.HY = HY;
        this.PRON = PRON;
        this.EXAMPLE = EXAMPLE;
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
            child = layoutInflater.inflate(R.layout.item_switch, null);
            word = new Word();
            word.id_TextView = (TextView) child.findViewById(R.id.indexSwitch);
            word.en_TextView = (TextView) child.findViewById(R.id.switchEn);
            word.hy_TextView = (TextView) child.findViewById(R.id.switchHy);
            word.pron_TextView = (TextView) child.findViewById(R.id.switchPron);
            word.example_TextView = (TextView) child.findViewById(R.id.switchEx);
            word.plus = (ImageButton) child.findViewById(R.id.plusSwitch);
            word.minus = (ImageButton) child.findViewById(R.id.minusSwitch);

            word.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    word.en_TextView.setBackgroundResource(R.drawable.back_plus);
                    word.en_TextView.setTextSize(30);
                    word.hy_TextView.setTextSize(0);
                    word.pron_TextView.setTextSize(18);
                    word.example_TextView.setTextSize(18);
                }
            });

            word.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    word.en_TextView.setBackgroundResource(R.drawable.back_minus);
                    word.en_TextView.setTextSize(30);
                    word.hy_TextView.setTextSize(0);
                    word.pron_TextView.setTextSize(18);
                    word.example_TextView.setTextSize(18);
                }
            });
            child.setTag(word);
        } else {

            word = (Word) child.getTag();
        }
        word.id_TextView.setText(ID.get(position));
        word.en_TextView.setText(EN.get(position));
        word.hy_TextView.setText(HY.get(position));
        word.pron_TextView.setText(PRON.get(position));
        word.example_TextView.setText(EXAMPLE.get(position));
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
        TextView pron_TextView;
        TextView hy_TextView;
        TextView example_TextView;
        ImageButton plus;
        ImageButton minus;
    }
}
