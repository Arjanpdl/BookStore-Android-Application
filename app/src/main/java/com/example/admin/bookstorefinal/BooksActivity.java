package com.example.admin.bookstorefinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class BooksActivity extends AppCompatActivity {
    private TextView btnvalue;
    private FirebaseAuth firebaseAuth;
    public Button addtocartbtnvalue;
    private  ViewHolder mAdapter;

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebasedatabase;
    DatabaseReference mRef;
    DatabaseReference mReffchild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        firebaseAuth = FirebaseAuth.getInstance();
       btnvalue = findViewById(R.id.passedclassname);
       // String btnvalues= btnvalue.getText().toString();
        Intent classintent = getIntent();
        String classnamepassed = classintent.getStringExtra("Catagoryname");
        btnvalue.setText(classnamepassed);




        //addtocartbtnvalue = findViewById(R.id.addtocartbtn);



        // starting firebase recycleview
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Posts List");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
// send query to firebaase

        mFirebasedatabase = FirebaseDatabase.getInstance();
        mRef = mFirebasedatabase.getReference(btnvalue.getText().toString());
        //mReffchild=mRef.child(addtocartbtnvalue.getText().toString());





    }

    //load data recycler view onstart

//    public  void  addtocart(View view){
//        startActivity(new Intent(BooksActivity.this,OfficeActivity.class));
//    }

// start search bar
private  void firebaseSearch(String searchText){
    Query firebaseSearchQuery = mRef.orderByChild("title").startAt(searchText).endAt(searchText
    + "\uf8ff");

    FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter=
            new FirebaseRecyclerAdapter<Model, ViewHolder>(Model.class,
                    R.layout.row,
                    ViewHolder.class,firebaseSearchQuery) {
                @Override
                protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                    viewHolder.setDetails(getApplicationContext(),model.getTitle(),
                            model.getDescription(),model.getIsbn(),model.getPrice(),model.getImage(),model.getCatagory());

                }
                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                    viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                        @Override
                        public void onItemClick(View view, int position) {

                            TextView mTitleTv = view.findViewById(R.id.rtitletv);
                            TextView mDesctv = view.findViewById(R.id.rdescriptiontv);
                            TextView mIsbntv = view.findViewById(R.id.risbntv);
                            TextView mPricetv = view.findViewById(R.id.rpricetv);
                            ImageView mImageViewtv = view.findViewById(R.id.rimagetv);
                            TextView  mCatagorytv = view.findViewById(R.id.rcatagorytv);



                            String mTitle = mTitleTv.getText().toString();
                            String mDesc = mDesctv.getText().toString();
                            String mIsbn = mIsbntv.getText().toString();
                            String mPrice = mPricetv.getText().toString();
                            String mCatagory = mCatagorytv.getText().toString();
                            Drawable mDrawable = mImageViewtv.getDrawable();
                            Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                            // pass data using intent

                            Intent intent = new Intent(view.getContext(),PostDetailActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                            byte[] bytes = stream.toByteArray();
                            intent.putExtra("image",bytes);
                            intent.putExtra("title",mTitle);
                            intent.putExtra("description",mDesc);
                            intent.putExtra("isbn",mIsbn);
                            intent.putExtra("price",mPrice);
                            intent.putExtra("catagory",mCatagory);

                            startActivity(intent);

                        }

                        @Override
                        public void onItemLongClick(View view, int position){
                            // on long click
                            Toast.makeText(BooksActivity.this,"Long click"+position,Toast.LENGTH_SHORT).show();
                        }
                    });

                    return viewHolder;
                }

            };
    mRecyclerView.setAdapter(firebaseRecyclerAdapter);
}




    @Override
    protected  void  onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter =
          new FirebaseRecyclerAdapter<Model, ViewHolder>(
                  Model.class,
                  R.layout.row,
                  ViewHolder.class,
                  mRef
          ) {
              @Override
              protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {

                  viewHolder.setDetails(getApplicationContext(),model.getTitle(),
                          model.getDescription(),model.getIsbn(),model.getPrice(),model.getImage(),model.getCatagory());


              }

              @Override
              public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                 ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {

                        TextView mTitleTv = view.findViewById(R.id.rtitletv);
                        TextView mDesctv = view.findViewById(R.id.rdescriptiontv);
                        TextView mIsbntv = view.findViewById(R.id.risbntv);
                        TextView mPricetv = view.findViewById(R.id.rpricetv);
                        ImageView mImageViewtv = view.findViewById(R.id.rimagetv);
                        TextView  mCatagorytv = view.findViewById(R.id.rcatagorytv);


                        String mTitle = mTitleTv.getText().toString();
                        String mDesc = mDesctv.getText().toString();
                        String mIsbn = mIsbntv.getText().toString();
                        String mPrice = mPricetv.getText().toString();
                        String mCatagory = mCatagorytv.getText().toString();
                        Drawable mDrawable = mImageViewtv.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                        // pass data using intent

                        Intent intent = new Intent(view.getContext(),PostDetailActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra("image",bytes);
                        intent.putExtra("title",mTitle);
                        intent.putExtra("description",mDesc);
                        intent.putExtra("isbn",mIsbn);
                        intent.putExtra("price",mPrice);
                        intent.putExtra("catagory",mCatagory);
                        startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position){
                        // on long click
                        Toast.makeText(BooksActivity.this,"Long click"+position,Toast.LENGTH_SHORT).show();
                        // Add cart system here and copy on both
                    }
                });

                  return viewHolder;
              }
          };

        // set adapter recyclerview

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(BooksActivity.this,LoginActivity.class));
        Toast.makeText(BooksActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }
    private  void cart(){

        startActivity(new Intent(BooksActivity.this,CartActivity.class));
        Toast.makeText(BooksActivity.this,"View Cart", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               firebaseSearch(newText);
                return false;
            }
        });
        return  super.onCreateOptionsMenu(menu);
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
