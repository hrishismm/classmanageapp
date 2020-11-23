package com.example.classmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Pdf_files_user extends AppCompatActivity {
ListView mypdfListView;

DatabaseReference databaseReference;

//Helper class uploadPdf
List<uploadPDF> uploadPDFs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pdf_files_user);

mypdfListView=(ListView)findViewById(R.id.myListView);

//Initialize
uploadPDFs=new ArrayList<>();

viewAllFiles();

//OnclickItemListView
mypdfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        uploadPDF uploadPDF=uploadPDFs.get(i);


        Intent intent = new Intent();
        intent.setType(Intent.ACTION_VIEW);
        intent.setPackage("com.google.android.apps.docs");

        intent.setData(Uri.parse(uploadPDF.getUrl()));
        startActivity(intent);


    }
}


);
    }

    private void viewAllFiles() {

    databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
databaseReference.addValueEventListener(new ValueEventListener() {

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {


        for (DataSnapshot postSnapshot:snapshot.getChildren())
        {

uploadPDF uploadPDF=postSnapshot.getValue(uploadPDF.class);
uploadPDFs.add(uploadPDF);

        }

String[] uploads=new String[uploadPDFs.size()];
        for(int i=0;i<uploads.length;i++)
            {
                uploads[i]=uploadPDFs.get(i).getName();
            }


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads){


            @Override
            public View getView(int position,  View convertView,  ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView myText=(TextView)view.findViewById(android.R.id.text1);
                myText.setTextColor(Color.BLACK);
                return view;
            }
        };
        mypdfListView.setAdapter(adapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




    }
}