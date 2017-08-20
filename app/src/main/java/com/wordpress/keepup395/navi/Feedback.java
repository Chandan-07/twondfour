package com.wordpress.keepup395.navi;

/**
 * Created by Swarna on 04-08-2017.
 */

public class Feedback {
    private String uderid;
    private String emailuser;
    private String feed;

    public Feedback(String userid, String emailuser, String feed) {
        this.uderid = userid;
        this.emailuser = emailuser;
        this.feed = feed;

    }

    public String getUderid() {
        return uderid;
    }

    public void setUderid(String uderid) {
        this.uderid = uderid;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
