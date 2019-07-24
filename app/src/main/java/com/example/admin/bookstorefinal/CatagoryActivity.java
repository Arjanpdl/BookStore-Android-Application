package com.example.admin.bookstorefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CatagoryActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
private Button btnoffice;
private  Button btnbooks;
int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);
        firebaseAuth = FirebaseAuth.getInstance();
        btnoffice = findViewById(R.id.btnnoffice);
        btnbooks = findViewById(R.id.btnnbooks);
    }

    public void Books (View view)
    {

       // startActivity(new Intent(this,BooksActivity.class));

        String TextCatagoryname = btnbooks.getText().toString();
        // starting our intent
        Intent classintent = new Intent(this,BooksActivity.class);
        classintent.putExtra("Catagoryname",TextCatagoryname);
        startActivityForResult(classintent,request_code);

    }

    public  void  Office (View view)
    {

        //startActivity(new Intent(this,OfficeActivity.class));

        String TextCatagoryname = btnoffice.getText().toString();
        // starting our intent
        Intent classintent = new Intent(this,BooksActivity.class);
        classintent.putExtra("Catagoryname",TextCatagoryname);
        startActivityForResult(classintent,request_code);
    }









    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(CatagoryActivity.this,LoginActivity.class));
        Toast.makeText(CatagoryActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }
    private  void cart(){

        startActivity(new Intent(CatagoryActivity.this,CartActivity.class));
        Toast.makeText(CatagoryActivity.this,"View Cart", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
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
//        switch (item.getItemId()){
//            case  R.id.logoutMenu:{
//                Logout();
//            }
//            case R.id.cartMenu:{
//                cart();
//            }
//        }
        return super.onOptionsItemSelected(item);
    }

}
