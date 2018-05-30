package edu.dartmouth.cs.d_path.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import edu.dartmouth.cs.d_path.Activies.LoginActivity;
import edu.dartmouth.cs.d_path.Activies.MainActivity;
import edu.dartmouth.cs.d_path.Adapters.CourseAdapter;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;
import edu.dartmouth.cs.d_path.service.CourseTableService;

/**
 * Created by jameslee on 5/24/18.
 */

public class CoursesFragment extends Fragment {
    public static final String TAG="coursesFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefresh;

    public static ArrayList<Course> courses = new ArrayList<Course>();
    public ArrayList<Course> firstCourses = new ArrayList<>();

    public ArrayList<Course> coursesUpdated = new ArrayList<Course>();
    public ArrayList<Course> firstCoursesUpdated = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEntriesRef;

    private HashMap<String, Course> table = CourseTableService.CourseTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEntriesRef = mFirebaseDatabase.getReference("Users");
        mEntriesRef.child("user_"+ FirebaseAuth.getInstance().getUid()).child("recommendations").addChildEventListener(new RecommendationsChildEventListener());
        Log.d(TAG, mEntriesRef.toString());

    }

    //child event listener to handle firebase events
    class RecommendationsChildEventListener implements ChildEventListener {

        @Override
        //when child is added in firebase
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "ON CHILD ADDED");
            //get course and put into arraylist
            String courseNumber = dataSnapshot.getValue(String.class);
            String courseKey = dataSnapshot.getKey();
            int courseKeyNumber = Integer.parseInt(courseKey);

            Course course = LoginActivity.courseTable.get(courseNumber);
            if (courseKeyNumber < 200){
                if (firstCourses.size()<6) {
                    firstCourses.add(course);
                    mAdapter.notifyDataSetChanged();
                    runLayoutAnimation(mRecyclerView);
                }
            } else {
                courses.add(course);
//            Course course = dataSnapshot.getValue(Course.class);
//            courses.add(course);
            }

        }

        @Override
        //when child is updated, change in database
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "ON CHILD CHANGED");
            String courseNumber = dataSnapshot.getValue(String.class);
            String courseKey = dataSnapshot.getKey();
            int courseKeyNumber = Integer.parseInt(courseKey);

            Course course = LoginActivity.courseTable.get(courseNumber);
            if (courseKeyNumber < 200) {
                if (firstCourses.size() < 12) {
                    firstCourses.add(course);
                } else {
                    courses.remove(0);
                    courses.add(course);
                }
            }
            if (courseKeyNumber == 200){
                for (int i = 0; i < 6; i++){
                    firstCourses.remove(0);
                }
            }
        }

        @Override
        //when child is removed in firebase
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d(TAG, "ON CHILD REMOVED");
//            Course course = dataSnapshot.getValue(Course.class);
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
        return inflater.inflate(R.layout.fragment_courses, container, false);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d(TAG, "onAttach");
        super.onAttachFragment(fragment);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "RESTORED");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mSwipeRefresh = view.findViewById(R.id.swipeRefreshLayout);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                runLayoutAnimation(mRecyclerView);
                mSwipeRefresh.setRefreshing(false);
            }
        });

//
//        if (!firstCoursesUpdated.isEmpty()){
//            firstCourses = firstCoursesUpdated;
//            firstCoursesUpdated.clear();
//        }
//        if (!coursesUpdated.isEmpty()){
//            courses= coursesUpdated;
//            coursesUpdated.clear();
//        }
        // specify an adapter (see also next example)
        mAdapter = new CourseAdapter(this.getActivity(), firstCourses, courses); // change this back to firstcourses
        mRecyclerView.setAdapter(mAdapter);

//        // attach swipe controller to recycler view
//        SwipeController swipeController = new SwipeController();
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(mRecyclerView);


    }
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.course_layout_animation);
        mAdapter.notifyDataSetChanged();
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }
}
