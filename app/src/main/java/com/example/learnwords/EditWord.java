package com.example.learnwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditWord extends AppCompatActivity {
    TextView en, hy, example, pronounce, enVersion;
    DBHelper dbHelper;
    int id;
    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        id = Integer.parseInt(String.valueOf(getIntent().getSerializableExtra("id")));
        en = findViewById(R.id.editEn);
        hy = findViewById(R.id.editAr);
        example = findViewById(R.id.textArea_in);
        pronounce = findViewById(R.id.editPron);
        enVersion = findViewById(R.id.editVers);

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();
        cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + " where " + DBHelper.KEY_ID + " = ? ", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int enIndex = cursor.getColumnIndex(DBHelper.KEY_EN);
            int hyIndex = cursor.getColumnIndex(DBHelper.KEY_HY);
            int exampleIndex = cursor.getColumnIndex(DBHelper.KEY_EXAMPLE);
            int pronIndex = cursor.getColumnIndex(DBHelper.KEY_PRONOUNCE);
            int versionIndex = cursor.getColumnIndex(DBHelper.KEY_EN_VERSION);
            do {
                en.setText(cursor.getString(enIndex));
                hy.setText(cursor.getString(hyIndex));
                example.setText(cursor.getString(exampleIndex));
                pronounce.setText(cursor.getString(pronIndex));
                enVersion.setText(cursor.getString(versionIndex));
            } while (cursor.moveToNext());
        } else
            cursor.close();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditWord.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri address = Uri.parse("https://translate.google.ru/#view=home&op=translate&sl=en&tl=hy");
                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openLinkIntent);
            }
        });
    }

    public void editWord(View view) {
        contentValues.put(DBHelper.KEY_EN, en.getText().toString());
        contentValues.put(DBHelper.KEY_HY, hy.getText().toString());
        contentValues.put(DBHelper.KEY_EXAMPLE, example.getText().toString());
        contentValues.put(DBHelper.KEY_PRONOUNCE, pronounce.getText().toString());
        contentValues.put(DBHelper.KEY_EN_VERSION, enVersion.getText().toString());
        database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[]{String.valueOf(id)});
        dbHelper.close();
        Intent intent = new Intent(EditWord.this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteWord(View view) {
        database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);
        dbHelper.close();
        Intent intent = new Intent(EditWord.this, MainActivity.class);
        startActivity(intent);
    }
}