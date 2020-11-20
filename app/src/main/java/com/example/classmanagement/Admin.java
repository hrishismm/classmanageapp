package com.example.classmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    Button testUpdate;
    Button time_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        testUpdate = (Button) findViewById(R.id.testUpdateButton);
        time_table= (Button) findViewById(R.id.time_table);
        time_table.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),time_table.class);
                startActivity(intent);
            }
        });
        testUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),TestUpdate.class);
                startActivity(intent);
            }
        });
    }
}