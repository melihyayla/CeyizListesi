package com.ceyizlistesi.ceyizlistesi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Product extends AppCompatActivity {
    TextView textView ;
    ProgressBar progressBar;
    LinearLayout prodcutButton, activeProducts, passiveProducts, demoLinear;
    Drawable unchecked, checked, detail_arrow;
    int id, piece;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String str = getIntent().getStringExtra("Value");
        id = 0 ;
        progressBar = findViewById(R.id.circularProgressbar);
        prodcutButton = findViewById(R.id.new_product_button);

        demoLinear = findViewById(R.id.demo_linear_product);

        activeProducts = findViewById(R.id.active_products);
        passiveProducts = findViewById(R.id.passive_products);

        progressBar.setProgress(25);


        checked = getResources().getDrawable(R.drawable.ic_check);
        unchecked = getResources().getDrawable(R.drawable.ic_add);
        detail_arrow = getResources().getDrawable(R.drawable.ic_detail_arrow);


        prodcutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //createProduct(activeProducts,"New", 2);

                //Intent intent = new Intent(Product.this, ProductDetail.class);
                //startActivity(intent);

                createDialogBox();
            }
        });


    }

    public void createDialogBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.add_product_box,null);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();

        final LinearLayout productNameLinear  = mView.findViewById(R.id.product_name_linear_layout) ;
        final LinearLayout productNumber = mView.findViewById(R.id.product_number_linear_layout);
        final LinearLayout priceNumber = mView.findViewById(R.id.price_linear_layout);
        final EditText productNameEditText, priceEditText;
        ImageView decreaseButton, increaseButton, closeButton;
        Button saveButton;
        final TextView pieceText = mView.findViewById(R.id.pieceText);
        piece = 0;

        saveButton = mView.findViewById(R.id.save_button);
        decreaseButton = mView.findViewById(R.id.decrease_button);
        increaseButton = mView.findViewById(R.id.increase_button);

        closeButton = mView.findViewById(R.id.close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(piece<1){
                    piece=0;
                    pieceText.setText(""+piece);
                }

                else{
                    piece--;
                    pieceText.setText(""+piece);
                }


            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(piece<20){
                    piece++;
                    pieceText.setText(""+piece);
                }

                else{
                    piece=20;
                    pieceText.setText(""+piece);
                }

            }
        });

        pieceText.setText(""+piece);

        final Drawable backgroundActive = getResources().getDrawable(R.drawable.linear_layout_bg_active);

        final Drawable backgroundDeactive = getResources().getDrawable(R.drawable.linear_layout_bg);


        productNameEditText = mView.findViewById(R.id.product_name_edit_text);

        priceEditText = mView.findViewById(R.id.price_edit_text);


        productNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                productNameLinear.setBackground(backgroundActive);
                productNumber.setBackground(backgroundDeactive);
                priceNumber.setBackground(backgroundDeactive);
                Log.i("Productname", "Deneme1");
            }
        });




        Log.i("Productname", "Deneme");





        priceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                productNameLinear.setBackground(backgroundDeactive);
                productNumber.setBackground(backgroundDeactive);
                priceNumber.setBackground(backgroundActive);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = productNameEditText.getText().toString();

                createProduct(activeProducts, productName, piece);
                dialog.dismiss();
            }
        });



        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void createProduct(LinearLayout parent, String productName, int piece){

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (40 * scale + 0.5f);
        int pixels2 = (int) (25 * scale + 0.5f);


        //ViewGroup.LayoutParams mainLayoutParams = parent.getLayoutParams();
        //mainLayoutParams.height = parent.getMeasuredHeight() + pixels;


        final LinearLayout newProduct = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);

        layoutParams.setMargins(10,10,10,10);

        newProduct.setLayoutParams(layoutParams);
        //newProduct.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels));

        newProduct.setBackground(getResources().getDrawable(R.drawable.product_linear_background));
        newProduct.setOrientation(LinearLayout.HORIZONTAL);


        final ImageView imageView = new ImageView(getApplicationContext());

        imageView.setImageDrawable(unchecked);

        LinearLayout.LayoutParams imageParams    = new LinearLayout.LayoutParams( pixels2 , pixels2);

        layoutParams.setMargins(15,10,15,10);
        layoutParams.weight = 1;

        imageView.setLayoutParams(imageParams);

        final LinearLayout.LayoutParams productsParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);





            imageView.setOnClickListener(new View.OnClickListener() {
                Boolean demoFlag = false;
                @Override
                public void onClick(View view) {
                    if(!demoFlag){
                        imageView.setImageDrawable(checked);
                        activeProducts.removeView(newProduct);
                        passiveProducts.addView(newProduct);
                        activeProducts.setLayoutParams(productsParams);
                        passiveProducts.setLayoutParams(productsParams);
                        demoFlag = true;
                    }
                    else {
                        imageView.setImageDrawable(unchecked);
                        passiveProducts.removeView(newProduct);
                        activeProducts.addView(newProduct);
                        activeProducts.setLayoutParams(productsParams);
                        passiveProducts.setLayoutParams(productsParams);
                        demoFlag = false;
                    }


                }
            });





        newProduct.addView(imageView);

        TextView tv1 = new TextView(getApplicationContext());


        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        textParams.weight = 4;

       textParams.setMargins(10,10,10,10);

        tv1.setTypeface(Typeface.create("gordita_regular", Typeface.NORMAL));

        tv1.setTextColor(Color.parseColor("#474167"));

        tv1.setLayoutParams(textParams);



        newProduct.addView(tv1);

        if(piece>1){
            productName += "(" + piece + ")";
        }
        else{
            piece = 0;
        }


        tv1.setText(productName);

        ImageView detail_arrow_image_view = new ImageView(getApplicationContext());

        LinearLayout.LayoutParams imageParams2    = new LinearLayout.LayoutParams( pixels2 , pixels2);

        imageParams2.setMargins(5,10,15,10);

        imageParams.weight = 1;

        detail_arrow_image_view.setLayoutParams(imageParams2);

        detail_arrow_image_view.setImageDrawable(detail_arrow);

        newProduct.addView(detail_arrow_image_view);

        parent.addView(newProduct);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));






        /*
        //children of parent linearlayout

        ImageView iv = new ImageView(getApplicationContext());

        LinearLayout layout2 = new LinearLayout(getApplicationContext());

        layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout2.setOrientation(LinearLayout.VERTICAL);

        parent.addView(iv);
        parent.addView(layout2);

        //children of layout2 LinearLayout


        TextView tv2 = new TextView(getApplicationContext());
        TextView tv3 = new TextView(getApplicationContext());
        TextView tv4 = new TextView(getApplicationContext());

        layout2.addView(tv1);
        layout2.addView(tv2);
        layout2.addView(tv3);
        layout2.addView(tv4);*/
    }
}
