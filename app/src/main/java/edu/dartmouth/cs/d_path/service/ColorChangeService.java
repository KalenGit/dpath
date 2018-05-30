package edu.dartmouth.cs.d_path.service;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import edu.dartmouth.cs.d_path.Adapters.AllCourseAdapter;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/29/18.
 */

public class ColorChangeService {
    Context context;

    public ColorChangeService(Context context){
        this.context = context;
    }

    //change color of course row depending on Major
    public void changeColor(AllCourseAdapter.ViewHolder holder, Course currentCourse){
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
            holder.getNumber().setBackgroundDrawable(ContextCompat.getDrawable(context, drawable) );
        } else {
            holder.getNumber().setBackground(ContextCompat.getDrawable(context, drawable));
        }
        holder.getNumber().setTextColor(ContextCompat.getColor(context, color));
        holder.getTitle().setTextColor(ContextCompat.getColor(context, color));

    }
}
