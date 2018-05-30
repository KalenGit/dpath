package edu.dartmouth.cs.d_path.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.dartmouth.cs.d_path.Activies.CourseDescriptionActivity;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;
import edu.dartmouth.cs.d_path.service.ColorChangeService;

/**
 * Created by jameslee on 5/28/18.
 */
public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.ViewHolder> {
    private static final String TAG="AllCourseAdapter";
    private Context context;
    private LayoutInflater inflater;
    ArrayList<Course> courses = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView number;
        ImageView save;
        LinearLayout layout;
        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.child_course_title);
            number = itemView.findViewById(R.id.child_course_number);
            layout = itemView.findViewById(R.id.all_course_row);
            save = itemView.findViewById(R.id.child_save_icon);

            //onClick listener for each row
            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //store course data from intent and start activity
                    Intent intent = new Intent(context, CourseDescriptionActivity.class);
                    intent.putExtra("course_number",courses.get(getAdapterPosition()).courseNumber);
                    intent.putExtra("course_description", courses.get(getAdapterPosition()).description);
                    intent.putExtra("course_title", courses.get(getAdapterPosition()).title);
                    intent.putExtra("course_instructors", courses.get(getAdapterPosition()).instructors);
                    intent.putExtra("course_distributives", courses.get(getAdapterPosition()).distributives);
                    intent.putExtra("course_offered", courses.get(getAdapterPosition()).offered);
                    context.startActivity(intent);

                }
            });

            //onClick listener for save
            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "SAVED "+ courses.get(getAdapterPosition()).getCourseNumber(), Toast.LENGTH_SHORT).show();
                    //save to firebase
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child("user_" + FirebaseAuth.getInstance().getUid()).child("saved")
                            .child(courses.get(getAdapterPosition()).getCourseNumber()
                                    .replace(".","-"))
                            .setValue(courses.get(getAdapterPosition()).getCourseNumber().replace(".","-"));
                }
            });



        }

        public TextView getNumber() {
            return number;
        }

        public TextView getTitle() {
            return title;
        }
    }


    public AllCourseAdapter(Context context, ArrayList<Course> courses) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courses = courses;
    }

    @Override
    public AllCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.all_course_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        ColorChangeService colorChangeService = new ColorChangeService(context);
        Course currentCourse = courses.get(position);
        holder.title.setText(currentCourse.title);
        holder.number.setText(currentCourse.courseNumber);
        colorChangeService.changeColor(holder, currentCourse);

    }


    // Return the size of dataset
    @Override
    public int getItemCount() {
        return courses.size();
    }
}
