package edu.dartmouth.cs.d_path.Adapters;

import android.content.Context;
import android.content.Intent;
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
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;


public class SavedCourseAdapter extends RecyclerView.Adapter<SavedCourseAdapter.ViewHolder> {
    private static final String TAG="SavedCourseAdapter";
    private Context context;
    private LayoutInflater inflater;
    ArrayList<Course> courses = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView number;
        ImageView delete;
        LinearLayout layout;
        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.course_title);
            number = itemView.findViewById(R.id.course_number);
            layout = itemView.findViewById(R.id.saved_course_row);
            delete = itemView.findViewById(R.id.delete_icon);

            //onClick for course row
            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "Item Clicked at "+ getAdapterPosition(), Toast.LENGTH_SHORT).show();
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
            //onClick to delete
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (getAdapterPosition()>=0) {
                        Toast.makeText(view.getContext(), "DELETED " + courses.get(getAdapterPosition()).getCourseNumber(), Toast.LENGTH_SHORT).show();
                        //delete course row from recyclerview
                        delete(getAdapterPosition());
                    }

                }
            });



        }
    }

    public SavedCourseAdapter(Context context, ArrayList<Course> courses) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courses = courses;
    }

    @Override
    public SavedCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.saved_course_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Course currentCourse = courses.get(position);
        holder.title.setText(currentCourse.title);
        holder.number.setText(currentCourse.courseNumber);
        changeColor(holder, currentCourse);

    }

    //helper function to change color depending on courseMajor
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

    // Return the size of dataset
    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void delete(int position){
        courses.remove(position);
        notifyItemRemoved(position);
    }
}
