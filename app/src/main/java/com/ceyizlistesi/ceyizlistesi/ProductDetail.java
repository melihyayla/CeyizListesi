package com.ceyizlistesi.ceyizlistesi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetail extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ImageView productAddButton, cancelButton, demoView;
    EditText informationEditText;
    Switch privacySwitch;
    Boolean checkedFlag = false;
    LinearLayout addPictureLinearLayout, pieceLinearLayout, priceLinearLayout, switchLayout, parentLayout;
    int piece, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        cancelButton = findViewById(R.id.cancel_button);
        productAddButton = findViewById(R.id.product_add_button);
        informationEditText = findViewById(R.id.product_information);
        privacySwitch = findViewById(R.id.switch_private);
        addPictureLinearLayout = findViewById(R.id.add_picture_linear_layout);
        pieceLinearLayout = findViewById(R.id.piece_linear_layout);
        priceLinearLayout = findViewById(R.id.price_linear_layout);
        switchLayout = findViewById(R.id.switch_layout_user);
        demoView = findViewById(R.id.demo_image_view);
        parentLayout = findViewById(R.id.product_detail_parent);


        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(ProductDetail.this);
            }
        });


        privacySwitch.setOnCheckedChangeListener(this);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Drawable checked = getResources().getDrawable(R.drawable.ic_check);
        final Drawable unchecked = getResources().getDrawable(R.drawable.ic_add);

        productAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!checkedFlag){
                    productAddButton.setImageDrawable(checked);}
                else
                    productAddButton.setImageDrawable(unchecked);

                checkedFlag=!checkedFlag;
            }
        });

        addPictureLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action cod
            }
        });

        pieceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPieceDialogBox(0);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    demoView.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    demoView.setImageURI(selectedImage);
                }
                break;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            checkedFlag = true;

        } else {
            checkedFlag = false;

        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void createPieceDialogBox(int newPiece){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.piece_dialog_box,null);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();


        ImageView decreaseButton, increaseButton, closeButton;
        Button saveButton;
        final TextView pieceText = mView.findViewById(R.id.pieceText);


        piece = newPiece;
        pieceText.setText(""+piece);

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



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

}
