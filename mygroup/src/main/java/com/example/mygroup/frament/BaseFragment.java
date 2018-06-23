package com.example.mygroup.frament;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygroup.R;
import com.example.mygroup.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

 public void Sikp(View view){
     view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getActivity(), MainActivity.class);
             startActivity(intent);
             getActivity().finish();
         }
     });
 }





}
