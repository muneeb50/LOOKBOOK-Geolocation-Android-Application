package com.htsm.lookbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.htsm.lookbook.Activities.HomeActivity;
import com.htsm.lookbook.Activities.SignUpActivity;
import com.htsm.lookbook.Controllers.UserController;
import com.htsm.lookbook.R;

public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";

    private EditText mEmailInput;
    private EditText mPasswordInput;
    private Button mSignInButton;
    private TextView mSignUpButton;
    private UserController mUserController;

    private AlertDialog mAlertDialog;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserController = new UserController(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signin, container, false);

        mEmailInput = v.findViewById(R.id.id_input_email);
        mPasswordInput = v.findViewById(R.id.id_input_pass);
        mSignInButton = v.findViewById(R.id.id_btn_sign_in);
        mSignUpButton = v.findViewById(R.id.textButton);

        mAlertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Signing In...")
                .setView(new ProgressBar(getActivity()))
                .create();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmailInput.getText().length() > 0 && mPasswordInput.getText().length() > 0) {
                    mAlertDialog.show();
                    mUserController.signInUser(mEmailInput.getText().toString(), mPasswordInput.getText().toString(), new UserController.OnTaskCompletedListener() {
                        @Override
                        public void onTaskSuccessful() {
                            mAlertDialog.dismiss();
                            startActivity(HomeActivity.newIntent(getActivity(), null));
                            getActivity().finish();
                            Log.i(TAG, "Sign In Successfull");
                        }

                        @Override
                        public void onTaskFailed(Exception ex) {
                            mAlertDialog.dismiss();
                            Snackbar.make(SignInFragment.this.getView(), "Account Sign In failed", Toast.LENGTH_LONG).show();
                            Log.wtf(TAG, "Failed to signin." + ex.toString());
                        }
                    });
                } else {
                    Snackbar.make(SignInFragment.this.getView(), "Please fill all fields!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = SignUpActivity.newIntent(getActivity(), false);
                startActivity(i);
            }
        });

        return v;
    }
}
