package com.example.youlu1803.Fragment;


import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.youlu1803.Adapter.ContactAdapter;
import com.example.youlu1803.Entity.Contact;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.DialogManager;
import com.example.youlu1803.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends MyBaseFragment {


    private ContactAdapter adapter;
    private boolean permissionsFlog=false;
    private GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       contentView =  inflater.inflate(R.layout.fragment_contact, container, false);
       InitialUI();
       requestMyPermissions();
       return contentView;
    }

    private void requestMyPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},100);
        }else {

            refreshContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:
                if (grantResults.length>0){
                    if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(),"必须通过访问权限才能使用相应功能",Toast.LENGTH_LONG).show();
                        return;
                    }
                    permissionsFlog=true;
                    refreshContact();
                }else {
                Toast.makeText(getActivity(),"没有执行权限处理",Toast.LENGTH_LONG).show();
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void InitialUI() {
        actionBar = contentView.findViewById(R.id.Contact_ActionBar);
        InitialActionBar(-1,"手机联系人",R.mipmap.ic_search);
        gridView = contentView.findViewById(R.id.Contact_GridView);
        adapter = new ContactAdapter(getActivity());
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new MyOnItemClickListener());
        gridView.setOnItemLongClickListener(new MyOnItemLongClickListener());
    }
    public void refreshContact(){
        List<Contact> contacts = ContactsManager.getAllcontacts(getActivity());

        Contact contact = new Contact(0,"添加联系人",null,null,null,0);
        contacts.add(0,contact);
        adapter.addData(contacts,true);


    }
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position==0){
                DialogManager.ShowAddDialogContact(getActivity());

            }else {
                Contact contact = adapter.getItem(position);
                DialogManager.ShowEditContactDialog(getActivity(),contact);
            }
        }
    }
    class  MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Contact contact = adapter.getItem(position);
            DialogManager.ShowDeleteContactDialog(contact,getActivity(),adapter);
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionsFlog){
            refreshContact();
        }else {
            permissionsFlog = true;
        }

    }
}
