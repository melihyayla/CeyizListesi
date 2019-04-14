package com.ceyizlistesi.ceyizlistesi.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceyizlistesi.ceyizlistesi.Product;
import com.ceyizlistesi.ceyizlistesi.ProductDetail;
import com.ceyizlistesi.ceyizlistesi.R;

public class DiscoverFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover,container,false);

        return view;
    }



    public void createProduct(LinearLayout parent, final String myProductName, final int myPiece, final int myPrice){

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (40 * scale + 0.5f);
        int pixels2 = (int) (25 * scale + 0.5f);


        final LinearLayout newProduct = new LinearLayout(getContext());

        LinearLayout.LayoutParams layoutParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);

        layoutParams.setMargins(10,10,10,10);

        newProduct.setLayoutParams(layoutParams);

        newProduct.setBackground(getResources().getDrawable(R.drawable.product_linear_background));
        newProduct.setOrientation(LinearLayout.HORIZONTAL);


        final ImageView imageView = new ImageView(getContext());
       // imageView.setImageDrawable(unchecked);
        final LinearLayout.LayoutParams imageParams    = new LinearLayout.LayoutParams( pixels2 , pixels2);
        layoutParams.setMargins(15,10,15,10);
        layoutParams.weight = 1;
        imageView.setLayoutParams(imageParams);
        final LinearLayout.LayoutParams productsParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);




        newProduct.addView(imageView);
        TextView tv1 = new TextView(getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 4;
        textParams.setMargins(10,10,10,10);
        tv1.setTypeface(Typeface.create("gordita_regular", Typeface.NORMAL));
        tv1.setTextColor(Color.parseColor("#474167"));
        tv1.setLayoutParams(textParams);











        parent.addView(newProduct);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

}
