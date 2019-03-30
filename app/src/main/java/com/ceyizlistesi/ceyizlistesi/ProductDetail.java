package com.ceyizlistesi.ceyizlistesi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

public class ProductDetail extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ImageView productAddButton, cancelButton;
    EditText informationEditText;
    Switch privacySwitch;
    Boolean checkedFlag = false;
    LinearLayout addPictureLinearLayout,addnewPictureLinearLayout, pieceLinearLayout, priceLinearLayout, switchLayout, parentLayout,
            rowLinearLayout,priceRow,pieceRow,picturesLinearLayout;
    HorizontalScrollView picturesScrollView;
    int piece, price;
    String productName;
    TextView pieceTextView,priceTextView,productNameTextView;
    int numberOfImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        price =  getIntent().getIntExtra("price", 0);
        piece = getIntent().getIntExtra("piece", 0);
        productName = getIntent().getStringExtra("productName");

        cancelButton = findViewById(R.id.cancel_button);
        productAddButton = findViewById(R.id.product_add_button);
        informationEditText = findViewById(R.id.product_information);
        privacySwitch = findViewById(R.id.switch_private);
        addPictureLinearLayout = findViewById(R.id.add_picture_linear_layout);
        picturesScrollView = findViewById(R.id.pictures_scrollview);
        addnewPictureLinearLayout = findViewById(R.id.add_new_picture_linear_layout);
        pieceLinearLayout = findViewById(R.id.piece_linear_layout);
        priceLinearLayout = findViewById(R.id.price_linear_layout);
        switchLayout = findViewById(R.id.switch_layout_user);
        productNameTextView = findViewById(R.id.product_name);
        parentLayout = findViewById(R.id.product_detail_parent);
        rowLinearLayout = findViewById(R.id.rows_linear_layout);
        priceRow = findViewById(R.id.price_row);
        pieceRow = findViewById(R.id.piece_row);
        pieceTextView = findViewById(R.id.piece_textView);
        priceTextView = findViewById(R.id.price_textView);
        picturesLinearLayout = findViewById(R.id.pictures_linear_layout);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(ProductDetail.this);
            }
        });

        picturesScrollView.setVisibility(View.GONE);


        if(piece==0 && price == 0){
            pieceRow.setVisibility(View.GONE);
            rowLinearLayout.setVisibility(View.GONE);
            priceRow.setVisibility(View.GONE);
        }
        else if(price!=0 && piece ==0){
            pieceRow.setVisibility(View.GONE);
            priceLinearLayout.setVisibility(View.GONE);
            priceTextView.setText(String.valueOf(price+" ₺"));
        }
        else if (price==0 && piece !=0){
            priceRow.setVisibility(View.GONE);
            pieceLinearLayout.setVisibility(View.GONE);
            pieceTextView.setText(String.valueOf(piece+" adet"));
        }
        else{
            priceRow.setVisibility(View.VISIBLE);
            pieceLinearLayout.setVisibility(View.GONE);
            pieceRow.setVisibility(View.VISIBLE);
            priceLinearLayout.setVisibility(View.GONE);
            priceTextView.setText(String.valueOf(price+" ₺"));
            pieceTextView.setText(String.valueOf(piece+" adet"));
        }

        productNameTextView.setText(productName);


        privacySwitch.setOnCheckedChangeListener(this);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this,Product.class);
                startActivity(intent);
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

        addnewPictureLinearLayout.setOnClickListener(new View.OnClickListener() {
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
                createPieceDialogBox(piece);
            }
        });

        priceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPriceDialogBox(price);
            }
        });

        priceRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPriceDialogBox(price);
            }
        });

        pieceRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPieceDialogBox(piece);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductDetail.this,Product.class);
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //demoView.setImageURI(selectedImage);
                    createProduct(picturesLinearLayout,selectedImage);
                    numberOfImage++;
                    if(addPictureLinearLayout.getVisibility()==View.VISIBLE){
                        addPictureLinearLayout.setVisibility(View.GONE);
                        picturesScrollView.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    createProduct(picturesLinearLayout,selectedImage);

                    numberOfImage++;
                    if(addPictureLinearLayout.getVisibility()==View.VISIBLE){
                        addPictureLinearLayout.setVisibility(View.GONE);
                        picturesScrollView.setVisibility(View.VISIBLE);
                    }

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


        ImageView decreaseButton, increaseButton, closeButton, trashButton;
        Button saveButton;
        final TextView pieceText = mView.findViewById(R.id.pieceText);


        piece = newPiece;
        pieceText.setText(""+piece);

        saveButton = mView.findViewById(R.id.save_button);
        decreaseButton = mView.findViewById(R.id.decrease_button);
        increaseButton = mView.findViewById(R.id.increase_button);
        trashButton = mView.findViewById(R.id.piece_trash_image_view);
        closeButton = mView.findViewById(R.id.close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieceLinearLayout.setVisibility(View.VISIBLE);
                pieceRow.setVisibility(View.GONE);
                piece=0;
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
                rowLinearLayout.setVisibility(View.VISIBLE);
                pieceRow.setVisibility(View.VISIBLE);
                pieceLinearLayout.setVisibility(View.GONE);
                pieceTextView.setText(String.valueOf(piece+" adet"));
                dialog.dismiss();
            }
        });

        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void createPriceDialogBox(int newPrice){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.price_dialog_box,null);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();


        ImageView trashButton, closeButton;
        Button saveButton;

        final EditText priceEditText = mView.findViewById(R.id.price_edit_text);

        String str = String.valueOf(newPrice);
        priceEditText.setText(str);


        saveButton = mView.findViewById(R.id.save_button);
        closeButton = mView.findViewById(R.id.close_button);
        trashButton  = mView.findViewById(R.id.price_trash_image_view);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!priceEditText.getText().toString().isEmpty())
                price =  Integer.parseInt(priceEditText.getText().toString());

                rowLinearLayout.setVisibility(View.VISIBLE);
                priceRow.setVisibility(View.VISIBLE);
                priceTextView.setText(String.valueOf(price+" ₺"));
                priceLinearLayout.setVisibility(View.GONE);
                dialog.dismiss();

            }
        });

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = 0;
                priceRow.setVisibility(View.GONE);
                priceLinearLayout.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        Log.i("Price tag"," " + price);


        Log.i("Piece tag"," " + piece);

        dialog.show();



        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void createProduct(final LinearLayout parent, Uri newImage){

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (30 * scale + 0.5f);
        int pixels60 = (int) (60 * scale + 0.5f);
        int pixels80 = (int) (80 * scale + 0.5f);
        int pixels120 = (int) (120 * scale + 0.5f);

        final FrameLayout newImageFrameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(5,0,20,0);
        newImageFrameLayout.setLayoutParams(layoutParams);




        final ImageView imageView = new ImageView(getApplicationContext());
        Drawable photoTrash = getResources().getDrawable(R.drawable.ic_trash_photo);
        imageView.setImageDrawable(photoTrash);
        LinearLayout.LayoutParams imageParams  = new LinearLayout.LayoutParams( pixels , pixels);
        imageParams.setMargins(pixels60,0,0,0);
        imageParams.gravity = Gravity.RIGHT;
        imageParams.weight=1.0f;
        imageView.setLayoutParams(imageParams);

        final ImageView imageView2 = new ImageView(getApplicationContext());

        try {
            Bitmap roundedImage =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), newImage);
            Bitmap resized = Bitmap.createScaledBitmap(roundedImage, 80, 120, true);
            resized = roundCorner(resized,10);
            imageView2.setImageBitmap(resized);
            LinearLayout.LayoutParams imageParams2  = new LinearLayout.LayoutParams( pixels80 , pixels120);
            imageParams2.setMargins(1,50,1,1);
            imageView2.setLayoutParams(imageParams2);
        } catch (IOException e) {
            e.printStackTrace();
        }


        newImageFrameLayout.addView(imageView2);
        newImageFrameLayout.addView(imageView);

        parent.addView(newImageFrameLayout);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfImage--;
                parent.removeView(newImageFrameLayout);
                if (numberOfImage==0){
                    addPictureLinearLayout.setVisibility(View.VISIBLE);
                    picturesScrollView.setVisibility(View.GONE);
                }
            }
        });
    }

    public static Bitmap roundCorner(Bitmap src, float round) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // set canvas for painting
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        // config paint
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        // config rectangle for embedding
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        // draw rect to canvas
        canvas.drawRoundRect(rectF, round, round, paint);

        // create Xfer mode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // draw source image to canvas
        canvas.drawBitmap(src, rect, rect, paint);

        // return final image
        return result;
    }
}
