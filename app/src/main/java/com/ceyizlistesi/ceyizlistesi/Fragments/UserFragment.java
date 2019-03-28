package com.ceyizlistesi.ceyizlistesi.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.ceyizlistesi.ceyizlistesi.R;
import com.ceyizlistesi.ceyizlistesi.SignInActivity;
import com.ceyizlistesi.ceyizlistesi.SignUpActivity;

public class UserFragment extends Fragment {

    Button sign_in_button, sign_up_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);


        sign_in_button = view.findViewById(R.id.sign_in_button);
        sign_up_button = view.findViewById(R.id.sign_up_button);


        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignInActivity.class);
                startActivity(intent);

            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }
}
