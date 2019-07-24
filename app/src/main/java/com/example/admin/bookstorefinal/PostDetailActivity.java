package com.example.admin.bookstorefinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    TextView mTitleTv , mDetailTv, mPriceTv, mIsbnTv,mpasssed;
    ImageView mImagetv;
    Button mbuttondatabaseaddtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
   // Action bar hehe we need
        firebaseAuth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail View");

        // lets set back button

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mTitleTv = findViewById(R.id.rtitletvpost);
        mDetailTv= findViewById(R.id.rdescriptiontvpost);
        mPriceTv = findViewById(R.id.rpricetvpost);
        mIsbnTv = findViewById(R.id.risbntvpost);
        mImagetv = findViewById(R.id.rimagetvpost);
        mbuttondatabaseaddtv = findViewById(R.id.addtocartbtnpost);
        mpasssed = findViewById(R.id.rcatagorytvpost);


        byte[] bytes = getIntent().getByteArrayExtra("image");
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String isbn = getIntent().getStringExtra("isbn");
        String price = getIntent().getStringExtra("price");
        String catagoryvalue = getIntent().getStringExtra("catagory");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        //set data to view
       mpasssed.setText(catagoryvalue);
        mTitleTv.setText(title);
        mDetailTv.setText(description);
        mIsbnTv.setText(isbn);
        mPriceTv.setText(price);
        mbuttondatabaseaddtv.setText(title);
        mImagetv.setImageBitmap(bmp);


        // now can we upload to database using button click

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(catagoryvalue);

        final   DatabaseReference toPath = FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(mbuttondatabaseaddtv.getText().toString());

        // start
        mbuttondatabaseaddtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(mbuttondatabaseaddtv.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            //.........................

                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isComplete()) {

                                                //  Toast.makeText(TakeAttendence.this,"Attendence Accepted",Toast.LENGTH_SHORT).show();

                                            } else {

                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            };
                            ref.child(mbuttondatabaseaddtv.getText().toString()).addListenerForSingleValueEvent(valueEventListener);

                            //..................................................................


                            Toast.makeText(PostDetailActivity.this,"Added to cart",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(PostDetailActivity.this,"Invalid cant add",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        //end
    }

    @Override
    public  boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }



    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(PostDetailActivity.this,LoginActivity.class));
        Toast.makeText(PostDetailActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }
    private  void cart(){

        startActivity(new Intent(PostDetailActivity.this,CartActivity.class));
        Toast.makeText(PostDetailActivity.this,"View Cart", Toast.LENGTH_SHORT).show();
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
        if(id== R.id.cartMenu){
            cart();
        }
        return super.onOptionsItemSelected(item);
    }
}
