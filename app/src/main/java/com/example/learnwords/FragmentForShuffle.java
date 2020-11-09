package com.example.learnwords;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.learnwords.adapter.ListShuffleAdapter;

import java.util.ArrayList;
import java.util.Random;

public class FragmentForShuffle extends Fragment {
    Button btnShow;
    int id;
    ListView listView;
    ListShuffleAdapter listShuffleAdapter;

    ArrayList<String> ID_Array;
    ArrayList<String> EN_Array;
    ArrayList<String> HY_Array;
    ArrayList<String> EXAMPLE_Array;
    ArrayList<String> PRON_Array;
    ArrayList<String> EN_VERSION_Array;

    ArrayList<String> ID_Array_random;
    ArrayList<String> EN_Array_random;
    ArrayList<String> HY_Array_random;
    ArrayList<String> EXAMPLE_Array_random;
    ArrayList<String> PRON_Array_random;
    ArrayList<String> EN_VERSION_Array_random;

    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    DBHelper dbHelper;

    public static FragmentForShuffle newInstance(String param1, String param2) {
        FragmentForShuffle fragment = new FragmentForShuffle();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_for_shuffle, container, false);

        String str = String.valueOf(getActivity().getIntent().getSerializableExtra("inp"));
        listView = root.findViewById(R.id.listsS);

        ID_Array = new ArrayList<String>();
        EN_Array = new ArrayList<String>();
        HY_Array = new ArrayList<String>();
        PRON_Array = new ArrayList<String>();
        EXAMPLE_Array = new ArrayList<String>();
        EN_VERSION_Array = new ArrayList<String>();

        ID_Array_random = new ArrayList<String>();
        EN_Array_random = new ArrayList<String>();
        HY_Array_random = new ArrayList<String>();
        EXAMPLE_Array_random = new ArrayList<String>();
        EN_VERSION_Array_random = new ArrayList<String>();
        PRON_Array_random = new ArrayList<String>();
        if (!str.equals("")) {
            id = Integer.parseInt(str);
            dbHelper = new DBHelper(getContext());
            sqLiteDatabase = dbHelper.getWritableDatabase();

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + "", null);
            ID_Array.clear();
            EN_Array.clear();
            HY_Array.clear();
            PRON_Array.clear();
            EXAMPLE_Array.clear();
            if (cursor.moveToFirst()) {
                int x = 1;
                do {
                    ID_Array.add(String.valueOf(x++));
                    EN_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN)));
                    HY_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HY)));
                    PRON_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PRONOUNCE)));
                    EXAMPLE_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EXAMPLE)));
                } while (cursor.moveToNext());
                Random r = new Random();
                for (int i = 0; i < id; i++) {
                    int x1 = r.nextInt(EN_Array.size());
                    ID_Array_random.add(String.valueOf(i + 1));
                    EN_Array_random.add(EN_Array.get(x1));
                    HY_Array_random.add(HY_Array.get(x1));
                    PRON_Array_random.add(PRON_Array.get(x1));
                    EXAMPLE_Array_random.add(EXAMPLE_Array.get(x1));
                }
            }
            TextView textView1 = root.findViewById(R.id.textView);
            textView1.setText("currently we have ( " + id + " word )");
            textView1.setTextColor(Color.rgb(255, 255, 255));
            listShuffleAdapter = new ListShuffleAdapter(getContext(),
                    ID_Array_random,
                    EN_Array_random,
                    HY_Array_random,
                    PRON_Array_random,
                    EXAMPLE_Array_random
            );
            listView.setAdapter(listShuffleAdapter);
            cursor.close();
            dbHelper.close();
            sqLiteDatabase.close();

        }
return root;
    }
}