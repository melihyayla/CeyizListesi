package com.ceyizlistesi.ceyizlistesi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    int id, piece, price;
    String productName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        String str = getIntent().getStringExtra("Value");
        id = 0;
        price=0 ;
        productName = "Ürün";
        progressBar = findViewById(R.id.circularProgressbar);
        prodcutButton = findViewById(R.id.new_product_button);

        demoLinear = findViewById(R.id.demo_linear_product);

        activeProducts = findViewById(R.id.active_products);
        passiveProducts = findViewById(R.id.passive_products);

        progressBar.setProgress(25);


        checked = getResources().getDrawable(R.drawable.ic_added);
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
        final LinearLayout detailPage = mView.findViewById(R.id.detail_page_linear);
        final LinearLayout background = mView.findViewById(R.id.background_linear_layout);


        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                InputMethodManager iM = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

               //inputMethodManager.hideSoftInputFromWindos activity.getCurrentFocus().getWindowToken(), 0);

                im.hideSoftInputFromWindow(dialog.getWindow().getDecorView().getWindowToken(), 0);
            }
        });




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
                productName = productNameEditText.getText().toString();


                if(!priceEditText.getText().toString().isEmpty())
                    price =  Integer.parseInt(priceEditText.getText().toString());


                createProduct(activeProducts, productName, piece, price);
                dialog.dismiss();
            }
        });






        detailPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!priceEditText.getText().toString().isEmpty())
                    price =  Integer.parseInt(priceEditText.getText().toString());

                productName = productNameEditText.getText().toString();

                Intent intent = new Intent(Product.this, ProductDetail.class);
                intent.putExtra("price", price);
                intent.putExtra("piece", piece);
                intent.putExtra("productName", productName);
                finish();

                startActivity(intent);
                dialog.dismiss();
            }
        });


        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void createProduct(LinearLayout parent, final String myProductName, final int myPiece, final int myPrice){

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (40 * scale + 0.5f);
        int pixels2 = (int) (25 * scale + 0.5f);
        int pixels3 = (int) (17 * scale + 0.5f);
        int pixel0 = (int) (0* scale + 0.5f);

        final LinearLayout newProduct = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);

        layoutParams.setMargins(10,10,10,10);

        newProduct.setLayoutParams(layoutParams);

        newProduct.setBackground(getResources().getDrawable(R.drawable.product_linear_background));
        newProduct.setOrientation(LinearLayout.HORIZONTAL);


        final ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageDrawable(unchecked);

        final ImageView imageView3 = new ImageView(getApplicationContext());
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_password));

        final LinearLayout.LayoutParams imageParams    = new LinearLayout.LayoutParams( pixel0 , pixels2);
        imageParams.setMargins(5,10,10,10);
        imageParams.weight = 1;
        imageView.setLayoutParams(imageParams);


        final LinearLayout.LayoutParams productsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        activeProducts.setLayoutParams(productsParams);
        passiveProducts.setLayoutParams(productsParams);

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


        textParams.setMargins(10,10,pixel0,10);
        textParams.weight = 1;
        tv1.setTypeface(Typeface.create("gordita_regular", Typeface.NORMAL));
        tv1.setTextColor(Color.parseColor("#474167"));
        tv1.setLayoutParams(textParams);



        newProduct.addView(tv1);

        final LinearLayout.LayoutParams imageParams3    = new LinearLayout.LayoutParams( pixel0 , pixels3);
        imageParams3.setMargins(0,10,0,10);
        imageParams3.weight = 1;
        imageView3.setLayoutParams(imageParams3);

        newProduct.addView(imageView3);

        final LinearLayout empty = new LinearLayout(this);

        LinearLayout.LayoutParams emptyLayoutParams = new LinearLayout.LayoutParams(pixel0, LinearLayout.LayoutParams.WRAP_CONTENT);
        emptyLayoutParams.setMargins(0,10,0,10);
        empty.setLayoutParams(emptyLayoutParams);
        empty.setOrientation(LinearLayout.HORIZONTAL);
        emptyLayoutParams.weight=8;

        newProduct.addView(empty);


        String myPN = productName;
        if(piece>1){
            myPN += "(" + piece + ")";
        }
        else{
            piece = 0;
        }


        tv1.setText(myPN);


        ImageView detail_arrow_image_view = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams imageParams2  = new LinearLayout.LayoutParams( pixel0 , pixels2*90/100);
        imageParams2.setMargins(5,10,15,10);
        imageParams2.weight = 1;
        detail_arrow_image_view.setLayoutParams(imageParams2);
        detail_arrow_image_view.setImageDrawable(detail_arrow);


        detail_arrow_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product.this, ProductDetail.class);
                intent.putExtra("price", myPrice);
                intent.putExtra("piece", myPiece);
                intent.putExtra("productName", myProductName);

                startActivity(intent);
                finish();
            }
        });

        newProduct.addView(detail_arrow_image_view);

        parent.addView(newProduct);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


}
