package com.example.dynamicspaceallocation.app_utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dynamicspaceallocation.R;
import com.example.dynamicspaceallocation.entities.Course;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CourseAdapter extends ArrayAdapter<Course> {
    //Data Members
    private Context context;
    private List<Course> courses;

    //Constructor
    public CourseAdapter(Context context, List<Course> list) {
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.courses = list;
    } //end overloaded constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.row_layout, parent, false);

        ImageView ivBook = convertView.findViewById(R.id.ivBook);
        TextView tvCourseCode = convertView.findViewById(R.id.tvCourseCode);
        TextView tvCourseName = convertView.findViewById(R.id.tvCourseName);

        return convertView;
    } //end getView()
} //end class CourseAdapter()
