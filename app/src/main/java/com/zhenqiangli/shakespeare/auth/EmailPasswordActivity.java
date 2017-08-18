package com.zhenqiangli.shakespeare.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zhenqiangli.shakespeare.BaseActivity;
import com.zhenqiangli.shakespeare.R;

/**
 * https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/EmailPasswordActivity.java
 */

public class EmailPasswordActivity extends BaseActivity implements OnClickListener{
  public static final String TAG = "EmailPassword";

  private FirebaseAuth auth;
  private EditText emailEditText;
  private EditText passwordEditText;

  public static Intent newIntent(Context context) {
    return new Intent(context, EmailPasswordActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_email_password);

    emailEditText = (EditText) findViewById(R.id.field_email);
    passwordEditText = (EditText) findViewById(R.id.field_password);
    findViewById(R.id.email_sign_in_button).setOnClickListener(this);
    findViewById(R.id.email_create_account_button).setOnClickListener(this);

    auth = FirebaseAuth.getInstance();
  }

  private void createAccount(String email, String password) {
    Log.d(TAG, "createAccount: " + email);
    if (!validateForm()) {
      return;
    }
    
    showProgressDialog();
    
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              Log.d(TAG, "createUserWithEmail:success");
              FirebaseUser user = auth.getCurrentUser();
              
              updateUI(user);
            } else {
              Log.w(TAG, "createUserWithEmail:failure", task.getException());
              Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
              updateUI(null);
            }

            hideProgressDialog();
          }
        });
  }

  private void signIn(String email, String password) {
    Log.d(TAG, "signIn: " + email);
    if (!validateForm()) {
      return;
    }

    showProgressDialog();

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              Log.d(TAG, "signInWithEmail:success");
              FirebaseUser user = auth.getCurrentUser();
              updateUI(user);
            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "signInWithEmail:failure", task.getException());
              Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
              updateUI(null);
            }
          }
        });
  }

  private void signOut() {
    auth.signOut();
    updateUI(null);
  }

  private void sendEmailVerification() {}

  private void showProgressDialog() {}
  private void hideProgressDialog() {}
  private void updateUI(FirebaseUser user) {}
  
  private boolean validateForm() {
    boolean valid = true;
    String email = emailEditText.getText().toString();
    if (TextUtils.isEmpty(email)) {
      emailEditText.setError("Required.");
      valid = false;
    } else {
      emailEditText.setError(null);
    }
    
    String password = passwordEditText.getText().toString();
    if (TextUtils.isEmpty(password)) {
      passwordEditText.setError("Required.");
      valid = false;
    } else {
      passwordEditText.setError(null);
    }
    
    return valid;
  }

  @Override
  public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.email_create_account_button) {
      createAccount(emailEditText.getText().toString(), passwordEditText.getText().toString());
    } else if (i == R.id.email_sign_in_button) {
      signIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
    } else {
      Log.d(TAG, "onClick: unknown");
    }
  }
}
