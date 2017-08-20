package com.wordpress.keepup395.navi;

/**
 * Created by Swarna on 04-08-2017.
 */

public class Dataentry {
    private String chatId;
    private String bikename;
    private String chatuser;
    private String chatstart;
    private String startdate;
    private String enddate;
    private String chattitl;
    private String cost;
    private String email;

    public Dataentry(String chatId, String bikename, String chatuser, String chatstart, String startdate, String enddate, String cost, String chattitl, String email) {
        this.bikename = bikename;
        this.enddate = enddate;
        this.startdate = startdate;
        this.chatstart = chatstart;
        this.chatuser = chatuser;
        this.chatId = chatId;
        this.cost = cost;
        this.chattitl = chattitl;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getBikename() {
        return bikename;
    }

    public void setBikename(String bikename) {
        this.bikename = bikename;
    }

    public String getChatuser() {
        return chatuser;
    }

    public void setChatuser(String chatuser) {
        this.chatuser = chatuser;
    }

    public String getChatstart() {
        return chatstart;
    }

    public void setChatstart(String chatstart) {
        this.chatstart = chatstart;
    }

    public String getChatdata() {
        return startdate;
    }

    public void setChatdata(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getChattitl() {
        return chattitl;
    }

    public void setChattitl(String chattitl) {
        this.chattitl = chattitl;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String chatcos) {
        this.cost = cost;
    }
}
