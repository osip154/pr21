package com.abdulaziz.pr21_mirzakamilov_pr23103;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        userList = findViewById(R.id.list);

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        userCursor = db.rawQuery("select * from " +
                DatabaseHelper.TABLE, null);

        String[] headers = new String[]{
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_YEAR
        };

        userAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                userCursor,
                headers,
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        userList.setAdapter(userAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                Intent intent = new Intent(getApplicationContext(),
                        UserActivity.class);

                intent.putExtra("id", id);

                startActivity(intent);
            }
        });

        addButton.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(),
                    UserActivity.class);

            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        userCursor.close();
    }
}