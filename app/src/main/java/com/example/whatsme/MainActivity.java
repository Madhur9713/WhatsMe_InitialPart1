package com.example.whatsme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText mPhoneNumber, mCode;
    private Button mSend;

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to check if the user is already logged in
        userIsLoggedIn();

        initialiseAllViews();

        FirebaseApp.initializeApp(this);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mVerificationId!=null){
                    verifyPhoneNumberWithCode();
                }
                else
                    startPhoneNumberVerification();
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.i("th", e.getMessage());

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Log.e("TAG", "onCodeSent: s - " + mVerificationId + " : t - " + forceResendingToken);

                mVerificationId = s;
                mSend.setText("Verify Code");

            }
        };
    }




    // This function will initialise the views
    private void initialiseAllViews() {
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mCode = findViewById(R.id.code);

        mSend = findViewById(R.id.send);
    }

    // This function is used for verification of phoneNumber
    private void startPhoneNumberVerification() {
        Log.i("th", mPhoneNumber.getText().toString());
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

    }

    //
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userIsLoggedIn();
                        }

                    }
                });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
            finish();
            return;
        }
    }

    // to verify phone number with code and make phone auth credentials
    private void verifyPhoneNumberWithCode(){
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, mCode.getText().toString());
        signInWithPhoneAuthCredential(phoneAuthCredential);

    }
}
