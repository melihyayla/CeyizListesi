package com.ceyizlistesi.ceyizlistesi.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceyizlistesi.ceyizlistesi.R;

public class NotificationsFragment extends Fragment {

    LinearLayout linearLayout ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications,container,false);

        linearLayout = view.findViewById(R.id.demo_linear);

        addView(view);

        return view;
    }


    public void addView(View view){

        TextView tv1 = new TextView(view.getContext());

        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tv1.setLayoutParams(lparams);

        // newProduct.addView(tv1);

        tv1.setText("Demo");

        linearLayout.addView(tv1);
    }
}
