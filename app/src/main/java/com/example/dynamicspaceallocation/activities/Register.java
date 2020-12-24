package com.example.dynamicspaceallocation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.dynamicspaceallocation.R;
import com.example.dynamicspaceallocation.app_utility.AppClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public class Register extends AppCompatActivity {

    ActionBar actionBar;
    TextInputEditText etEmail, etTelNumber, etPassword, etRetypePassword;
    TextInputLayout ilEmail, ilTelNumber, ilPassword, ilRetypePassword;
    private View mProgressView;
    private View mRegisterFormView;
    private TextView tvLoad;
    private final String USERS = "com.example.dynamicspaceallocation.Users";
    TextView tvUserCode, tvUserType;
    String sFirstName, sLastName, sCity, sHomeAddress, sIdNumber, sProvince, sGender, sRace, sUserCode, sUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("\t\t\tRegister User");

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        tvLoad = findViewById(R.id.tvLoad);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRetypePassword = findViewById(R.id.etRetypePassword);
        etTelNumber = findViewById(R.id.etTelNumber);
        ilEmail = findViewById(R.id.ilEmail);
        ilPassword = findViewById(R.id.ilPassword);
        ilRetypePassword = findViewById(R.id.ilRetypePassword);
        ilTelNumber = findViewById(R.id.ilTelNumber);
        tvUserCode = findViewById(R.id.tvUserCode);
        tvUserType = findViewById(R.id.tvUserType);

        //get the user info from the personal details page
        sCity = getIntent().getStringExtra("city");
        sFirstName = getIntent().getStringExtra("firstName");
        sLastName = getIntent().getStringExtra("lastName");
        sIdNumber = getIntent().getStringExtra("idNumber");
        sGender = getIntent().getStringExtra("gender");
        sRace = getIntent().getStringExtra("race");
        sHomeAddress = getIntent().getStringExtra("homeAddress");
        sProvince = getIntent().getStringExtra("province");
        sUserCode = getIntent().getStringExtra("userCode");

        if(sUserCode.length() == 5)
            sUserType = "Lecturer";
        else if(sUserCode.length() == 9)
            sUserType = "Student";

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateEmailAddress(((EditText) v).getText());
                } //end if
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validatePassword(((EditText) v).getText());
                } //end if
            }
        });
        etRetypePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateRetypePassword(((EditText) v).getText());
                } //end if
            }
        });
        etTelNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateTelNumber(((EditText) v).getText());
                } //end if
            }
        });
    } //end onCreate()

    //Custom Methods
    public void btnRegister_onClick(View view) {
        /*
            purpose of this is to register a new user, either a lecturer or a student
        */
        SharedPreferences.Editor editor;
        String sPassword, sRetype, sTelNumber;

        try {
            editor = getSharedPreferences(USERS, MODE_PRIVATE).edit();

            //test if all text inputs are empty
            if(!Objects.requireNonNull(etEmail.getText()).toString().isEmpty() && !Objects.requireNonNull(etPassword.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(etRetypePassword.getText()).toString().isEmpty() && !Objects.requireNonNull(etTelNumber.getText()).toString().isEmpty()) {
                //validate email address
                if(AppClass.isEmailValid(etEmail.getText().toString())) {
                    ilEmail.setError(null);

                    sTelNumber = etTelNumber.getText().toString();
                    //validate telephone number
                    if(sTelNumber.length() == 10) {
                        if(AppClass.isPhoneNumberValid(sTelNumber)) {
                            ilTelNumber.setError(null);

                            sPassword = etPassword.getText().toString();
                            sRetype = etRetypePassword.getText().toString();
                            //validate passwords
                            if(!AppClass.isPasswordOk(sPassword))
                                ilPassword.setError("Password length too short");
                            if(!AppClass.isPasswordOk(sRetype))
                                ilRetypePassword.setError("Password length too short");
                            if(sPassword.matches(sRetype)) {
                                ilPassword.setError(null);
                                ilRetypePassword.setError(null);

                                //save user info to a file
                                editor.putString("firstName", sFirstName);
                                editor.putString("lastName", sLastName);
                                editor.putString("idNumber", sIdNumber);
                                editor.putString("city", sCity);
                                editor.putString("homeAddress", sHomeAddress);
                                editor.putString("emailAddress", etEmail.getText().toString());
                                editor.putString("telNumber", sTelNumber);
                                editor.putString("password", sRetype);
                                if(sUserType.equals("Lecturer"))
                                    editor.putString("staffNumber", sUserCode);
                                else
                                    editor.putString("studentNumber", sUserCode);
                                editor.putString("userType", sUserType);
                                editor.apply();

                                AppClass.user.setEmail(etEmail.getText().toString());
                                AppClass.user.setProperty("firstName", sFirstName);
                                AppClass.user.setProperty("lastName", sFirstName);
                                AppClass.user.setProperty("idNumber", sFirstName);
                                AppClass.user.setProperty("city", sFirstName);
                                AppClass.user.setProperty("homeAddress", sFirstName);
                                AppClass.user.setProperty("province", sFirstName);
                                AppClass.user.setPassword(sRetype);
                                if(sUserType.equals("Lecturer"))
                                    AppClass.user.setProperty("staffNumber", sUserCode);
                                else
                                    AppClass.user.setProperty("studentNumber", sUserCode);
                                AppClass.user.setProperty("userType", sFirstName);

                                //register new user
                                showProgress(true);
                                if(sUserType.equals("Lecturer"))
                                    tvLoad.setText(R.string.text_registering_lecturer);
                                else
                                    tvLoad.setText(R.string.text_registering_student);
                                Backendless.UserService.register(AppClass.user, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        showProgress(false);

                                        if(response.getProperty("userType").equals("Lecturer"))
                                            Toast.makeText(Register.this, "Lecturer " + response.getProperty("lastName") + " " + response.getProperty("firstName") + " successfully registered.", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(Register.this, "Student  " + response.getProperty("lastName") + " " + response.getProperty("firstName") + " successfully registered.", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    } //end handleResponse()
                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        showProgress(false);
                                        Toast.makeText(Register.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                    } //end handleFault()
                                });
                            } //end if
                        } //end if
                        else
                            ilTelNumber.setError("Tel. number not in the right format");
                    } //end if
                    else
                        ilTelNumber.setError("Tel. number must be 10 characters long");
                } //end if
                else
                    ilEmail.setError("Email address not valid");
            } //end if
            else {
                //test if any single text input is empty
                if(!etEmail.getText().toString().isEmpty())
                    ilEmail.setError(null);
                else
                    ilEmail.setError("Email address required");

                if(!Objects.requireNonNull(etPassword.getText()).toString().isEmpty())
                    ilPassword.setError(null);
                else
                    ilPassword.setError("Password required");

                if(!Objects.requireNonNull(etRetypePassword.getText()).toString().isEmpty())
                    ilRetypePassword.setError(null);
                else
                    ilRetypePassword.setError("Retype password required");

                if(!Objects.requireNonNull(etTelNumber.getText()).toString().isEmpty())
                    ilTelNumber.setError(null);
                else
                    ilTelNumber.setError("Tel. number required");
            } //end else
        } //end try
        catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } //end catch()
    } //end btnRegister()

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
        tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
        tvLoad.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    } //end showProgress()

    public void validateEmailAddress(Editable email) {
        //check if input text is empty
        if(!TextUtils.isEmpty(email)) {
            //check if the email address entered is a valid email address
            if(AppClass.isEmailValid(Objects.requireNonNull(etEmail.getText()).toString()))
                ilEmail.setError(null);
            else
                ilEmail.setError("Email address not valid");
        }
        else
            ilEmail.setError("Email address required");
    } //end validateEmail()

    public void validatePassword(Editable password) {
        //check if input text is empty
        if(!TextUtils.isEmpty(password)) {
            //check if the password entered is a valid password
            if(AppClass.isPasswordOk(Objects.requireNonNull(etPassword.getText()).toString()))
                ilPassword.setError(null);
            else
                ilPassword.setError("Password length is too short");
        } //end if
        else
            ilPassword.setError("Password required");
    } //end validatePassword()

    public void validateRetypePassword(Editable retypePassword) {
        //check if input text is empty
        if(!TextUtils.isEmpty(retypePassword)) {
            //check if the password entered is a valid password
            if(AppClass.isPasswordOk(Objects.requireNonNull(etRetypePassword.getText()).toString()))
                ilRetypePassword.setError(null);
            else
                ilRetypePassword.setError("Password length is too short");
        } //end if
        else
            ilRetypePassword.setError("Retype password required");
    } //end validatePassword()

    public void validateTelNumber(Editable telNumber) {
        //check if input text is empty
        if(!TextUtils.isEmpty(telNumber)) {
            //check if the tel number entered is a tel number
            if(Objects.requireNonNull(etTelNumber.getText()).toString().length() == 10) {
                if (AppClass.isPhoneNumberValid(Objects.requireNonNull(etTelNumber.getText()).toString()))
                    ilTelNumber.setError(null);
                else
                    ilTelNumber.setError("Tel. number not in the right format");
            } //end if
            else
                ilTelNumber.setError("Tel. number must be 10 characters long");
        } //end if
        else
            ilTelNumber.setError("Tel. number required");
    } //end validateTelNumber()
} //end class Register
