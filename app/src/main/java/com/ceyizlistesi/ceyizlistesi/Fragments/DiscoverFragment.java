package com.ceyizlistesi.ceyizlistesi.Fragments;

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
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.ceyizlistesi.ceyizlistesi.MainActivity;
import com.ceyizlistesi.ceyizlistesi.Product;
import com.ceyizlistesi.ceyizlistesi.ProductDetail;
import com.ceyizlistesi.ceyizlistesi.R;

public class DiscoverFragment extends Fragment {
    ImageView add_plus_icon;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout connectionLinearLayout,followCountLinearLayout;
    private ScrollView scrollViewFeed;
    private TextView followCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover,container,false);

        add_plus_icon = view.findViewById(R.id.add_icon_center);
        connectionLinearLayout = view.findViewById(R.id.connection_internet_linear_layout);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        scrollViewFeed = view.findViewById(R.id.scroll_view_feed);
        followCount = view.findViewById(R.id.follow_count);
        followCountLinearLayout = view.findViewById(R.id.follow_count_linear_layout);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternetConnection();
                Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
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


    public void checkInternetConnection(){

        if(((MainActivity)getActivity()).isNetworkAvailable()){
            add_plus_icon.setVisibility(View.VISIBLE);
            connectionLinearLayout.setVisibility(View.GONE);
            scrollViewFeed.setEnabled(true);

        }
        else{
            add_plus_icon.setVisibility(View.GONE);
            connectionLinearLayout.setVisibility(View.VISIBLE);
            scrollViewFeed.setEnabled(false);
        }

    }

    public LinearLayout createdLinearLayout(LinearLayout parent){

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (200 * scale + 0.5f);
        int pixels2 = (int) (25 * scale + 0.5f);


        final LinearLayout newProduct = new LinearLayout(getContext());

        LinearLayout.LayoutParams layoutParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);

        layoutParams.setMargins(15,0,15,15);

        newProduct.setLayoutParams(layoutParams);

        //newProduct.setBackground(getResources().getDrawable(R.drawable.product_linear_background));
        newProduct.setOrientation(LinearLayout.HORIZONTAL);

        parent.addView(newProduct);

        return  newProduct;

    }

    public void createImageProduct(LinearLayout parent, final String type, final Bitmap image,
                                   final Bitmap userPicture,final String productName, final String productComment){

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (40 * scale + 0.5f);
        int pixels2 = (int) (25 * scale + 0.5f);


        final FrameLayout newProduct = new FrameLayout(getContext());

        //FrameLayout.LayoutParams layoutParams    = new FrameLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);

        layoutParams.setMargins(7,0,7,0);




        newProduct.setLayoutParams(layoutParams);

        final ImageView imageView = new ImageView(getContext());
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.demofridge);
        //image.setImageBitmap(b);
        imageView.setImageBitmap(b);

        final LinearLayout.LayoutParams imageParams    = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
        imageParams.setMargins(1,1,1,1);
        imageView.setLayoutParams(imageParams);

        newProduct.addView(imageView);


        final ImageView profileImageView = new ImageView(getContext());

        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.profile_picture);
        //image.setImageBitmap(b);

        b2 = roundCorner(b2,50);
        profileImageView.setImageBitmap(b2);
        profileImageView.setBackground(getResources().getDrawable(R.drawable.image_circle_background));


        final LinearLayout.LayoutParams imageParams2  = new LinearLayout.LayoutParams( pixels , pixels);

        imageParams2.setMargins(10,10,10,10);
        profileImageView.setPadding(2,2,2,2);
        profileImageView.setLayoutParams(imageParams);

        newProduct.addView(profileImageView);


        if(type.equals("advert")){
            newProduct.setBackground(getResources().getDrawable(R.drawable.frame_layout_advert_discover));


            final FrameLayout advertLinear = new FrameLayout(getContext());

            final LinearLayout.LayoutParams advertLinearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            advertLinearParams.gravity = Gravity.RIGHT | Gravity.TOP;
            advertLinearParams.setMargins(5,5,5,5);

            TextView tvAdvert = new TextView(getContext());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    pixels2*3, pixels2);

            textParams.setMargins(5,5,5,5);
            tvAdvert.setTypeface(Typeface.create("gordita_bold", Typeface.NORMAL));
            tvAdvert.setTextColor(Color.parseColor("#474167"));
            tvAdvert.setText("SİZE ÖZEL");
            tvAdvert.setBackground(getResources().getDrawable(R.drawable.text_advert_discover));
            tvAdvert.setTextSize(8);
            tvAdvert.setTextColor(ContextCompat.getColor(getContext(), R.color.dusk));
            tvAdvert.setGravity(Gravity.CENTER);
            tvAdvert.setLayoutParams(textParams);

        }
        else
            newProduct.setBackground(getResources().getDrawable(R.drawable.frame_layout_discover));





        final LinearLayout.LayoutParams productsParams    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
