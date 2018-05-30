package edu.dartmouth.cs.d_path.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.dartmouth.cs.d_path.Activies.LoginActivity;
import edu.dartmouth.cs.d_path.Adapters.AllCourseAdapter;
import edu.dartmouth.cs.d_path.Adapters.CourseAdapter;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/28/18.
 */

public class AllCoursesFragment extends Fragment {
    public static final String TAG="allCoursesFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Course> courses = new ArrayList<Course>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEntriesRef;

    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        //add child listener to Courses in Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEntriesRef = mFirebaseDatabase.getReference("Courses");
        mEntriesRef.addChildEventListener(new AllCoursesChildEventListener());
        Log.d(TAG, mEntriesRef.toString());
    }

    //child event listener to handle firebase events
    class AllCoursesChildEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "ON CHILD ADDED");
            //get course and put into arraylist
            Course course = dataSnapshot.getValue(Course.class);
            courses.add(course);
            //animaiton for loading courses
            runLayoutAnimation(mRecyclerView);

        }

        @Override
        //when child is updated, change in database
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "ON CHILD CHANGED");


        }

        @Override
        //when child is removed in firebase
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d(TAG, "ON CHILD REMOVED");

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_courses, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set up recyclerView
        mRecyclerView = view.findViewById(R.id.recycler_all_courses);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        
        // specify the adapter
        mAdapter = new AllCourseAdapter(this.getActivity(), courses);
        mRecyclerView.setAdapter(mAdapter);
    }

    //animation for the recycler view
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.course_layout_animation);
        mAdapter.notifyDataSetChanged();

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }
}
