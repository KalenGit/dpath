package edu.dartmouth.cs.d_path.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jameslee on 5/24/18.
 */

public class UserProfile {
    private String id;
    private String mMajor;
    private String mEmail;
    private ArrayList<Course> recommendations;
    private ArrayList<Course> saved;
    private HashMap<String, Long> ratings;

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Course> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(ArrayList<Course> recommendations) {
        this.recommendations = recommendations;
    }

    public ArrayList<Course> getSaved() {
        return saved;
    }

    public void setSaved(ArrayList<Course> saved) {
        this.saved = saved;
    }

    public HashMap<String, Long> getRatings() {
        return ratings;
    }

    public void setRatings(HashMap<String, Long> ratings) {
        this.ratings = ratings;
    }
}
