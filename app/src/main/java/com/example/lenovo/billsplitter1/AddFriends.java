package com.example.lenovo.billsplitter1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
///changes hear

public class AddFriends extends Activity {
    private EditText Name, Email, Percent;
    private String TAG = "AddFriends";
    int amountValue;
    int splitAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriends);
        Bundle bundle = getIntent().getExtras();
        amountValue = bundle.getInt("Amount");
        Log.i(TAG, "amountValue:: " + amountValue);

        Name= (EditText) findViewById(R.id.type);
        Email = (EditText) findViewById(R.id.date);
        Percent = (EditText) findViewById(R.id.amount);
    }

    public void save(View v) {
        String fname = Name.getText().toString();
        String email = Email.getText().toString();
        String percentage = Percent.getText().toString();
        int per = Integer.parseInt(percentage);
        Log.i(TAG, "per:: " + per);
        // accourding to per we cal split Amount ok
        //ok we got it
        splitAmount = (per * amountValue)/100;
        Log.i(TAG, "splitAmount:: " + splitAmount);

        if (fname != null && !fname.equalsIgnoreCase("") && email != null && !email.equalsIgnoreCase("") &&
                percentage != null && !percentage.equalsIgnoreCase("")){
            Intent intent = new Intent();
            intent.putExtra("name", fname);
            intent.putExtra("email", email);
            intent.putExtra("per", percentage);
            intent.putExtra("amount", splitAmount+"");
            setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
        }
    }
}

