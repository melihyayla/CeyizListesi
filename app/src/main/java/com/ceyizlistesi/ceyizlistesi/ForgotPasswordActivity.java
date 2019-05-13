package com.ceyizlistesi.ceyizlistesi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button resetPasswordButton;
    EditText mailAddresEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mailAddresEditText = findViewById(R.id.username_edit_text);



        resetPasswordButton = findViewById(R.id.reset_password);



        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailAddr = mailAddresEditText.getText().toString();
                Toast.makeText(ForgotPasswordActivity.this, "Mail address: " + mailAddr, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
