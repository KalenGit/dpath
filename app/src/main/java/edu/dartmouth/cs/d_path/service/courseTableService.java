package edu.dartmouth.cs.d_path.service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import edu.dartmouth.cs.d_path.Fragments.AllCoursesFragment;
import edu.dartmouth.cs.d_path.Model.Course;

/**
 * Created by jameslee on 5/28/18.
 */


public class courseTableService{

    public static HashMap<String, Course> CourseTable = new HashMap();

    static{
        System.out.println("here");
        FirebaseDatabase
                .getInstance()
                .getReference("Courses")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            Course course = data.getValue(Course.class);
                            CourseTable.put(course.getCourseNumber().replace(".", "-"), course);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
