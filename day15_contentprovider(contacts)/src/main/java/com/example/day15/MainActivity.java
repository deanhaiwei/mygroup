package com.example.day15;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{
    public static final int REQUEST_CODE=100;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListView();
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CODE);
        }else {

            LoadAsyncContacts();



        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                LoadAsyncContacts();

            }
        }
    }

    public void setListView(){
        ListView listView = findViewById(R.id.listviewID);
        adapter = new SimpleCursorAdapter(
                this,android.R.layout.simple_list_item_2, cursor,
               new String[]{Phone.NUMBER, Phone.DISPLAY_NAME},
               new int []{android.R.id.text1,android.R.id.text2},
        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);

        String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));

    }
    public void LoadAsyncContacts(){

        Bundle bundle = new Bundle();
        bundle.putParcelable("uriKey",Phone.CONTENT_URI);
        LoaderManager.LoaderCallbacks<Cursor> callbacks = this;
        getSupportLoaderManager().initLoader(1, bundle,callbacks);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id==1){
            Uri uri = args.getParcelable("uriKey") ;
            return  new CursorLoader(this,uri,null,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
