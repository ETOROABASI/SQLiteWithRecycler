package com.example.android.sqlitewithrecycler.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.sqlitewithrecycler.R;
import com.example.android.sqlitewithrecycler.adapter.CustomAdapter;
import com.example.android.sqlitewithrecycler.data.DbContract;
import com.example.android.sqlitewithrecycler.data.DbHelper;

import static com.example.android.sqlitewithrecycler.R.string.fname;
import static com.example.android.sqlitewithrecycler.R.string.marks;

public class ListActivity extends AppCompatActivity implements CustomAdapter.ItemClickCallBack {
    RecyclerView recyclerView;
    CustomAdapter mAdapter;
    Cursor mCursor;
    private SQLiteDatabase mDb;
    DbHelper dbHelper;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbHelper = new DbHelper(this);
        btnHome = (Button) findViewById(R.id.btn_list_home);
        mDb = dbHelper.getWritableDatabase();
        recyclerView = (RecyclerView) this.findViewById(R.id.recView_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCursor = dbHelper.getAllData();
        mAdapter = new CustomAdapter(this, mCursor);
        backToHome();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickCallBack(this);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB
                dbHelper.deleteData(id);
                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(dbHelper.getAllData());
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onItemClick(View v, int position){
        mCursor.moveToPosition(position);   //THIS ALLOWS THE CURSOR TO FOCUS ON THE ROW OF THE ITEM CLICKED
        String id = mCursor.getString(mCursor.getColumnIndex(DbContract.DbEntryData._ID));
        String fname = mCursor.getString(mCursor.getColumnIndex(DbContract.DbEntryData.COL_FIRST_NAME));
        String lname = mCursor.getString((mCursor.getColumnIndex(DbContract.DbEntryData.COL_LAST_NAME)));
        String marks = mCursor.getString(mCursor.getColumnIndex(DbContract.DbEntryData.COL_MARKS));

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle extras = new Bundle();

        extras.putString("ID", id);
        extras.putString("FIRST_NAME", fname);
        extras.putString("LAST_NAME", lname);
        extras.putString("MARKS", marks);

        intent.putExtra("BUNDLE_EXTRAS", extras);

        startActivity(intent);

        Toast.makeText(ListActivity.this, "clicked: "+ id, Toast.LENGTH_SHORT).show();
    }


    public void backToHome(){
        btnHome.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ListActivity.this, MainActivity.class));
                    }
                }

        );
    }

}
