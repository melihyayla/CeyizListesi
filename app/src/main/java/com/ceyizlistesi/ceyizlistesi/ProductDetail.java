package com.ceyizlistesi.ceyizlistesi;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

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
    private static final String IMAGE_DIRECTORY = "/demonuts";
    TextView pieceTextView,priceTextView,productNameTextView;
    int numberOfImage = 0;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        requestMultiplePermissions();
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

                createCancelDialogBox();


            }
        });

        final Drawable checked = getResources().getDrawable(R.drawable.ic_added);
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

                showPictureDialog();
                /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action cod*/
            }
        });

        addnewPictureLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog();
                /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action cod*/
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
        finish();
        startActivity(intent);

    }

    public void createCancelDialogBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.confirm_dialog_box,null);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();


        Button saveButton = mView.findViewById(R.id.confirm_button);
        Button cancelButton = mView.findViewById(R.id.cancel_button);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                Intent intent = new Intent(ProductDetail.this,Product.class);
                startActivity(intent);
                finish();

            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void showPictureDialog(){
        android.app.AlertDialog.Builder pictureDialog = new android.app.AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        /*switch(requestCode) {
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
        }*/


        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(ProductDetail.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //imageview.setImageBitmap(bitmap);

                    createProduct(picturesLinearLayout,bitmap);
                    numberOfImage++;
                    if(addPictureLinearLayout.getVisibility()==View.VISIBLE){
                        addPictureLinearLayout.setVisibility(View.GONE);
                        picturesScrollView.setVisibility(View.VISIBLE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductDetail.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(ProductDetail.this, "Image Saved!", Toast.LENGTH_SHORT).show();

            createProduct(picturesLinearLayout,thumbnail);
            numberOfImage++;
            if(addPictureLinearLayout.getVisibility()==View.VISIBLE){
                addPictureLinearLayout.setVisibility(View.GONE);
                picturesScrollView.setVisibility(View.VISIBLE);
            }
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

    public void createProduct(final LinearLayout parent, Bitmap bitmapTaken){

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

        //try {
            Bitmap roundedImage =  bitmapTaken;
            Bitmap resized = Bitmap.createScaledBitmap(roundedImage, 80, 120, true);
            resized = roundCorner(resized,10);
            imageView2.setImageBitmap(resized);
            LinearLayout.LayoutParams imageParams2  = new LinearLayout.LayoutParams( pixels80 , pixels120);
            imageParams2.setMargins(1,50,1,1);
            imageView2.setLayoutParams(imageParams2);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}


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


    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }



}
