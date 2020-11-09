package com.example.learnwords.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.learnwords.R;

import java.util.ArrayList;
import java.util.Locale;

public class ListShowAdapter extends BaseAdapter implements SectionIndexer {
    Context context;
    ArrayList<String> ID;
    ArrayList<String> EN;
    ArrayList<String> HY;
    ArrayList<String> PRON;
    ArrayList<String> EXAMPLE;

    TextToSpeech mTTS;
    public ListShowAdapter(Context context, ArrayList<String> ID, ArrayList<String> EN, ArrayList<String> HY, ArrayList<String> PRON, ArrayList<String> EXAMPLE) {
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
            child = layoutInflater.inflate(R.layout.item_show, null);
            word = new Word();
            word.id_TextView = (TextView) child.findViewById(R.id.index);
            word.en_TextView = (TextView) child.findViewById(R.id.showEn);
            word.hy_TextView = (TextView) child.findViewById(R.id.showHy);
            word.pron_TextView = (TextView) child.findViewById(R.id.showPron);
            word.example_TextView = (TextView) child.findViewById(R.id.showEx);
            word.plus = (ImageButton) child.findViewById(R.id.plus);
            word.minus = (ImageButton) child.findViewById(R.id.minus);
            word.speakEn = (ImageButton) child.findViewById(R.id.speakEnShow);

            word.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    word.en_TextView.setBackgroundResource(R.drawable.back_plus);
                    word.hy_TextView.setBackgroundResource(R.drawable.back_plus);
                    word.hy_TextView.setTextSize(30);
                }
            });

            word.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    word.en_TextView.setBackgroundResource(R.drawable.back_minus);
                    word.hy_TextView.setBackgroundResource(R.drawable.back_minus);
                    word.hy_TextView.setTextSize(30);
                }
            });
            mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = mTTS.setLanguage(Locale.ENGLISH);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        } else {
                            word.speakEn.setEnabled(true);
                        }
                    }
                }
            });
            word.speakEn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTTS.setPitch(1f);
                    mTTS.setSpeechRate(1f);
                    mTTS.speak(EN.get(position), TextToSpeech.QUEUE_FLUSH, null);
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
        ImageButton speakEn;
        ImageButton plus;
        ImageButton minus;
    }
}
