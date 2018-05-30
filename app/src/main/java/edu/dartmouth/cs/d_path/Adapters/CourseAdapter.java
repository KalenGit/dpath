package edu.dartmouth.cs.d_path.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.dartmouth.cs.d_path.Activies.CourseDescriptionActivity;
import edu.dartmouth.cs.d_path.Fragments.CoursesFragment;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/25/18.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private static final String TAG="CourseAdapter";
    private Context context;
    private LayoutInflater inflater;
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Course> coursesRest = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView number;
        ImageView delete;
        ImageView save;
        LinearLayout layout;
        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.course_title);
            number = itemView.findViewById(R.id.course_number);
            layout = itemView.findViewById(R.id.course_row);
            delete = itemView.findViewById(R.id.delete_icon);
            save = itemView.findViewById(R.id.save_icon);



            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
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

            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "DELETED "+ courses.get(getAdapterPosition()).getCourseNumber(), Toast.LENGTH_SHORT).show();
                    DatabaseReference ref;
                    ref = FirebaseDatabase.getInstance().getReference("Users").child("user_"+ FirebaseAuth.getInstance().getUid()).child("recommendations");
                    ref.child(courses.get(getAdapterPosition()).courseNumber.replace(".","-")).removeValue();
                    delete(getAdapterPosition());

                    courses.add(5, coursesRest.get(0));
                    coursesRest.remove(0);
                    notifyItemInserted(5);


                }
            });
            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "SAVED "+ courses.get(getAdapterPosition()).getCourseNumber(), Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child("user_" + FirebaseAuth.getInstance().getUid()).child("saved")
                            .child(courses.get(getAdapterPosition()).getCourseNumber()
                                    .replace(".","-"))
                            .setValue(courses.get(getAdapterPosition()).getCourseNumber().replace(".","-"));
                                }
                            });



                }
        }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseAdapter(Context context, ArrayList<Course> courses, ArrayList<Course> courseRest) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courses = courses;
        this.coursesRest = courseRest;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.course_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Course currentCourse = courses.get(position);
        if (courses.get(position)!=null) {
            holder.title.setText(currentCourse.title);
            holder.number.setText(currentCourse.courseNumber);
        }
        Log.d(TAG, "onBind");

        changeColor(holder, currentCourse);
    }
    public void changeColor(ViewHolder holder, Course currentCourse){
        String courseNumb = currentCourse.getCourseNumber();
        final int sdk = android.os.Build.VERSION.SDK_INT;
        int drawable = 0;
        int color = 0;


        if (courseNumb.contains("ECON")){
            drawable = R.drawable.recycler_shape2;
            color = R.color.darkGreen;

        } else if (courseNumb.contains("BIOL")){
            drawable = R.drawable.recycler_shape2_orange;
            color = R.color.darkOrange;

        } else if(courseNumb.contains("COSC")){
            drawable = R.drawable.recycler_shape2_blue;
            color = R.color.darkBlue;

        } else if (courseNumb.contains("HIST")) {
            drawable = R.drawable.recycler_shape2_red;
            color = R.color.darkRed;

        } else if(courseNumb.contains("GOVT")){
            drawable = R.drawable.recycler_shape2_turq;
            color = R.color.darkTurq;

        } else if(courseNumb.contains("ENGS")) {
            drawable = R.drawable.recycler_shape2_purple;
            color = R.color.darkPurple;
        }

        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable) );
        } else {
            holder.number.setBackground(ContextCompat.getDrawable(context, drawable));
        }
        holder.number.setTextColor(ContextCompat.getColor(context, color));
        holder.title.setTextColor(ContextCompat.getColor(context, color));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void delete(int position){
        courses.remove(position);
        notifyItemRemoved(position);
    }
}
