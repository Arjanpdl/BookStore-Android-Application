package com.example.admin.bookstorefinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class CartActivity extends AppCompatActivity {
    private TextView btnvalue;
    private FirebaseAuth firebaseAuth;
    public Button addtocartbtnvalue;
        float price= 0;
        float totalprice= 0;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebasedatabase;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        btnvalue = findViewById(R.id.passedclassname);
        // String btnvalues= btnvalue.getText().toString();
//        Intent classintent = getIntent();
//        String classnamepassed = classintent.getStringExtra("Catagoryname");
//        btnvalue.setText(classnamepassed);




        // starting firebase recycleview
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Posts List");

        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
// send query to firebaase

        mFirebasedatabase = FirebaseDatabase.getInstance();
        mRef = mFirebasedatabase.getReference("Cart");


    }

    //load data recycler view onstart

    @Override
    protected  void  onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Model,ViewHolder1> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder1>(
                        Model.class,
                        R.layout.row1,
                        ViewHolder1.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder1 viewHolder, Model model, int position) {

                        viewHolder.setDetails1(getApplicationContext(),model.getTitle(),
                                model.getDescription(),model.getIsbn(),model.getPrice(),model.getImage(),model.getCatagory());

//                        TextView mtotal = findViewById(R.id.rpricetv);
//                        String mtotalfinal = mtotal.getText().toString();
//                        TextView showtotal = findViewById(R.id.cartshow);
//
//                       price= new Float(mtotalfinal);
//                       totalprice = totalprice+price;
//                        String str = String.valueOf(totalprice);
//
//                       showtotal.setText(str);


                    }

                    @Override
                    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder1 viewHolder = super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView valuetodelete = findViewById(R.id.rtitletv);
                                final String mvaluetodelete = valuetodelete.getText().toString();
                                mRef.child(mvaluetodelete).removeValue();
                                Toast.makeText(CartActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();
//                                TextView valuetoadd = findViewById(R.id.rpricetv);
//                                String mvaluetoadd = valuetoadd.getText().toString();
//                                for (int i= 0;i<=position;i++){
//                                    int total;
//                                    total =+ mvaluetoadd;
//                                }


                            }

                            @Override
                            public void onItemLongClick(View view, int position){
                                // on long click

                               // Toast.makeText(CartActivity.this,"Long click"+position,Toast.LENGTH_SHORT).show();
                            }
                        });

                        return viewHolder;
                    }
                };

        // set adapter recyclerview

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

//    public  void totalincart(){
//
//    }
    public void clear(View view)
    {
        mRef.removeValue();
    }




    public void checkouts(View view){
        startActivity(new Intent(CartActivity.this,CheckoutActivity.class));

    }

    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(CartActivity.this,LoginActivity.class));
        Toast.makeText(CartActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id== R.id.logoutMenu){
            Logout();
        }

        return super.onOptionsItemSelected(item);
    }
}
