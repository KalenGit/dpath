package edu.dartmouth.cs.d_path;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jameslee on 5/25/18.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private static final String TAG="CourseAdapter";
    private Context context;
    private LayoutInflater inflater;
    ArrayList<Course> courses = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView number;
        LinearLayout layout;
        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.course_title);
            number = itemView.findViewById(R.id.course_number);
            layout = itemView.findViewById(R.id.course_row);

            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "Item Clicked at "+ getAdapterPosition(), Toast.LENGTH_SHORT).show();

                }
            });
            number.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    delete(getAdapterPosition());

                }
            });
        }

//        @Override
//        public void onClick(View view) {
//            delete(getAdapterPosition());
////            Toast.makeText(view.getContext(), "Item Clicked at "+ getAdapterPosition(), Toast.LENGTH_SHORT).show();
//
//        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseAdapter(Context context, ArrayList<Course> courses) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courses = courses;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);
        View view = inflater.inflate(R.layout.course_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Course currentCourse = courses.get(position);
        holder.title.setText(currentCourse.title);
        holder.number.setText(currentCourse.courseNumber);
//        holder.number.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Log.d(TAG, "CLICKED NUMBER AT " + position);
//            }
//        });

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
