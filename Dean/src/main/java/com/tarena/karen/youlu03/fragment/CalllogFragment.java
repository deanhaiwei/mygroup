package com.tarena.karen.youlu03.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.entity.Calllog;
import com.tarena.karen.youlu03.manager.ContactsManager;

import java.util.List;

/**
 * Created by pjy on 2017/5/16.
 */

public  class CalllogFragment
        extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
         contentView=inflater.
                inflate(
                        R.layout.fragment_calllog_layout,
                        container,false);
         initialUI();
        return contentView;
    }

    @Override
    public void initialUI() {
        //初始设置标题栏
        actionbar= (LinearLayout) contentView.findViewById(R.id.actionbar_CallLog);
        initialActionbar(-1,"通话记录",-1);
        List<Calllog> calllogs=ContactsManager.getAllCalllogs(getActivity());

        for (Calllog calllog:calllogs) {
            Log.i("TAG:calllog",calllog.toString());
        }
    }
}
