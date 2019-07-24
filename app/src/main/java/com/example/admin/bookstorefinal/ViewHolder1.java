package com.example.admin.bookstorefinal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder1 extends RecyclerView.ViewHolder {
    View mView;
    public  ViewHolder1(View itemView){
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClicklistner.onItemClick(view, getAdapterPosition());
            }
        });
        //long click

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClicklistner.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });
    }
    // set details to recyclerview
    public  void  setDetails1(Context ctx, String title, String description, String image, String isbn, String price,String catagory){
// views

        TextView mTitleView = mView.findViewById(R.id.rtitletv);
        TextView mDescription = mView.findViewById(R.id.rdescriptiontv);
        TextView mIsbn = mView.findViewById(R.id.risbntv);
        TextView mPrice = mView.findViewById(R.id.rpricetv);
        ImageView mImage = mView.findViewById(R.id.rimagetv);
        TextView mCatagory = mView.findViewById(R.id.rcatagorytv);

        //set data to view
        mTitleView.setText(title);
        mDescription.setText(description);
        mIsbn.setText(isbn);
        mPrice.setText(price);
        Picasso.get().load(image).into(mImage);
        mCatagory.setText(catagory);
    }

//    public void  getdetails(String price){
//        TextView mPrice = mView.findViewById(R.id.rpricetv);
//        mPrice.setText(price);
//    }


    private  ViewHolder.ClickListner mClicklistner;

    // Adding interface for other activity to get the data we pulled from adatabase

    public interface  ClickListner{
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public  void setOnClickListner(ViewHolder.ClickListner clickListner){
        mClicklistner = clickListner;
    }
}
