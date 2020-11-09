package com.example.learnwords;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNewWord extends AppCompatActivity implements View.OnClickListener {

    TextView en, hy, example, pronounce, enVersion;
    DBHelper dbHelper;
    Button btn, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
        en = findViewById(R.id.editEnglish);
        hy = findViewById(R.id.editArmenian);
        example = findViewById(R.id.textArea_information);
        pronounce = findViewById(R.id.editPronounce);
        enVersion = findViewById(R.id.editVersion);
        btn = findViewById(R.id.btn);
        btnShow = findViewById(R.id.btnShow);
        btn.setOnClickListener(this);
        dbHelper = new DBHelper(this);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri address = Uri.parse("https://translate.google.ru/#view=home&op=translate&sl=en&tl=hy");
                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openLinkIntent);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewWord.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {
        String english = en.getText().toString();
        String armenia = hy.getText().toString();
        String exam = example.getText().toString();
        String pron = pronounce.getText().toString();
        String version = enVersion.getText().toString();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_EN, english);
        contentValues.put(DBHelper.KEY_HY, armenia);
        contentValues.put(DBHelper.KEY_EXAMPLE, exam);
        contentValues.put(DBHelper.KEY_PRONOUNCE, pron);
        contentValues.put(DBHelper.KEY_EN_VERSION, version);
        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
        dbHelper.close();
        en.setText("");
        hy.setText("");
        example.setText("");
        pronounce.setText("");
        enVersion.setText("");

        Intent intent = new Intent(AddNewWord.this, MainActivity.class);
        startActivity(intent);
    }

    public void clear(View view) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_CONTACTS, null, null);
        dbHelper.close();
        Intent intent = new Intent(AddNewWord.this, MainActivity.class);
        startActivity(intent);
    }
}