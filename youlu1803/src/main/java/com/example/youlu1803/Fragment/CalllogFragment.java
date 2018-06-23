package com.example.youlu1803.Fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.youlu1803.Adapter.CalllogAdpter;
import com.example.youlu1803.Entity.Colllog;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.DialogManager;
import com.example.youlu1803.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalllogFragment extends MyBaseFragment {


    private ListView listView;
    private CalllogAdpter adpter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView =  inflater.inflate(R.layout.fragment_calllog, container, false);
        InitialUI();
        checkPermissions();
        return contentView;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALL_LOG)!=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_CALL_LOG },101);
        }else {
            refreshCalllog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 101:
                if (grantResults.length>0){
                    if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(),"权限未通过",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        refreshCalllog();
                    }
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
        actionBar = contentView.findViewById(R.id.Calllog_ActionBar);
        InitialActionBar(-1,"通话记录",-1);
        listView = contentView.findViewById(R.id.Calllog_ListView);
        adpter = new CalllogAdpter(getActivity());
        listView.setAdapter(adpter);
        listView.setOnItemLongClickListener(new MyOnItemLongClickListener());

    }

    private void refreshCalllog() {
        List<Colllog> colllogs = ContactsManager.AddAllCalllog(getActivity());
        adpter.addData(colllogs,true);
    }

    public class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Colllog colllog = adpter.getItem(position);
            DialogManager.ShowDeleteCalllogDialog(colllog,getActivity(),adpter);
            return true;
        }
    }
}
