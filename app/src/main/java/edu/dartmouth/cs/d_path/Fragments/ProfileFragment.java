package edu.dartmouth.cs.d_path.Fragments;

import android.content.Intent;
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

import edu.dartmouth.cs.d_path.Activies.LoginActivity;
import edu.dartmouth.cs.d_path.Model.UserProfile;
import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/24/18.
 */

public class ProfileFragment extends Fragment {
    public static final String TAG="profileFragment";

    TextView mEmail;
    TextView mMajor;
    TextView mReset;
    TextView mSignOut;
    String email;
    String major;


    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);



    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmail = view.findViewById(R.id.profile_email);
        mMajor = view.findViewById(R.id.profile_major);
        mReset = view.findViewById(R.id.reset_preference_button);
        mSignOut = view.findViewById(R.id.sign_out_button);
        FirebaseDatabase.getInstance().getReference("Users").child("user_"+ FirebaseAuth.getInstance().getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChanged");
                email = dataSnapshot.getValue(String.class);
                mEmail.setText(email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Users").child("user_"+ FirebaseAuth.getInstance().getUid()).child("major").addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChanged");
                major = dataSnapshot.getValue(String.class);
                mMajor.setText(major);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }
}
