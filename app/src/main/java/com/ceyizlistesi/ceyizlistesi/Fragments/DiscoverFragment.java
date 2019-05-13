package com.ceyizlistesi.ceyizlistesi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceyizlistesi.ceyizlistesi.Helper.CustomScrollView;
import com.ceyizlistesi.ceyizlistesi.MainActivity;
import com.ceyizlistesi.ceyizlistesi.Product;
import com.ceyizlistesi.ceyizlistesi.ProductDetail;
import com.ceyizlistesi.ceyizlistesi.R;

import java.util.LinkedList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    ImageView plusIcon;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout connectionLinearLayout,followCountLinearLayout, parentListLinearLayout, noInternetLayout, unicornLayout;
    private com.ceyizlistesi.ceyizlistesi.Helper.CustomScrollView scrollViewFeed;
    private TextView followCount;
    public String deneme = "Deneme";
    ConnectivityManager manager;
    NetworkInfo networkInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover,container,false);

        plusIcon = view.findViewById(R.id.share_plus_button);
        plusIcon.setVisibility(View.VISIBLE);


        connectionLinearLayout = view.findViewById(R.id.connection_internet_linear_layout);
        parentListLinearLayout = view.findViewById(R.id.parent_list_linear_layout);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        noInternetLayout = view.findViewById(R.id.no_internet__linear_layout);
        unicornLayout = view.findViewById(R.id.unicorn_linear_layout);
        scrollViewFeed = view.findViewById(R.id.scroll_view_feed);
        followCount = view.findViewById(R.id.follow_count);
        followCountLinearLayout = view.findViewById(R.id.follow_count_linear_layout);
        manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(), "Yenilendi", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkInternetConnection();
                        mSwipeRefreshLayout.setRefreshing(false);
                        followCountLinearLayout.setVisibility(View.GONE);
                        followCount.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });





        checkInternetConnection();


        return view;
    }

    public void generateProducts(){

        if(parentListLinearLayout.getChildCount()>0)
            parentListLinearLayout.removeAllViews();
        LinkedList<LinearLayout> linearLayouts = new LinkedList<>();
        for(int i=0; i<6; i++){
            if(i%2==0){
                linearLayouts.add(createdLinearLayout(parentListLinearLayout));
            }

            createImageProduct(linearLayouts.get(i/2),"normal","Urun"+i,"Loop Denemesi");
        }

        LinearLayout demo = createdLinearLayout(parentListLinearLayout);

        //LinearLayout demo2 = createdLinearLayout(parentListLinearLayout);


        createImageProduct(demo, "advert", "Deneme", "Denedim oluyor");

        createImageProduct(demo, "empty", "Deneme", "Denedim olmuyor UZUUUUUUZZZZZZZZZN");
    }

    public void checkInternetConnection(){

        if(isNetworkAvailable()){
            plusIcon.setVisibility(View.VISIBLE);
            generateProducts();
            connectionLinearLayout.setVisibility(View.GONE);
            scrollViewFeed.setEnableScrolling(true);
            noInternetLayout.setVisibility(View.GONE);
            parentListLinearLayout.setVisibility(View.VISIBLE);
            unicornLayout.setVisibility(View.VISIBLE);



        }
        else{
            plusIcon.setVisibility(View.GONE);
            connectionLinearLayout.setVisibility(View.VISIBLE);
            scrollViewFeed.setEnableScrolling(false);
            noInternetLayout.setVisibility(View.VISIBLE);
            unicornLayout.setVisibility(View.GONE);
            parentListLinearLayout.setVisibility(View.GONE);


        }

    }

    public LinearLayout createdLinearLayout(LinearLayout parent){

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (220 * scale + 0.5f);

        final LinearLayout newProduct = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);
        layoutParams.setMargins(20,0,20,20);
        newProduct.setLayoutParams(layoutParams);
        newProduct.setOrientation(LinearLayout.HORIZONTAL);
        parent.addView(newProduct);

        return  newProduct;

    }

    public void createImageProduct(LinearLayout parent, final String type, final String productName, final String productComment){

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (40 * scale + 0.5f);
        int pixels2 = (int) (25 * scale + 0.5f);



        final FrameLayout newProduct = new FrameLayout(getContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        layoutParams.setMargins(20,0,20,0);
        newProduct.setLayoutParams(layoutParams);

        final ImageView imageView = new ImageView(getContext());
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.fridge);

        Bitmap resizedMain = Bitmap.createScaledBitmap(b, 180, 220, true);
        resizedMain = roundCorner(resizedMain,10f);
        imageView.setImageBitmap(resizedMain);

        final LinearLayout.LayoutParams imageParams    = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
        imageParams.setMargins(5,5,5,5);
        imageView.setLayoutParams(imageParams);

        newProduct.addView(imageView);


        final ImageView profileImageView = new ImageView(getContext());
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.profile_picture);
        Bitmap resizedProfile = Bitmap.createScaledBitmap(b2, 50, 50, true);
        resizedProfile = roundCorner(resizedProfile,500f);
        profileImageView.setImageBitmap(resizedProfile);
        profileImageView.setBackground(getResources().getDrawable(R.drawable.image_circle_background));
        final LinearLayout.LayoutParams imageParams2  = new LinearLayout.LayoutParams( pixels , pixels);
        imageParams2.setMargins(10,20,10,10);
        profileImageView.setPadding(2,2,2,2);
        profileImageView.setLayoutParams(imageParams2);
        newProduct.addView(profileImageView);


        Typeface gorditaBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/gordita_bold.otf");
        Typeface gorditaMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/gordita_medium.otf");

        if(type.equals("advert")){
            newProduct.setBackground(getResources().getDrawable(R.drawable.frame_layout_advert_discover));


            final LinearLayout advertLinear = new LinearLayout(getContext());
            final FrameLayout.LayoutParams advertLinearParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            advertLinear.setGravity(Gravity.RIGHT);


            advertLinearParams.setMargins(7,7,7,7);
            advertLinearParams.gravity=Gravity.RIGHT;
            TextView tvAdvert = new TextView(getContext());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    pixels2*3, pixels2);

            textParams.setMargins(5,5,5,5);
            tvAdvert.setTypeface(gorditaBold);
            tvAdvert.setText("SİZE ÖZEL");
            advertLinear.setLayoutParams(advertLinearParams);
            tvAdvert.setBackground(getResources().getDrawable(R.drawable.text_advert_discover));
            tvAdvert.setTextSize(8);
            tvAdvert.setTextColor(ContextCompat.getColor(getContext(), R.color.dusk));
            tvAdvert.setGravity(Gravity.CENTER);
            tvAdvert.setLayoutParams(textParams);

            advertLinear.addView(tvAdvert);

            newProduct.addView(advertLinear);

        }
        else if(type.equals("empty")){

            newProduct.setVisibility(View.INVISIBLE);
            parent.addView(newProduct);
            return ;
        }





        final LinearLayout commentLinear = new LinearLayout(getContext());
        final FrameLayout.LayoutParams commentLinearParams  = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
               ViewGroup.LayoutParams.WRAP_CONTENT);


        commentLinearParams.setMargins(5,5,5,5);
        commentLinearParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        commentLinear.setOrientation(LinearLayout.VERTICAL);
        commentLinear.setLayoutParams(commentLinearParams);
        commentLinear.setGravity(Gravity.BOTTOM);


        TextView productNameTextView = new TextView(getContext());
        LinearLayout.LayoutParams productNameTextParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        productNameTextParams.setMargins(5,5,5,5);
        productNameTextParams.gravity = Gravity.CENTER;
        productNameTextView.setTypeface(gorditaBold);
        productNameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        productNameTextView.setLayoutParams(productNameTextParams);
        productNameTextView.setTextSize(12);
        productNameTextView.setMaxLines(1);
        productNameTextView.setText("#"+productName);

        TextView productCommentTextView = new TextView(getContext());
        LinearLayout.LayoutParams productCommetTextParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        productCommetTextParams.setMargins(5,5,5,5);
        productCommetTextParams.gravity = Gravity.CENTER;
        productCommentTextView.setTypeface(gorditaMedium);
        productCommentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        productCommentTextView.setLayoutParams(productCommetTextParams);
        productCommentTextView.setTextSize(12);
        productCommentTextView.setMaxLines(1);
        productCommentTextView.setEllipsize(TextUtils.TruncateAt.END);
        productCommentTextView.setText(productComment);

        commentLinear.addView(productNameTextView);
        commentLinear.addView(productCommentTextView);


        newProduct.addView(commentLinear);

        parent.addView(newProduct);
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

    public boolean isNetworkAvailable() {

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }



}
