package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageButton homeImageButton = findViewById(R.id.homeImageButton);

        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, HomeActivity.class);

                startActivity(intent);

            }
        });

        TextView aboutVersion = findViewById(R.id.aboutTextView);
        Intent srcIntent = getIntent();

        aboutVersion.setText(BuildConfig.VERSION_NAME +"/"+ srcIntent.getStringExtra("aString"));

        TextView description = findViewById(R.id.descriptionTextView);
        Intent srcDescription = getIntent();

        description.setText(srcDescription.getStringExtra("description"));

    }


}