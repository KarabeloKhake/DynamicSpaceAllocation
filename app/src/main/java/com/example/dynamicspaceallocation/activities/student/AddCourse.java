package com.example.dynamicspaceallocation.activities.student;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.dynamicspaceallocation.R;
import com.example.dynamicspaceallocation.app_utility.AppClass;
import com.example.dynamicspaceallocation.app_utility.CourseAdapter;
import com.example.dynamicspaceallocation.entities.Course;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddCourse extends AppCompatActivity {

    ActionBar actionBar;
    private View mProgressView;
    private View mAddCourseFormView;
    private TextView tvLoad;
    private TextInputEditText etCourseCode, etCourseName, etCourseDescription;
    private TextInputLayout ilCourseCode, ilCourseName, ilCourseDescription;
    TextView tvListCourses;
    RecyclerView recyclerView;
    RecyclerView.Adapter courseAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_course);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("\t\t\t\t\tAdd Course");

        mAddCourseFormView = findViewById(R.id.add_course_form);
        mProgressView = findViewById(R.id.add_course_progress);
        tvLoad = findViewById(R.id.tvLoad);
        etCourseCode = findViewById(R.id.etCourseCode);
        etCourseDescription = findViewById(R.id.etCourseDescription);
        etCourseName = findViewById(R.id.etCourseName);
        ilCourseCode = findViewById(R.id.ilCourseCode);
        ilCourseDescription = findViewById(R.id.ilCourseDescription);
        ilCourseName = findViewById(R.id.ilCourseName);
        tvListCourses = findViewById(R.id.tvListCourses);
        recyclerView = findViewById(R.id.rvListCourses);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager = new LinearLayoutManager(this));

        //
        if(AppClass.courses.size() > 0) {
            //set the where clause
            String sWhereClause = "studentNumber = '" + AppClass.user.getProperty("studentNumber") + "'";
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(sWhereClause);
            queryBuilder.setGroupBy("courseName");

            Backendless.Persistence.of(Course.class).find(queryBuilder, new AsyncCallback<List<Course>>() {
                @Override
                public void handleResponse(List<Course> response) {
                    AppClass.courses = response;
                    //set the adapter
                    courseAdapter = new CourseAdapter(AddCourse.this, AppClass.courses);
                    recyclerView.setAdapter(courseAdapter);
                } //end handleResponse()
                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(AddCourse.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                } //end handleFault()
            });
        } //end if


        etCourseCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateCourseCode(((EditText) v).getText());
                } //end if
            }
        });
        etCourseDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateCourseDes(((EditText) v).getText());
                } //end if
            }
        });
        etCourseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateCourseName(((EditText) v).getText());
                } //end if
            }
        });
    } //end onCreate()

    //Custom Methods
    public void btnAddCourse_onClick(View view) {
        try {
            //
            if(!Objects.requireNonNull(etCourseCode.getText()).toString().isEmpty() && !Objects.requireNonNull(etCourseDescription.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(etCourseName.getText()).toString().isEmpty()) {
                //
                ilCourseCode.setError(null);
                ilCourseDescription.setError(null);
                ilCourseName.setError(null);
            } //end if
            else {
                //
                if(!Objects.requireNonNull(etCourseCode.getText()).toString().isEmpty())
                    ilCourseCode.setError(null);
                else
                    ilCourseCode.setError("Course code required");

                if(!Objects.requireNonNull(etCourseCode.getText()).toString().isEmpty())
                    ilCourseDescription.setError(null);
                else
                    ilCourseDescription.setError("Course description required");

                if(!Objects.requireNonNull(etCourseName.getText()).toString().isEmpty())
                    ilCourseName.setError(null);
                else
                    ilCourseName.setError("Course name required");
            } //end else
        } //end try
        catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } //end catch()
    } //end btnAddCourse_onClick()

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mAddCourseFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mAddCourseFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAddCourseFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

    private void validateCourseCode(Editable courseCode) {
        //check if input text is empty
        if(!TextUtils.isEmpty(courseCode)) {
            ilCourseCode.setError(null);
        } //end if
        else
            ilCourseCode.setError("Course code required");
    } //end validateCourseCode()

    private void validateCourseDes(Editable courseDes) {
        //check if input text is empty
        if(!TextUtils.isEmpty(courseDes)) {
            ilCourseDescription.setError(null);
        } //end if
        else
            ilCourseDescription.setError("Course description required");
    } //end validateCourseDes()

    private void validateCourseName(Editable courseName) {
        //check if input text is empty
        if(!TextUtils.isEmpty(courseName)) {
            ilCourseName.setError(null);
        } //end if
        else
            ilCourseName.setError("Course name required");
    } //end validateCourseName()
} //end class AddCourse
