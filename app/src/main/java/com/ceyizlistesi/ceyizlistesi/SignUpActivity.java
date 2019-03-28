package com.ceyizlistesi.ceyizlistesi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout mailAdressLinearLayout, passwordLinearLayout;
    EditText mailEditText, passwordEditText;
    ImageView eyeActiveImageView;
    Boolean flag = true;
    TextView signInTexView;
    Button button;
    FrameLayout background_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        background_sign_up = findViewById(R.id.background_sign_up);

        background_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(SignUpActivity.this);
            }
        });

        signInTexView = findViewById(R.id.sign_in_text);

        signInTexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        mailAdressLinearLayout = findViewById(R.id.mail_address_linear_layout);
        passwordLinearLayout = findViewById(R.id.password_linear_layout);

        mailEditText = findViewById(R.id.mail_address_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        eyeActiveImageView = findViewById(R.id.eye_active_image_view);


        final Drawable backgroundActive = getResources().getDrawable(R.drawable.linear_layout_bg_active);
        final Drawable backgroundDeactive = getResources().getDrawable(R.drawable.linear_layout_bg);



        mailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailAdressLinearLayout.setBackground(backgroundActive);
                passwordLinearLayout.setBackground(backgroundDeactive);


            }
        });


        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailAdressLinearLayout.setBackground(backgroundDeactive);
                passwordLinearLayout.setBackground(backgroundActive);

            }
        });

        eyeActiveImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(flag){
                    passwordEditText.setTransformationMethod(null);
                    flag=false;
                }

                else{
                    passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                    flag=true;
                }
            }
        });


        button = findViewById(R.id.sign_up_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
