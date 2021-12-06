package com.example.ride_pal_real.ui.ride_applicants;

public class Application {


    public String name, prefrences, id, phonenumber, canDrive;

    public Application(){


    }


    public Application(String name, String prefrences, String id, String phonenumber, String canDrive){
        this.name = name;
        this.prefrences = prefrences;
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

    public String getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(String prefrences) {
        this.prefrences = prefrences;
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

    public boolean getCanDrive() {

        if(canDrive.equals("true")){
            return true;
        }
        return false;
    }

    public void setCanDrive(boolean cd) {
        if (cd){
            canDrive = "true";
        }
        else{
            canDrive = "false";
        }
    }

    public String makeTitle(){
        String s = "";
        s += getName();
        return s;
    }


}
