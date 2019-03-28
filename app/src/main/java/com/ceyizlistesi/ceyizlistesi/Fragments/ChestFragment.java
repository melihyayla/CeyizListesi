package com.ceyizlistesi.ceyizlistesi.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ceyizlistesi.ceyizlistesi.Product;
import com.ceyizlistesi.ceyizlistesi.R;

public class ChestFragment  extends Fragment {

    TextView percentageElectric, percentageMachine, percentageFurnite, percentageCurtain;
    ProgressBar progressBarElectric, progressBarMachine, progressBarFurnite, progressBarCurtain;
    LinearLayout linearLayoutMachine, linearLayoutElectric, linearLayoutFurnite, linearLayoutCurtain;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chest,container,false);

        linearLayoutMachine = view.findViewById(R.id.linear_layout_washing_machine);
        linearLayoutElectric = view.findViewById(R.id.linear_layout_electric);
        linearLayoutCurtain = view.findViewById(R.id.linear_layout_curtain);
        linearLayoutFurnite = view.findViewById(R.id.linear_layout_furniture);



        linearLayoutMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), Product.class);
                intent.putExtra("Value","Washing_machine");
                startActivity(intent);

            }
        });

        percentageElectric = view.findViewById(R.id.percentage_electric);
        percentageMachine = view.findViewById(R.id.percentage_machine);
        percentageCurtain = view.findViewById(R.id.percentage_curtain);
        percentageFurnite = view.findViewById(R.id.percentage_furnite);

        progressBarElectric = view.findViewById(R.id.progressBarElectric);
        progressBarMachine = view.findViewById(R.id.progressBarWashingMachine);
        progressBarCurtain = view.findViewById(R.id.progressBarCurtain);
        progressBarFurnite = view.findViewById(R.id.progressBarFurnite);


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)percentageElectric.getLayoutParams();
        params.setMargins(75, 20, 5, 5);

        LinearLayout.LayoutParams paramsMachine = (LinearLayout.LayoutParams)percentageMachine.getLayoutParams();
        paramsMachine.setMargins(75, 20, 5, 5);

        LinearLayout.LayoutParams paramsCurtain = (LinearLayout.LayoutParams)percentageCurtain.getLayoutParams();
        paramsCurtain.setMargins(20, 20, 5, 5);

        LinearLayout.LayoutParams paramsFurnite = (LinearLayout.LayoutParams)percentageFurnite.getLayoutParams();
        paramsFurnite.setMargins(20, 20, 5, 5);


        percentageElectric.setLayoutParams(params);
        progressBarElectric.setProgress(20);
        percentageElectric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)percentageElectric.getLayoutParams();
                params.setMargins(310, 20, 5, 5); //substitute parameters for left, top, right, bottom

                percentageElectric.setLayoutParams(params);

                progressBarElectric.setProgress(100);
                progressBarMachine.setProgress(30);

                percentageElectric.setText(""+100);

                percentageMachine.setText(""+30);

            }
        });


        return view;
    }




}
