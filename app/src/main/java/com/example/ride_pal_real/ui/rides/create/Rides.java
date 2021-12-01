package com.example.ride_pal_real.ui.rides.create;

public class Rides {
    public String name, time, destination, party1id, party2id;
    public boolean monday, tuesday, wednesday, thursday, friday;

    public Rides(){


    }




    public Rides(String name, String time, String destination, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, String party1id, String party2id){
        this.name = name;
        this.time = time;
        this.destination = destination;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.party1id = party1id;
        this.party2id = party2id;
    }


    public String getName() {
        return name;
    }

    public void setName(String dis) {
        this.name = dis;
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




    @Override
    public String toString() {
        return "Rides{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", destination='" + destination + '\'' +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                '}';
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

    public String makeTitle(){
        String s ="";
        s += getTime() + " ";
        if (isMonday()){ s+= "1";} else{ s += "0";};
        if (isTuesday()){ s+= "1";} else{ s += "0";};
        if (isWednesday()){ s+= "1";} else{ s += "0";};
        if (isThursday()){ s+= "1";} else{ s += "0";};
        if (isFriday()){ s+= "1";} else{ s += "0";};
        return s;
    }


}
