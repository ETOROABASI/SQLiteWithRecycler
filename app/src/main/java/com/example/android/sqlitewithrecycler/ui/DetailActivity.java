package com.example.android.sqlitewithrecycler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.sqlitewithrecycler.R;
import com.example.android.sqlitewithrecycler.data.DbHelper;

import static com.example.android.sqlitewithrecycler.R.string.fname;
import static com.example.android.sqlitewithrecycler.R.string.marks;

public class DetailActivity extends AppCompatActivity {
    long id;
    //long realId;
    EditText editFname, editLname, editMarks;
    Button btnHome, btnUpdate;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getBundleExtra("BUNDLE_EXTRAS");
        editFname = (EditText) findViewById(R.id.edit_detail_fname);
        editLname = (EditText) findViewById(R.id.edit_detail_sname);
        editMarks = (EditText) findViewById(R.id.edit_detail_marks);
        btnHome = (Button) findViewById(R.id.btn_detail_home);
        btnUpdate= (Button) findViewById(R.id.btn_detail_update);
        dbHelper = new DbHelper(getApplicationContext());   // SAME AS DbHelper(this);
        editFname.setText(extras.getString("FIRST_NAME"));
        editLname.setText(extras.getString("LAST_NAME"));
        editMarks.setText(extras.getString("MARKS"));
        id = Integer.parseInt(extras.getString("ID"));
       // realId = Integer.parseInt(id);
        returnToHome();
        updateData();



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void returnToHome(){
        btnHome.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String fname = editFname.getText().toString();
                        String lname = editLname.getText().toString();
                        String stringMarks = (editMarks.getText().toString());
                        if(fname.length()== 0 || lname.length()==0 || stringMarks.length() == 0){
                            showMessage("Error", "No Field Should Be Empty");
                            return;
                        }

                        int marks = Integer.parseInt(stringMarks);
                        Boolean isUpdated = dbHelper.updateData(fname, lname, marks, id);
                        if(isUpdated){
                            Toast.makeText(DetailActivity.this, "Data is Updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(DetailActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                        }
                        editMarks.clearFocus();
                        editLname.clearFocus();
                        editFname.getText().clear();
                        editLname.getText().clear();
                        editMarks.getText().clear();
                    }
                }
        );

    }


    public void showMessage(String title, String message){
        //ALERTS FOR ERROR
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);            //TO CANCEL IT AFTER IT HAS BUILT
        builder.setTitle(title);                   //SETS THE TITLE OF THE DIALOG BOX
        builder.setMessage(message);                //SETS THE MESSAGE INSIDE THE DIALOG BOX
        builder.show();                             //TO SHOW THE DIALOG BOX

    }

}
