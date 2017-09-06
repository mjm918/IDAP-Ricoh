package com.inti.ricoh.julfi.idap.Objects;

/**
 * Created by julfi on 30/08/2017.
 */

public class MessageLayer {

    private  String id,name,message,date,alais;


    public MessageLayer(){

    }

    public MessageLayer(String name,String message,String date,String alais){
        this.name = name;
        this.message = message;
        this.date = date;
        this.alais = alais;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
    }
}
