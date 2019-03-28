package com.ceyizlistesi.ceyizlistesi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    LinearLayout usernameLinearLayout,nameLinearLayout,surnameLinearLayout, mailaddressLinearLayout, passwordLinearLayout;
    EditText usernameEditText, nameEditText,surnameEditText, mailaddressEditText, passwordEditText;
    TextView signUpTextView;
    ImageView eyeActiveImageView;
    Boolean flag=true;
    FrameLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Drawable backgroundActive = getResources().getDrawable(R.drawable.linear_layout_bg_active);
        final Drawable backgroundDeactive = getResources().getDrawable(R.drawable.linear_layout_bg);

        usernameLinearLayout = findViewById(R.id.username_linear_layout);
        nameLinearLayout = findViewById(R.id.name_linear_layout);
        surnameLinearLayout = findViewById(R.id.surname_linear_layout);
        mailaddressLinearLayout = findViewById(R.id.mail_address_linear_layout);
        passwordLinearLayout = findViewById(R.id.password_linear_layout);
        background = findViewById(R.id.background_sign_in);

        usernameEditText = findViewById(R.id.username_edit_text);
        nameEditText = findViewById(R.id.name_edit_text);
        surnameEditText = findViewById(R.id.surname_edit_text);
        mailaddressEditText = findViewById(R.id.mail_address_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        signUpTextView = findViewById(R.id.sign_up_text);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        eyeActiveImageView = findViewById(R.id.eye_active_image_view);

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

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(SignInActivity.this);
            }
        });

        mailaddressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailaddressLinearLayout.setBackground(backgroundActive);
                passwordLinearLayout.setBackground(backgroundDeactive);
                nameLinearLayout.setBackground(backgroundDeactive);
                surnameLinearLayout.setBackground(backgroundDeactive);
                usernameLinearLayout.setBackground(backgroundDeactive);

            }
        });

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailaddressLinearLayout.setBackground(backgroundDeactive);
                passwordLinearLayout.setBackground(backgroundDeactive);
                nameLinearLayout.setBackground(backgroundDeactive);
                surnameLinearLayout.setBackground(backgroundDeactive);
                usernameLinearLayout.setBackground(backgroundActive);
            }
        });


        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailaddressLinearLayout.setBackground(backgroundDeactive);
                passwordLinearLayout.setBackground(backgroundDeactive);
                nameLinearLayout.setBackground(backgroundActive);
                surnameLinearLayout.setBackground(backgroundDeactive);
                usernameLinearLayout.setBackground(backgroundDeactive);
            }
        });


        surnameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailaddressLinearLayout.setBackground(backgroundDeactive);
                passwordLinearLayout.setBackground(backgroundDeactive);
                nameLinearLayout.setBackground(backgroundDeactive);
                surnameLinearLayout.setBackground(backgroundActive);
                usernameLinearLayout.setBackground(backgroundDeactive);
            }
        });


        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mailaddressLinearLayout.setBackground(backgroundDeactive);
                passwordLinearLayout.setBackground(backgroundActive);
                nameLinearLayout.setBackground(backgroundDeactive);
                surnameLinearLayout.setBackground(backgroundDeactive);
                usernameLinearLayout.setBackground(backgroundDeactive);
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
