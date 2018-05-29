package edu.dartmouth.cs.d_path.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.dartmouth.cs.d_path.Adapters.SavedCourseAdapter;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;
import edu.dartmouth.cs.d_path.service.CourseTableService;

/**
 * Created by jameslee on 5/24/18.
 */

public class SavedFragment extends Fragment {
    public static final String TAG="savedFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEntriesRef;

    public ArrayList<Course> courses = new ArrayList<Course>();

    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEntriesRef = mFirebaseDatabase.getReference("Users");
        mEntriesRef.child("user_"+ FirebaseAuth.getInstance().getUid()).child("saved").addChildEventListener(new SavedChildEventListener());
        Log.d(TAG, mEntriesRef.toString());
    }
    //child event listener to handle firebase events
    class SavedChildEventListener implements ChildEventListener {

        @Override
        //when child is added in firebase
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "ON CHILD ADDED");
            //get course and put into arraylist
//            Course course = dataSnapshot.getValue(Course.class);
            String courseNumber = dataSnapshot.getValue(String.class);
            Course course = CourseTableService.CourseTable.get(courseNumber);
            courses.add(course);
            mAdapter.notifyItemInserted(courses.size()-1);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView = view.findViewById(R.id.my_saved_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new SavedCourseAdapter(this.getActivity(), courses);
        mRecyclerView.setAdapter(mAdapter);
    }
}
