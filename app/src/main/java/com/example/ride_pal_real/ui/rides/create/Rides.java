package com.example.ride_pal_real.ui.rides.create;

public class Rides {
    public String name, time, destination, party1id, party2id, party1name, party2name;
    public boolean monday, tuesday, wednesday, thursday, friday;

    public Rides(){


    }

    public Rides(String time, String destination, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, String party1id, String party2id, String party1name, String party2name){
        this.time = time;
        this.destination = destination;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.party1id = party1id;
        this.party2id = party2id;
        this.party1name = party1name;
        this.party2name = party2name;
    }

    public String getParty1name() {
        return party1name;
    }

    public void setParty1name(String party1name) {
        this.party1name = party1name;
    }

    public String getParty2name() {
        return party2name;
    }

    public void setParty2name(String party2name) {
        this.party2name = party2name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }


    public String getParty1id() {
        return party1id;
    }

    public void setParty1id(String party1id) {
        this.party1id = party1id;
    }

    public String getParty2id() {
        return party2id;
    }

    public void setParty2id(String party2id) {
        this.party2id = party2id;
    }



    public String toStringList(){
        String s = "";
        s += getDestination();
        s += "- ";
        s += getTime();
        s += ": ";

        if(isMonday()){ s += "Monday ";}
        if(isTuesday()){ s += "Tuesday ";}
        if(isWednesday()){ s += "Wednesday ";}
        if(isTuesday()){ s += "Thursday ";}
        if(isFriday()){ s += "Friday ";}

        return s;
    }

    public String toStringListYR(String uid){
        String s = "";

        if (uid.equals(getParty1id())) {
            s += getParty2name() + ", ";

        }
        else{
            s += getParty1name() + ", ";

        }
        s += getDestination();
        s += "- ";
        s += getTime();
        s += ": ";
        if (isMonday()) {
            s += "Monday ";
        }
        if (isTuesday()) {
            s += "Tuesday ";
        }
        if (isWednesday()) {
            s += "Wednesday ";
        }
        if (isTuesday()) {
            s += "Thursday ";
        }
        if (isFriday()) {
            s += "Friday ";
        }
        return s;
    }

    public String makeTitle(){
        String s ="";
        s += getTime() + " ";
        if (isMonday()){ s+= "1";} else{ s += "0";};
        if (isTuesday()){ s+= "1";} else{ s += "0";};
        if (isWednesday()){ s+= "1";} else{ s += "0";};
        if (isThursday()){ s+= "1";} else{ s += "0";};
        if (isFriday()){ s+= "1";} else{ s += "0";};
        s += " "+getParty1id();
        return s;
    }


}
