package com.example.lenovo.billsplitter1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.sqlite.SQLiteOpenHelper;


public class Friends extends Activity {
    private ListView list;
    public SQLiteDatabase db;
    DatabaseOperations ob;

//uy8rr
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ob  = new DatabaseOperations(Friends.this);
        db = ob.getWritableDatabase();

        list = (ListView) findViewById(R.id.listview);
        //if (obj1.count1 > 0) {
        ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
        try {
            Cursor c = null;
            c = ob.GetAllFriendInformation();

            if (c.moveToFirst()) {
                do {
                    Log.i("getFromDB()", "");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Id", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_ID)));
                    map.put("name", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_NAME)));
                    map.put("email", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_EMAIL)));
                    map.put("percent", c.getString(c.getColumnIndex(DatabaseOperations.FRIENDS_PERCENT)));
                    dbData.add(map);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        for(int i = 0; i < dbData.size();i++){
            HashMap<String, String> map = dbData.get(i);
            Log.i("Msg", "Friend name:: "+map.get("name"));
        }

        if (dbData.size() > 0) {
            //dbdate is ur collection(ArrayList of hashmap)
            list.setAdapter(new ListAdp(dbData));
        } else {
            Log.d("No friends found", "");
            finish();
        }


        // }plz run it i ma going for meeting
        //plz wait it takes time
    }

    class ListAdp extends BaseAdapter {
        ArrayList<HashMap<String, String>> FriendListData;

        public ListAdp(ArrayList<HashMap<String, String>> dbData) {
            FriendListData = dbData;
        }

        @Override
        public int getCount() {
            return FriendListData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = null;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                //here u have to inflate urs xml(single Listview itwm)
                //we dont know how to do it
                v = inflater.inflate(R.layout.inflate1_list, null);
            } else {
                v = convertView;
            }
            TextView t1 = (TextView) v.findViewById(R.id.id);
            TextView t2 = (TextView) v.findViewById(R.id.name);
            TextView t3 = (TextView) v.findViewById(R.id.email);
            TextView t4 = (TextView) v.findViewById(R.id.percent);



            HashMap<String, String> map = FriendListData.get(position);

            t1.setText(map.get("Id"));
            t3.setText(map.get("name"));
            t2.setText(map.get("email"));
            t4.setText(map.get("percent"));

            return v;
        }
    }
}
//check number of bills in db
//store all info of bill table in any collection (ArrayList of hashmap)
//Check how to store value in arraylist through cursor
//check Sqlite sample code  for Listview

//getFromDB();



    /*
    // Change to ur requirement
    private void getFromDB() {
		//check logic
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		try{
			db = (new SQLiteHelp(this)).getWritableDatabase();
			Cursor c = db.query(SQLiteHelp.CONTACT_TABLE, dataColumn, null, null, null, null, null);
	        if (c.moveToFirst()) {
				do {
					Log.i(TAG , "getFromDB()");
					HashMap<String, String> map = new HashMap<String, String>();

					map.put("Name",c.getString(c.getColumnIndex(SQLiteHelp.CONTACT_NAME)));
					map.put("Phone",c.getString(c.getColumnIndex(SQLiteHelp.CONTACT_PHONE)));
					map.put("City",c.getString(c.getColumnIndex(SQLiteHelp.CONTACT_CITY)));
					dbData.add(map);
				} while (c.moveToNext());
			}
	        c.close();
	        db.close();
		}catch (Exception e) {
			Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
		}

		 if (dbData.size() > 0) {
            //how to put data in List view
            list.setAdapter(new ListAdp(dbData));
        } else {
            Log.d("No history found", "");
            finish();
        }



	}
     */


