package edu.dartmouth.cs.d_path.Activies;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    ImageView mIcon;
    TextInputEditText etPasswordInput;
    TextInputEditText etEmailInput;
    Button btLogin;
    Button btRegister;
    private FirebaseAuth mAuth;

    public static HashMap<String, Course> courseTable = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //get instance of firebase authentication
        mAuth = FirebaseAuth.getInstance();

        etPasswordInput = findViewById(R.id.password_input);
        etEmailInput = findViewById(R.id.email_input);
        btLogin = findViewById(R.id.login_button);
        btRegister = findViewById(R.id.register_button);
        mIcon = findViewById(R.id.icon_login);

        //animation start for icon
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.login_animation);
        anim.setInterpolator(new AnticipateOvershootInterpolator());
        mIcon.startAnimation(anim);

    }

    @Override
    public void onStart() {
        super.onStart();
        addCourses();

    }

    //clicking register button
    public void onRegister(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    //clicking login button
    public void onLogin(View v){
        final String email = etEmailInput.getText().toString();
        final String password = etPasswordInput.getText().toString();
        boolean hasError = checkInput(email, password);

        if (!hasError) {
            // Sign in
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //if login in authentification is successful
                            if (task.isSuccessful()) {
                                Log.d(TAG, "SIGN IN SUCCESS");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                //if login is unsuccessful
                            } else {
                                Log.d(TAG, "SIGN IN FAIL");
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_fail_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    //helper function to check text input errors
    public boolean checkInput(String email, String password){
        boolean hasError = false;
        if (email.equals("")){
            etEmailInput.setError(getString(R.string.field_required_error));
            hasError = true;
        }
        if (password.equals("")){
            etPasswordInput.setError(getString(R.string.field_required_error));
            hasError = true;
        }
        if (password.length() < 6) {
            etPasswordInput.setError(getString(R.string.password_length_error));
            hasError = true;
        }
        if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailInput.setError(getString(R.string.invalid_email_error));
            hasError = true;
        }
        return hasError;
    }
    //helper function to add all courses from firebase
    public void addCourses(){
        System.out.println("here");
        FirebaseDatabase
                .getInstance()
                .getReference("Courses")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            Course course = data.getValue(Course.class);
                            courseTable.put(course.getCourseNumber().replace(".", "-"), course);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
