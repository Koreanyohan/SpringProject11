package com.example.demo.openAPI;

import java.util.ArrayList;
import lombok.ToString;

@ToString // 11장 p.40 => OpenAPITest2.java에서 getWeather()메서드의 sb.toString(); 가능하게 함.
class Body{
    public String dataType;
    public Items items;
    public int pageNo;
    public int numOfRows;
    public int totalCount;
}
@ToString // 11장 p.40
class Header{
    public String resultCode;
    public String resultMsg;
}
@ToString // 11장 p.40
class Item{
    public Object announceTime;
    public int numEf;
    public String regId;
    public int rnSt;
    public int rnYn;
    public String ta;
    public String wd1;
    public String wd2;
    public String wdTnd;
    public String wf;
    public String wfCd;
    public String wsIt;
}

@ToString // 11장 p.40
class Items{
    public ArrayList<Item> item;
}

@ToString // 11장 p.40
class Response{
    public Header header;
    public Body body;
}

@ToString // 11장 p.40
class Root{
    public Response response;
}