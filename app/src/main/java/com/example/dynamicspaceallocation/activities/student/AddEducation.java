package com.example.dynamicspaceallocation.activities.student;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.dynamicspaceallocation.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddEducation extends AppCompatActivity {

    ActionBar actionBar;
    private View mProgressView;
    private View mAddEducationFormView;
    private TextView tvLoad;
    AutoCompleteTextView acDepartment, acFaculty, acEducationLevel, acQualification;
    TextInputEditText etAcademicInstitution;
    TextInputLayout ilAcademicInstitution, ilDepartment, ilFaculty, ilEducationLevel, ilQualification;
    private String[] sDepartments, sEducationList, sFaculties, sQualifications;
    private String sDepartment, sEducation, sFaculty, sQualification;
    private ArrayAdapter<String> aDepartment;
    private ArrayAdapter<String> aEducation;
    private ArrayAdapter<String> aFaculty;
    private ArrayAdapter<String> aQualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_education_activity);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("\t\t\tAdd Qualification");

        mAddEducationFormView = findViewById(R.id.add_education_form);
        mProgressView = findViewById(R.id.add_education_progress);
        tvLoad = findViewById(R.id.tvLoad);
        acDepartment = findViewById(R.id.acDepartment);
        acFaculty = findViewById(R.id.acFaculty);
        acEducationLevel = findViewById(R.id.acEducationLevel);
        acQualification = findViewById(R.id.acQualification);
        etAcademicInstitution = findViewById(R.id.etAcademicInstitution);
        ilAcademicInstitution = findViewById(R.id.ilAcademicInstitution);
        ilDepartment = findViewById(R.id.ilDepartment);
        ilFaculty = findViewById(R.id.ilFaculty);
        ilEducationLevel = findViewById(R.id.ilEducationLevel);
        ilQualification = findViewById(R.id.ilQualification);

        faculties();
        educationalLevels();
    } //end onCreate()

    //Custom Methods
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mAddEducationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mAddEducationFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAddEducationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

    private void faculties() {
        final ArrayAdapter<String> aDept;
        sFaculties = getResources().getStringArray(R.array.faculty_array);
        aFaculty = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sFaculties);

        acFaculty.setAdapter(aFaculty);
        acFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItemPosition() == 0)
                    sFaculty = " ";
                else {
                    sFaculty = sFaculties[position];
                } //end else
            } //end onItemSelected()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } //end
        });
    } //end faculties()

    private void educationalLevels() {
        sEducationList = getResources().getStringArray(R.array.educational_levels_array);
        aEducation = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sEducationList);

        acEducationLevel.setAdapter(aEducation);
        acEducationLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItemPosition() == 0)
                    sEducation = " ";
                else
                    sEducation = sEducationList[position];
            } //end onItemSelected()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } //end
        });
    } //end educationalLevels()

    private void validateAcademicInstitution(Editable academicInst) {
        //check if input text is empty
        if(!TextUtils.isEmpty(academicInst))
            ilAcademicInstitution.setError(null);
        else
            ilAcademicInstitution.setError("Academic institution required");
    } //end validateAcademicInstitution()
} //end class AddEducation()
