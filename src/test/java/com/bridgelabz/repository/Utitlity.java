package com.bridgelabz.repository;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utitlity {
    List userArraylist;
    Map userDetail;

    Utitlity(){
        userDetail=new HashMap();
        userArraylist=new ArrayList<Map<String, String>>();
    }

public  List getAddUsersData(){
    JSONObject userDetail1 = new JSONObject();
    userDetail1.put("username", "Laxman");
    userDetail1.put("mobile", "7030493048");
    userDetail1.put("pincode", "431513");
    userDetail1.put("locality", "Rampur");
    userDetail1.put("address", "Raawan");
    userDetail1.put("emailId", "raawan@gmail.com");
    userDetail1.put("city", "Nanded");
    userDetail1.put("landmark", "LLLL");
    userDetail1.put("type", "home");




    userArraylist.add(userDetail1);
    return userArraylist;
}

}
