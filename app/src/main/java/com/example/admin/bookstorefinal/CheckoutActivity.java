package com.example.admin.bookstorefinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CheckoutActivity extends AppCompatActivity {
EditText mstudentid,mstudentname;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference mRef;
Button checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mstudentid = findViewById(R.id.studentidcheckout);
        mstudentname = findViewById(R.id.checkoutname);
        checkout = findViewById(R.id.buttoncheckout);
       TextView mDisplayDate = (TextView) findViewById(R.id.bookcheckout);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String date = month+1 + "-" + day + "-" + year;

        mDisplayDate.setText(date);
        FirebaseDatabase mFirebasedatabase;
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mRef = mFirebasedatabase.getReference("Cart");


        databaseReference = FirebaseDatabase.getInstance().getReference("Order").child("Date "+date);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });
    }

    public void checkout(){
        String studentNameValue = mstudentname.getText().toString();
        String mcneeseIdValue = mstudentid.getText().toString();
        if(!TextUtils.isEmpty(studentNameValue)&&!TextUtils.isEmpty(mcneeseIdValue)){
            String id = databaseReference.push().getKey();
            Students students = new Students(id,studentNameValue,mcneeseIdValue);
            // databaseReference.child(bttnName.getText().toString()).push().setValue(students);
            databaseReference.child(mstudentid.getText().toString()).setValue(students);
            mstudentname.setText("");
            mstudentid.setText("");
            mRef.removeValue();
            Toast.makeText(CheckoutActivity.this,"Thanks For purchase",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(CheckoutActivity.this,"CAnnot Purchase",Toast.LENGTH_SHORT).show();
        }
    }
}
