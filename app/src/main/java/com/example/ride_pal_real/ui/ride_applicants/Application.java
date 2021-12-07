package com.example.ride_pal_real.ui.ride_applicants;

public class Application {


    public String name, preferences, id, phonenumber, canDrive;

    public Application(){


    }


    public Application(String name, String preferences, String id, String phonenumber, String canDrive){
        this.name = name;
        this.preferences = preferences;
        this.id = id;
        this.phonenumber = phonenumber;
        this.canDrive = canDrive;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCanDrive() {

        return canDrive;
    }

    public void setCanDrive(boolean cd) {
        if (cd){
            canDrive = "true";
        }
        else{
            canDrive = "false";
        }
    }

    public void setCanDrive2(String canDrive){
        this.canDrive = canDrive;

    }

    public String makeTitle(){
        String s = "";
        s += getName();
        return s;
    }


}
