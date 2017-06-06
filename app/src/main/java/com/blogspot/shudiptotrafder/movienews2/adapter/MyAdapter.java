package com.blogspot.shudiptotrafder.movienews2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blogspot.shudiptotrafder.movienews2.MainActivity;
import com.blogspot.shudiptotrafder.movienews2.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final ItemClickListener itemClickListener;
    private Cursor mCursor;

    private final Context context;

    public MyAdapter(ItemClickListener itemClickListener, Context context) {
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    public void swapCursor(Cursor cursor){
        mCursor = cursor;

        if (mCursor != null){
            this.notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        mCursor.moveToPosition(position);

        String poster_path = mCursor.getString(MainActivity.MAIN_POSTER_PATH_INDEX);

        String baseUrl = "http://image.tmdb.org/t/p/w185/"+poster_path;

        Picasso.with(context).load(baseUrl).fit().into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        if (mCursor==null){
            return 0;
        }

        return mCursor.getCount();
    }

    //interface
    public interface ItemClickListener{
        void onItemClickListener(int id);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.mainListImageView);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCursor.moveToPosition(getAdapterPosition());

            int id = mCursor.getInt(MainActivity.MAIN_ID_INDEX);

            itemClickListener.onItemClickListener(id);
        }
    }
}
