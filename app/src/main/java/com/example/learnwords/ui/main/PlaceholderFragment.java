package com.example.learnwords.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.learnwords.DBHelper;
import com.example.learnwords.R;
import com.example.learnwords.ShuffleActivity;
import com.example.learnwords.adapter.ListAdapter;
import com.example.learnwords.adapter.ListShowAdapter;
import com.example.learnwords.adapter.ListShuffleShowAdapter;
import com.example.learnwords.adapter.ListSwitchAdapter;

import java.util.ArrayList;
import java.util.Random;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    ListView listView;
    ListAdapter listAdapter;
    ListShowAdapter listShowAdapter;
    ListSwitchAdapter listSwitchAdapter;
    ListShuffleShowAdapter listShuffleShowAdapter;

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

    LinearLayout linearLayout;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        linearLayout = root.findViewById(R.id.linerLayout);
        listView = root.findViewById(R.id.lists);

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

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switch (s) {
                    case "1":
                        dbHelper = new DBHelper(getContext());
                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + "", null);
                        ID_Array.clear();
                        EN_Array.clear();
                        HY_Array.clear();
                        EXAMPLE_Array.clear();
                        EN_VERSION_Array.clear();
                        if (cursor.moveToFirst()) {
                            do {
                                ID_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID)));
                                EN_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN)));
                                HY_Array.add("( " + cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HY)) + " )");
                                EXAMPLE_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EXAMPLE)));
                                EN_VERSION_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN_VERSION)));
                            } while (cursor.moveToNext());
                            Random r = new Random();
                            ArrayList<Integer> arr = new ArrayList<Integer>();
                            for (int i = 0; i < EN_Array.size(); i++) {
                                int x1 = r.nextInt(EN_Array.size());
                                if (arr.contains(x1)) {
                                    i--;
                                } else {
                                    arr.add(x1);
                                    ID_Array_random.add(ID_Array.get(x1));
                                    EN_Array_random.add(EN_Array.get(x1));
                                    HY_Array_random.add(HY_Array.get(x1));
                                    EXAMPLE_Array_random.add(EXAMPLE_Array.get(x1));
                                    EN_VERSION_Array_random.add(EN_VERSION_Array.get(x1));
                                }
                            }
                        }
                        final TextView textView = new TextView(getContext());
                        textView.setText("currently we have ( " + EN_Array.size() + " word )");
                        textView.setTextColor(Color.rgb(255, 255, 255));
                        linearLayout.addView(textView);
                        listAdapter = new ListAdapter(getContext(),
                                ID_Array_random,
                                EN_Array_random,
                                HY_Array_random,
                                EXAMPLE_Array_random,
                                EN_VERSION_Array_random
                        );
                        listView.setAdapter(listAdapter);
                        cursor.close();
                        dbHelper.close();
                        sqLiteDatabase.close();
                        break;
                    case "2":
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
                            ArrayList<Integer> arr = new ArrayList<Integer>();
                            for (int i = 0; i < EN_Array.size(); i++) {
                                int x1 = r.nextInt(EN_Array.size());
                                if (arr.contains(x1)) {
                                    i--;
                                } else {
                                    arr.add(x1);
                                    ID_Array_random.add(String.valueOf(i + 1));
                                    EN_Array_random.add(EN_Array.get(x1));
                                    HY_Array_random.add(HY_Array.get(x1));
                                    PRON_Array_random.add(PRON_Array.get(x1));
                                    EXAMPLE_Array_random.add(EXAMPLE_Array.get(x1));
                                }
                            }
                        }
                        TextView textView1 = new TextView(getContext());
                        textView1.setText("currently we have ( " + EN_Array.size() + " word )");
                        textView1.setTextColor(Color.rgb(255, 255, 255));
                        linearLayout.addView(textView1);
                        listShowAdapter = new ListShowAdapter(getContext(),
                                ID_Array_random,
                                EN_Array_random,
                                HY_Array_random,
                                PRON_Array_random,
                                EXAMPLE_Array_random
                        );
                        listView.setAdapter(listShowAdapter);
                        cursor.close();
                        dbHelper.close();
                        sqLiteDatabase.close();
                        break;
                    case "3":
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
                            ArrayList<Integer> arr = new ArrayList<Integer>();
                            for (int i = 0; i < EN_Array.size(); i++) {
                                int x1 = r.nextInt(EN_Array.size());
                                if (arr.contains(x1)) {
                                    i--;
                                } else {
                                    arr.add(x1);
                                    ID_Array_random.add(String.valueOf(i + 1));
                                    EN_Array_random.add(EN_Array.get(x1));
                                    HY_Array_random.add(HY_Array.get(x1));
                                    PRON_Array_random.add(PRON_Array.get(x1));
                                    EXAMPLE_Array_random.add(EXAMPLE_Array.get(x1));
                                }
                            }
                        }
                        TextView textView2 = new TextView(getContext());
                        textView2.setText("currently we have ( " + EN_Array.size() + " word )");
                        textView2.setTextColor(Color.rgb(255, 255, 255));
                        linearLayout.addView(textView2);
                        listSwitchAdapter = new ListSwitchAdapter(getContext(),
                                ID_Array_random,
                                EN_Array_random,
                                HY_Array_random,
                                PRON_Array_random,
                                EXAMPLE_Array_random
                        );
                        listView.setAdapter(listSwitchAdapter);
                        cursor.close();
                        dbHelper.close();
                        sqLiteDatabase.close();
                        break;
                    case "4":
                        LinearLayout linear1 = new LinearLayout(getContext());
                        Button btn = new Button(getContext());
                        btn.setBackgroundResource(R.drawable.back_minus);
                        btn.setText("shuffle");
                        linear1.addView(btn);
                        linearLayout.addView(linear1);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        LinearLayout linear = new LinearLayout(getContext());
                        final EditText editText = new EditText(getContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) RadioGroup.LayoutParams.MATCH_PARENT, (int) RadioGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 50;
                        params.rightMargin = 50;
                        editText.setLayoutParams(params);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setTextColor(Color.rgb(0, 0, 0));
                        linear.addView(editText);
                        builder.setTitle("Shuffle")
                                .setCancelable(false)
                                .setView(linear)
                                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Intent intent = new Intent(getContext(), ShuffleActivity.class);
                                        intent.putExtra("inp", editText.getText().toString());
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });
                        final AlertDialog alert = builder.create();
                        alert.setTitle("How many words do you want to see?");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.show();
                            }
                        });
                        dbHelper = new DBHelper(getContext());
                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + "", null);
                        ID_Array.clear();
                        EN_Array.clear();
                        HY_Array.clear();
                        if (cursor.moveToFirst()) {
                            int x = 1;
                            do {
                                ID_Array.add(String.valueOf(x++));
                                EN_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EN)));
                                HY_Array.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HY)));
                            } while (cursor.moveToNext());
                        }
                        TextView textView3 = new TextView(getContext());
                        textView3.setText("currently we have ( " + EN_Array.size() + " word )");
                        textView3.setTextColor(Color.rgb(255, 255, 255));
                        linearLayout.addView(textView3);
                        listShuffleShowAdapter = new ListShuffleShowAdapter(getContext(),
                                ID_Array,
                                EN_Array,
                                HY_Array
                        );
                        listView.setAdapter(listShuffleShowAdapter);
                        cursor.close();
                        dbHelper.close();
                        sqLiteDatabase.close();
                        break;
                    default:
                        break;
                }
            }
        });
        return root;
    }
}