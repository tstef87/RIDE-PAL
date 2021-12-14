package com.example.ride_pal_real.ui.rides.create;

public class Rides {
    public String name, time, destination, party1id, party2id, party1name, party2name, party1phonenumber, party2phonenumber, party1address, party2address;
    public boolean monday, tuesday, wednesday, thursday, friday;

    public Rides(){


    }




    public Rides(String time, String destination, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, String party1id, String party2id, String party1name, String party2name, String party1phonenumber, String party2phonenumber, String party1address, String party2address){
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
        this.party1phonenumber = party1phonenumber;
        this.party2phonenumber = party2phonenumber;
        this.party1address = party1address;
        this.party2address = party2address;

    }



    public String getParty1phonenumber() {
        return party1phonenumber;
    }

    public void setParty1phonenumber(String party1phonenumber) {
        this.party1phonenumber = party1phonenumber;
    }

    public String getParty2phonenumber() {
        return party2phonenumber;
    }

    public void setParty2phonenumber(String party2phonenumber) {
        this.party2phonenumber = party2phonenumber;
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

    public String makeDOW(){
        String s = "";

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

    public String getParty1address() {
        return party1address;
    }

    public void setParty1address(String party1address) {
        this.party1address = party1address;
    }

    public String getParty2address() {
        return party2address;
    }

    public void setParty2address(String party2address) {
        this.party2address = party2address;
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

    public String urlMaker(String id){

        String s = "";
        s += "https//:www.google.com/maps?daddr=";
        s += format(id);
        s += "+to:";
        s += getDestinationlonglat();

        //s += "&travelmode=driving";

        //https://www.google.com/maps?daddr=Saint+Joseph+University+to:128+jamestown+ave

        //https://www.google.com/maps/dir/?api=1&destination=7+bobtail+run+broomall&waypoints=7+bobtail+run&travelmode=driving
        System.out.println(s);
        return s;
    }

    public String format(String id){
        String s = "";
        String address;

        if (id.equals(getParty1id())) {
            address = getParty2address();
        }
        else {
            address = getParty1address();
        }

        for (int i = 0; i <address.length(); i++) {

            if (address.charAt(i) == ' '){
                s += "+";
            }
            else{
                s += address.charAt(i);
            }
        }

        return s;
    }

    public String getDestinationlonglat(){

        String s = "";
        if(getDestination().equals("Mandevill")){

            s += "39.996349,-75.236048";
            return s;

        }
        else if (getDestination().equals("Hagan")){

            s += "39.995671,-75.235773";
            return s;
        }

        else if (getDestination().equals("Campion")){

            s += "39.993389,-75.240340";
            return s;

        }

        else if (getDestination().equals("Merion")){

            s += "39.996297,-75.244389";
            return s;
        }
        else{


            return s;
        }
    }

}



