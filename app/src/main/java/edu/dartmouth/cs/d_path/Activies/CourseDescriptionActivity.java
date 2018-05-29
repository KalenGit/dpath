package edu.dartmouth.cs.d_path.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/27/18.
 */

public class CourseDescriptionActivity extends AppCompatActivity {
    public static String TAG ="CourseDescription";
    TextView mCourseTitle;
    TextView mCourseDescription;
    TextView mCourseDescriptionHeader;
    TextView mCourseInstructors;
    TextView mCourseInstructorsHeader;
    TextView mCourseDistributives;
    TextView mCourseDistributivesHeader;
    TextView mCourseOffered;
    TextView mCourseOfferedHeader;

    String courseNumber;
    String courseTitle;
    String courseAcronym;
    String courseDescription;
    String courseInstructors;
    String courseDistributives;
    String courseOffered;
    RatingBar ratingBar;
    Button btRate;
    long rating;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);

        //hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        courseNumber = intent.getStringExtra("course_number");
        courseTitle = intent.getStringExtra("course_title");
        courseAcronym = courseNumber.substring(0, 4);
        courseDescription= intent.getStringExtra("course_description");
        courseInstructors = intent.getStringExtra("course_instructors");
        courseDistributives = intent.getStringExtra("course_distributives");
        courseOffered = intent.getStringExtra("course_offered");

        Log.d(TAG, "course Instructors "+ courseInstructors);

        FirebaseDatabase.getInstance().getReference("Users").child("user_"+ FirebaseAuth.getInstance().getUid())
             .child("ratings").child(courseNumber.replace(".", "-")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildAdded");
                rating = dataSnapshot.getValue(Long.class);
                Log.d(TAG, Long.toString(rating));
                ratingBar.setRating(rating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCourseTitle = findViewById(R.id.course_title);
        mCourseDescription = findViewById(R.id.course_description);
        mCourseDescriptionHeader = findViewById(R.id.course_description_header);
        mCourseInstructors = findViewById(R.id.course_instructors);
        mCourseInstructorsHeader = findViewById(R.id.course_instructors_header);
        mCourseDistributives = findViewById(R.id.course_distributives);
        mCourseDistributivesHeader = findViewById(R.id.course_distributives_header);
        mCourseOffered = findViewById(R.id.course_offered);
        mCourseOfferedHeader = findViewById(R.id.course_offered_header);
        ratingBar = findViewById(R.id.rating);
        btRate = findViewById(R.id.rate_button);

        mCourseTitle.setText(courseTitle);
        mCourseDescription.setText(courseDescription);
        mCourseInstructors.setText(courseInstructors);
        mCourseDistributives.setText(courseDistributives);
        mCourseOffered.setText(courseOffered);

        changeColors(courseAcronym);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = Math.round(v);

            }
        });
    }
    public void onRate(View v){
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child("user_"+ FirebaseAuth.getInstance().getUid()).child("ratings").child(courseNumber).setValue(rating);
    }

    public void changeColors(String courseAcron){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (courseAcron.equals("COSC")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_header_blue));
                mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));
                mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));
                mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));
                mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));

            } else {
                mCourseTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_header_blue));
                mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));
                mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));
                mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));
                mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_blue));

            }

            mCourseTitle.setTextColor(ContextCompat.getColor(this, R.color.darkBlue));
            mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, R.color.darkBlue));
            mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, R.color.darkBlue));
            mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, R.color.darkBlue));
            mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, R.color.darkBlue));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarBlue);
            btRate.setBackgroundColor(ContextCompat.getColor(this,R.color.lightBlue));
            btRate.setTextColor(ContextCompat.getColor(this,R.color.darkBlue));

        } else if (courseAcron.equals("ECON")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_header));
                mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
                mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
                mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
                mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
            } else {
                mCourseTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_header));
                mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
                mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
                mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
                mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections));
            }

            mCourseTitle.setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
            mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
            mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
            mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
            mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarGreen);
            btRate.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGreen));
            btRate.setTextColor(ContextCompat.getColor(this,R.color.darkGreen));

        } else if (courseAcron.equals("ENGS")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_header_purple));
                mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
                mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
                mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
                mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
            } else {
                mCourseTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_header_purple));
                mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
                mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
                mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
                mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_purple));
            }

            mCourseTitle.setTextColor(ContextCompat.getColor(this, R.color.darkPurple));
            mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, R.color.darkPurple));
            mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, R.color.darkPurple));
            mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, R.color.darkPurple));
            mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, R.color.darkPurple));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarPurple);
            btRate.setBackgroundColor(ContextCompat.getColor(this,R.color.lightPurple));
            btRate.setTextColor(ContextCompat.getColor(this,R.color.darkPurple));
        } else if (courseAcron.equals("BIOL")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_header_orange));
                mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
                mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
                mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
                mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
            } else {
                mCourseTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_header_orange));
                mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
                mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
                mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
                mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_orange));
            }

            mCourseTitle.setTextColor(ContextCompat.getColor(this, R.color.darkOrange));
            mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, R.color.darkOrange));
            mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, R.color.darkOrange));
            mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, R.color.darkOrange));
            mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, R.color.darkOrange));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarOrange);
            btRate.setBackgroundColor(ContextCompat.getColor(this,R.color.lightOrange));
            btRate.setTextColor(ContextCompat.getColor(this,R.color.darkOrange));
        } else if (courseAcron.equals("GOVT")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_header_turq));
                mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
                mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
                mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
                mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
            } else {
                mCourseTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_header_turq));
                mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
                mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
                mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
                mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_turq));
            }


            mCourseTitle.setTextColor(ContextCompat.getColor(this, R.color.darkTurq));
            mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, R.color.darkTurq));
            mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, R.color.darkTurq));
            mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, R.color.darkTurq));
            mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, R.color.darkTurq));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarTurq);
            btRate.setBackgroundColor(ContextCompat.getColor(this,R.color.lightTurq));
            btRate.setTextColor(ContextCompat.getColor(this,R.color.darkTurq));
        } else if (courseAcron.equals("HIST")) {

            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_header_red));
                mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
                mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
                mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
                mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
            } else {
                mCourseTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_header_red));
                mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
                mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
                mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
                mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.course_description_sections_red));
            }


            mCourseTitle.setTextColor(ContextCompat.getColor(this, R.color.darkRed));
            mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, R.color.darkRed));
            mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, R.color.darkRed));
            mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, R.color.darkRed));
            mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, R.color.darkRed));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarRed);
            btRate.setBackgroundColor(ContextCompat.getColor(this,R.color.lightRed));
            btRate.setTextColor(ContextCompat.getColor(this,R.color.darkRed));

        }

    }
}