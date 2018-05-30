package edu.dartmouth.cs.d_path.Activies;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import edu.dartmouth.cs.d_path.R;
import edu.dartmouth.cs.d_path.Model.UserProfile;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    Spinner majorSpinner;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btRegister;
    ImageView mIcon;

    UserProfile newUser;


    String[] majors= new String[] {"Biology", "Computer Science", "Engineering Sciences", "Economics", "Government", "History",};
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private Boolean error = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //get instance of Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //set up new User
        newUser = new UserProfile();

        etEmail = findViewById(R.id.email_register);
        etPassword = findViewById(R.id.password_register);
        btRegister = findViewById(R.id.register_button2);
        mIcon = findViewById(R.id.icon_register);

        majorSpinner = findViewById(R.id.major_register);
        ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, majors);
        majorSpinner.setAdapter(inputAdapter);

        majorSpinner.getBackground().setColorFilter(getResources().getColor(R.color.registerColor),
                PorterDuff.Mode.SRC_ATOP);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.login_animation);
        anim.setInterpolator(new AnticipateOvershootInterpolator());
        mIcon.startAnimation(anim);
    }

    public void checkErrors(){
        Log.d(TAG, "checkErrors");

        String newEmail = etEmail.getText().toString();
        String newPassword = etPassword.getText().toString();
        Log.d(TAG, newEmail + " " + newPassword);
        if(TextUtils.isEmpty(newEmail)) {
            Log.d(TAG, "isEmpty");
            etEmail.setError(getString(R.string.field_required_error));
            error = true;
        }
        if(TextUtils.isEmpty(newPassword)) {
            etPassword.setError(getString(R.string.field_required_error));
            error = true;
        }
        if (newPassword.length() >0 && newPassword.length() < 6) {
            etPassword.setError(getString(R.string.password_length_error));
            error = true;
        } if(!TextUtils.isEmpty(newEmail) && !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            etEmail.setError(getString(R.string.invalid_email_error));
            error = true;
        }
    }

    public void onRegisterProfile(View v){
        checkErrors();
        Log.d(TAG, "checkedErrors");
        if (!error) {
            Log.d(TAG, "savingToFirebase");
            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),
                    etPassword.getText().toString())
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(RegisterActivity.this, "Authentication worked.",
                                        Toast.LENGTH_LONG).show();
                                //if successful add data to userProfile
                                newUser.setEmail(etEmail.getText().toString());
                                newUser.setMajor(majorSpinner.getSelectedItem().toString());
                                newUser.setId(task.getResult().getUser().getUid());

                                //add data to firebase
                                mFirebaseDatabase.getReference().child("Users").child("user_" + newUser.getId())
                                        .setValue(newUser)
                                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "SAVED");
                                                    finish();


                                                } else {
                                                    if (task.getException() != null)
                                                        Log.w(TAG, task.getException().getMessage());
                                                }
                                            }
                                        });
                                //


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed",
                                        Toast.LENGTH_LONG).show();
                                //
                            }
                        }
                    });
        }
        error=false;

    }


}
