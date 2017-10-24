package com.example.android.sqlitewithrecycler.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sqlitewithrecycler.R;
import com.example.android.sqlitewithrecycler.data.DbContract;

import java.util.ArrayList;

import static android.R.attr.name;
import static com.example.android.sqlitewithrecycler.R.string.fname;


/**
 * Created by ETORO on 17/10/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RecyclerViewHolder> {


    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Cursor cursor;
    private Context mContext;

    private ItemClickCallBack itemClickCallBack;

    public interface ItemClickCallBack{
        void onItemClick(View v, int position);
    }

    public void setItemClickCallBack( final ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }


    public CustomAdapter (Context context, Cursor cursor){
        setmContext(context);
        setCursor(cursor);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.student_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!cursor.moveToPosition(position)) {
            return; // bail if returned null
        }
        // Update the view holder with the information needed to display

        //This gets the String value of the content in the column: COULUMN_GUEST_NAME for a particular row.
        //Same for COULUMN_PARTY_SIZE and _ID
        String fname = cursor.getString(cursor.getColumnIndex(DbContract.DbEntryData.COL_FIRST_NAME));
        String lname = cursor.getString(cursor.getColumnIndex(DbContract.DbEntryData.COL_LAST_NAME));
        int marks = cursor.getInt(cursor.getColumnIndex(DbContract.DbEntryData.COL_MARKS));
        // COMPLETED (6) Retrieve the id from the cursor and
        long id = cursor.getLong(cursor.getColumnIndex(DbContract.DbEntryData._ID));

        // Display the guest name
        holder.fname.setText(fname);
        // Display the party count
        holder.lname.setText(lname);
        holder.marks.setText(String.valueOf(marks));
        // COMPLETED (7) Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }



    public void swapCursor(Cursor newCursor) {      //this gets a new getAllList for the cursor since an item had been deleted or added, so kinda, gets the new updated list and
        // pastes it on your recyclerView, for more check your onSwipe method and addToWaitList method in mainActivity
        // Always close the previous mCursor first
        if (cursor != null) cursor.close();
        cursor = newCursor;

        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }



    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView fname;
        TextView lname;
        TextView marks;
        View linear_container;

        public RecyclerViewHolder(View itemView){
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.textview_list_fname);
            lname = (TextView) itemView.findViewById(R.id.textview_list_lname);
            marks = (TextView) itemView.findViewById(R.id.textview_list_marks);
            linear_container = itemView.findViewById(R.id.linear_list_view);
            linear_container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.linear_list_view){

                itemClickCallBack.onItemClick(v, getAdapterPosition());


            }

        }

    }
}
