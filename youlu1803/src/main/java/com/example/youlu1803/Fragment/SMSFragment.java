package com.example.youlu1803.Fragment;


import android.Manifest;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.youlu1803.Adapter.ConversationAdapter;
import com.example.youlu1803.Entity.Conversation;
import com.example.youlu1803.Manager.DialogManager;
import com.example.youlu1803.Manager.SMSManager;
import com.example.youlu1803.MessageActivity;
import com.example.youlu1803.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SMSFragment extends MyBaseFragment {


    private ListView listView;
    private ConversationAdapter adapter;
    private boolean permisstion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView= inflater.inflate(R.layout.fragment_sm, container, false);


        InitialUI();
        checkPermisstion();
        return contentView;
    }

    private void checkPermisstion() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS)!=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},103);
        }else {
            refershConversation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 103:
                if (grantResults.length>0){
                    if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(),"获得短信权限才可以使用",Toast.LENGTH_SHORT).show();
                        return;
                    }
                        permisstion=true;
                        refershConversation();

                }else {
                    Toast.makeText(getActivity(),"没有进行相关权限的处理",Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }

    @Override
    public void InitialUI() {
        actionBar= contentView.findViewById(R.id.SMS_ActionBar);
        InitialActionBar(-1,"短消息",-1);
        listView = contentView.findViewById(R.id.SMS_ListView);
        adapter = new ConversationAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener( new MyOnItemClickListener());
        listView.setOnItemLongClickListener(new MyOnItemLongClickListener());
        //refershConversation();

    }

    private void refershConversation() {
        List<Conversation> conversations = SMSManager.getAllConversation(getActivity());
        adapter.addData(conversations,true);
    }
    public class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Conversation conversation = adapter.getItem(position);
            DialogManager.ShowDeleteConversationDialog(conversation,getActivity(),adapter);
            return true;
        }
    }
    public class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Conversation conversation = adapter.getItem(position);
            String name = conversation.getName();
            int thread_id = conversation.getThread_id();
            String address = conversation.getAddress();

            int read = conversation.getRead();
            if (read==0){
                SMSManager.UpdataConversation(getActivity(),thread_id);
            }

            Intent intent = new Intent(getActivity(), MessageActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("thread_Id",thread_id);
            intent.putExtra("address",address);
            startActivity(intent);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permisstion){
            refershConversation();
        }else {
            permisstion=true;
        }
    }
}
