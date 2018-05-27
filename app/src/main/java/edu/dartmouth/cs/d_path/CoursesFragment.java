package edu.dartmouth.cs.d_path;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jameslee on 5/24/18.
 */

public class CoursesFragment extends Fragment {
    public static final String TAG="coursesFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Course> courses = new ArrayList<Course>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        courses.add(new Course("CS50", "Software Implementation"));
        courses.add(new Course("CS65", "Smartphone Programming"));
        courses.add(new Course("CS10", "Object Oriented Programming"));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);




        // specify an adapter (see also next example)
        mAdapter = new CourseAdapter(this.getActivity(), courses);
        mRecyclerView.setAdapter(mAdapter);

//        // attach swipe controller to recycler view
//        SwipeController swipeController = new SwipeController();
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(mRecyclerView);


    }
}
