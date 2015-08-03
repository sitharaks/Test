package com.example.lenovo.billsplitter1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CreateNew extends Activity {
    EditText BillType , BillDate, TotalAmount ; //Change according camel notation in all class  ok
    private Button SplitButton;
    private String TAG = "CreateNew";
    public SQLiteDatabase db;
    DatabaseOperations ob;
    private static int billNumber;

    private boolean isbillCreated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnew);
        ob  = new DatabaseOperations(CreateNew.this);
        db = ob.getWritableDatabase();
        BillType = (EditText) findViewById(R.id.editText2);
        BillDate = (EditText) findViewById(R.id.editText4);
        TotalAmount = (EditText) findViewById(R.id.editText3);
        SplitButton = (Button) findViewById(R.id.button4);

    }


    // check how to use this function
    //it return back the from  AddFriends ()
    //which function
    //it should come back to the create new bill screen??
    //when u click on add button u call Addfriend as  sub activity
    //inside onActivityResult u recive name,email,percent of ur friends
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("","requestCode: "+requestCode+", resultCode: "+resultCode);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
            {
                //this value is comming from AddFriends Activity one by one
                Bundle b = data.getExtras();
                String name = b.getString("name");
                String email = b.getString("email");
                String per = b.getString("per");
                String amount = b.getString("amount");

                // put friends info in db one by on
               // Cursor a=ob1.GetFriendInformation(ob1);
                Log.i(TAG,"Friends information name "+name);
                Log.i(TAG,"Friends information email "+email);
                Log.i(TAG,"Friends information percentage "+per);
                Log.i(TAG,"Friends information amount "+amount);

                //get data from Addfriends
                //call putFriendsInfo function
                ob.PutFriendInformation(billNumber,name,email,Integer.parseInt(per),Integer.parseInt(amount));

            }
        }
    }

    public void SendMessage(View view) {
        if(BillType!=null && !BillType.getText().toString().equalsIgnoreCase("") && BillDate!=null && !BillDate.getText().toString().equalsIgnoreCase("") &&
                TotalAmount!=null && !TotalAmount.getText().toString().equalsIgnoreCase("")){
            if(!isbillCreated){
                //put bill detail in Sqlite dbs in
                billNumber = ob.PutBillInformation(BillType.getText().toString(),BillDate.getText().toString(),Integer.parseInt(TotalAmount.getText().toString()));
                Log.i("BillNumber",""+billNumber);
                isbillCreated = true;//this if conduction call only first time
            }
            Intent intent = new Intent(this, AddFriends.class);
            intent.putExtra("Amount",Integer.parseInt(TotalAmount.getText().toString()));
            startActivityForResult(intent, 1);
        }

    }

    public void sendMail(View view) {

        Cursor c = null;
        c = ob.GetFriendInformation(billNumber);
        Log.i("C", ""+c.getCount());

        ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();

        String msg = "Hi All"+"\n";
        try{
            if (c.moveToFirst()){
                do{
                    Log.i(TAG, "getFromDB()");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Id",c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_ID)));
                    map.put("Name",c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_NAME)));
                    map.put("Email", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_EMAIL)));
                    map.put("Percent", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_PERCENT)));
                    map.put("Amount", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_AMOUNT)));

                    dbData.add(map);
                } while (c.moveToNext());
            }
            c.close();

        }catch (Exception e) {
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        String[] to = new String[dbData.size()];
        for(int i = 0; i < dbData.size();i++){
            HashMap<String, String> map = dbData.get(i);
            to[i] = map.get(DatabaseOperations.FRIENDS_EMAIL);
            msg =  msg + map.get(DatabaseOperations.FRIENDS_NAME) + " you have to give Rs. "+
                    map.get(DatabaseOperations.FRIENDS_AMOUNT)+ ". \n";
            Log.i("Msg", ""+msg);
        }

        msg = msg + "Thanks";

        Log.i("To", ""+to.toString());

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, to);
        email.putExtra(Intent.EXTRA_SUBJECT, "Bill Detail");
        email.putExtra(Intent.EXTRA_TEXT, msg);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    public void SendMail(View view) {
    }
}


