package edu.dartmouth.cs.d_path;

/**
 * Created by jameslee on 5/24/18.
 */

public class UserProfile {
    private String id;
    private String mMajor;
    private String mEmail;

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
}
