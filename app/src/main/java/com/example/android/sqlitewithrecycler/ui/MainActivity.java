package com.example.android.sqlitewithrecycler.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.sqlitewithrecycler.R;
import com.example.android.sqlitewithrecycler.data.DbHelper;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    EditText fname, lname, marks;
    Button btnAddData, btnViewAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
        fname= (EditText) findViewById(R.id.edit_main_fname);
        lname = (EditText) findViewById(R.id.edit_main_sname);
        marks = (EditText) findViewById(R.id.edit_main_marks);
        btnAddData = (Button) findViewById(R.id.btn_main_submit);
        btnViewAll = (Button) findViewById(R.id.btn_main_viewAll);
        addData();
        viewAllData();
        }

    public void addData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (fname.getText().length() == 0 || lname.getText().length() == 0 || marks.getText().length() == 0) {
                            showMessage("Error", "No Field should be Empty");
                            return;
                        }
                        String firstName = fname.getText().toString();
                        String lastName = lname.getText().toString();
                        int mark = Integer.parseInt(marks.getText().toString());

                        boolean isInserted = dbHelper.addData(firstName,lastName, mark);
                        if(isInserted){
                            Toast.makeText(MainActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                            fname.getText().clear();
                            lname.getText().clear();
                            marks.setText("");
                            marks.clearFocus();
                            lname.clearFocus();
                        }

                        else{
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

    public void viewAllData(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
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



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
