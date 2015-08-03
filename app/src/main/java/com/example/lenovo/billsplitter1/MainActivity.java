package com.example.lenovo.billsplitter1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void sendBill(View view) {
        // Create New Activity where u show Bill Detail with frinds list

        Intent intent = new Intent(this,History.class);
        String message = editText.getText().toString();
        intent.putExtra("billNumber", message);
        startActivity(intent);

    }

    //for Create New bill
    public void createNew(View view) {
        Intent intent = new Intent(this, CreateNew.class);
        startActivity(intent);
    }
    // Call History Activity
    public void onHistory(View view) {
        Log.i("","");
        Intent intent = new Intent(this, History.class);
        startActivity(intent);

    }
}
