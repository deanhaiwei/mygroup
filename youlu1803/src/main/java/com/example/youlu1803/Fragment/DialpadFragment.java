package com.example.youlu1803.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youlu1803.Adapter.CalllogAdpter;
import com.example.youlu1803.Entity.Colllog;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.MediaManager;
import com.example.youlu1803.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialpadFragment extends MyBaseFragment {


    private ListView listView;
    private TextView actionBarTitle;
    private ImageView actionBarRight;
    private RelativeLayout relativeLayoutKeys;
    private String [] key = new String[]{"1","2","3","4","5","6","7","8","9","0","*","#"};
    private ImageButton callPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView= inflater.inflate(R.layout.fragment_dialpad, container, false);
        InitialUI();
        CallPhonePermissions();

        return contentView;
    }

    private void CallPhonePermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},102);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 102:
                if (grantResults.length>0){
                    if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(),"未通过授权",Toast.LENGTH_SHORT).show();

                    }else {
                        InitialKeys();
                    }
                }else {
                    Toast.makeText(getActivity(),"未做响应权限的处理",Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }

    private void InitialKeys() {
        relativeLayoutKeys = contentView.findViewById(R.id.Dialpad_RelativeLayout_Keys);
        callPhone = contentView.findViewById(R.id.Dialpad_CallPhone);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels/3;
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60,metrics);

        for (int i = 0 ;i<key.length;i++){
            final TextView keyforRelative = new TextView(getActivity());
            keyforRelative.setText(key[i]);
            keyforRelative.setId(i+1);
            keyforRelative.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
            keyforRelative.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
            if (i%3!=0){
                params.addRule(RelativeLayout.RIGHT_OF,i);
            }
            if (i>=3){
                params.addRule(RelativeLayout.BELOW,i-2);
            }
            relativeLayoutKeys.addView(keyforRelative,params);
            keyforRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaManager.PlayMusic(getActivity(),R.raw.b);
                    String title = actionBarTitle.getText().toString();
                    if ("拨打电话".equals(title)){
                        actionBarTitle.setText(keyforRelative.getText().toString());
                    }else if (title.length()==3||title.length()==8){
                        actionBarTitle.append("-");
                        actionBarTitle.append(keyforRelative.getText().toString());
                    }else {
                        actionBarTitle.append(keyforRelative.getText().toString());
                    }
                    if (title.length()>=13){
                        actionBarTitle.setText(title.substring(0,title.length()));
                    }
                }
            });

        }
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaManager.PlayMusic(getActivity(),R.raw.a);
                String phone = actionBarTitle.getText().toString();
                Intent intent =new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:"+phone);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    @Override
    public void InitialUI() {
        actionBar = contentView.findViewById(R.id.Dialpad_ActionBar);
        InitialActionBar(R.mipmap.ic_add_icon,"拨打电话",R.mipmap.ic_backspace);
        listView = contentView.findViewById(R.id.Dialpad_ListView);
        actionBarTitle = contentView.findViewById(R.id.TextView_ActionBar_Title);
        actionBarRight = contentView.findViewById(R.id.ImageView_ActionBar_Right);

        CalllogAdpter adpter = new CalllogAdpter(getActivity());
        listView.setAdapter(adpter);
        List<Colllog> colllogs = ContactsManager.AddAllCalllog(getActivity());
        adpter.addData(colllogs,true);
        actionBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = actionBarTitle.getText().toString();
                if ("拨打电话".equals(title)){

                    return;
                }else if (title.length()==1) {
                    actionBarTitle.setText("拨打电话");
                }else
                 if (title.length()==5||title.length()==10){
                    actionBarTitle.setText(title.substring(0,title.length()-2));
                }else {
                    actionBarTitle.setText(title.substring(0,title.length()-1));
                }
            }
        });

    }
}
