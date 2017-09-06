package com.inti.ricoh.julfi.idap.Objects;

/**
 * Created by julfi on 04/09/2017.
 */

public class NotificationLayer {

    private String id,title,message,date;

    public NotificationLayer(){

    }

    public NotificationLayer(String title,String message,String date){
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
