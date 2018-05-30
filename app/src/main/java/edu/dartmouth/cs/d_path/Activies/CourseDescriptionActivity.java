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
import android.widget.Toast;

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
                if (dataSnapshot.getValue(Long.class)!=null) {
                    rating = dataSnapshot.getValue(Long.class);
                } else{
                    rating = 0;
                }
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

        mCourseTitle.setText(courseNumber + ":"+ "\n" +courseTitle);
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
        Toast.makeText(this, "Rated "+ courseNumber + " " + rating + " stars", Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child("user_"+ FirebaseAuth.getInstance().getUid()).child("ratings").child(courseNumber.replace(".", "-")).setValue(rating);
    }

    public void changeColors(String courseAcron){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        int header = 0;
        int section = 0;
        int lightColor = 0;
        int darkColor= 0;

        if (courseAcron.equals("COSC")) {
            header = R.drawable.course_description_header_blue;
            section = R.drawable.course_description_sections_blue;
            lightColor = R.color.lightBlue;
            darkColor=  R.color.darkBlue;

        } else if (courseAcron.equals("ECON")) {
            header = R.drawable.course_description_header;
            section = R.drawable.course_description_sections;
            lightColor = R.color.lightGreen;
            darkColor=  R.color.darkGreen;

        } else if (courseAcron.equals("ENGS")) {
            header = R.drawable.course_description_header_purple;
            section = R.drawable.course_description_sections_purple;
            lightColor = R.color.lightPurple;
            darkColor=  R.color.darkPurple;

        } else if (courseAcron.equals("BIOL")) {
            header = R.drawable.course_description_header_orange;
            section = R.drawable.course_description_sections_orange;
            lightColor = R.color.lightOrange;
            darkColor=  R.color.darkOrange;

        } else if (courseAcron.equals("GOVT")) {
            header = R.drawable.course_description_header_turq;
            section = R.drawable.course_description_sections_turq;
            lightColor = R.color.lightTurq;
            darkColor=  R.color.darkTurq;

        } else if (courseAcron.equals("HIST")) {
            header = R.drawable.course_description_header_red;
            section = R.drawable.course_description_sections_red;
            lightColor = R.color.lightRed;
            darkColor=  R.color.darkRed;
        }
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mCourseTitle.setBackgroundDrawable(ContextCompat.getDrawable(this, header));
            mCourseDescriptionHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, section));
            mCourseInstructorsHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, section));
            mCourseDistributivesHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, section));
            mCourseOfferedHeader.setBackgroundDrawable(ContextCompat.getDrawable(this, section));

        } else {
            mCourseTitle.setBackground(ContextCompat.getDrawable(this, header));
            mCourseDescriptionHeader.setBackground(ContextCompat.getDrawable(this, section));
            mCourseInstructorsHeader.setBackground(ContextCompat.getDrawable(this, section));
            mCourseDistributivesHeader.setBackground(ContextCompat.getDrawable(this, section));
            mCourseOfferedHeader.setBackground(ContextCompat.getDrawable(this, section));

        }
        mCourseTitle.setTextColor(ContextCompat.getColor(this, darkColor));
        mCourseDescriptionHeader.setTextColor(ContextCompat.getColor(this, darkColor));
        mCourseOfferedHeader.setTextColor(ContextCompat.getColor(this, darkColor));
        mCourseInstructorsHeader.setTextColor(ContextCompat.getColor(this, darkColor));
        mCourseDistributivesHeader.setTextColor(ContextCompat.getColor(this, darkColor));
//            ratingBar = new RatingBar(this, null, R.style.RatingBarBlue);
        btRate.setBackgroundColor(ContextCompat.getColor(this, lightColor));
        btRate.setTextColor(ContextCompat.getColor(this, darkColor));

    }
}