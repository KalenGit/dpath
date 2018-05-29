package edu.dartmouth.cs.d_path.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.dartmouth.cs.d_path.Model.UserProfile;
import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/24/18.
 */

public class ProfileFragment extends Fragment {
    public static final String TAG="profileFragment";
    UserProfile currentUser;
    TextView mEmail;
    TextView mMajor;


    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);



    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mEmail = view.findViewById(R.id.profile_email);
//        mMajor = view.findViewById(R.id.profile_major);
//        currentUser = new UserProfile();
//        FirebaseDatabase.getInstance().getReference("Users").child("user_"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener(){
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChanged");
//                currentUser = dataSnapshot.getValue(UserProfile.class);
//                Log.d(TAG, currentUser.getEmail());
//                mEmail.setText(currentUser.getEmail());
//                mMajor.setText(currentUser.getMajor());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
