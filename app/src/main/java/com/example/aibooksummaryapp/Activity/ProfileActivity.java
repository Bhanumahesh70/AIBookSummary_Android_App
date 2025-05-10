package com.example.aibooksummaryapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aibooksummaryapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class ProfileActivity extends BaseNavActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private TextView userNameText, userEmailText;
    private Button logoutBtn;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile);
        // Inflate Saved Books layout inside the container_frame
        View profileView = getLayoutInflater().inflate(R.layout.activity_profile, frameLayout, true);

        userNameText = findViewById(R.id.usernameText);
        userEmailText = findViewById(R.id.emailText);
        logoutBtn = findViewById(R.id.logoutButton);



        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            userNameText.setText(account.getDisplayName());
            userEmailText.setText(account.getEmail());
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logoutBtn.setOnClickListener(v -> {
            mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            });
        });
    }
}

