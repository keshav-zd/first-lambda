package com.exam1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VisitoreDataExtractor {



      public  List<VisitoreData> extractCirculationData() throws IOException, InterruptedException {
            List<VisitoreData> visitoreDataList = new ArrayList();
            List<String> locationLIst = new ArrayList();
          int april=0;
          int may=0;
          int june=0;
          int july=0;
          int august=0;
          int september=0;
          int october=0;
          int november=0;
          int december=0;


          String apiUrl = "https://data.cityofchicago.org/resource/74j2-zzz4.json";
            int limit = 90;
            int offset = 0;
            int count = 0;

            while (true) {
                String requestUrl = apiUrl + "?$limit=" + limit + "&$offset=" + offset;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(requestUrl))
                        .GET()
                        .build();

                HttpClient client = HttpClient.newHttpClient();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();

                    if (jsonArray.size() == 0) {
                        break;
                    }
                    Iterator<JsonElement> iterator = jsonArray.iterator();

                    while(iterator.hasNext()){
                        JsonObject jsonObject = iterator.next().getAsJsonObject();

                        String branch = jsonObject.get("branch").getAsString();
                     String address = jsonObject.get("address").getAsString();


                        String city = jsonObject.get("city").getAsString();

                        int ZIP = jsonObject.get("zip").getAsInt();
                        int jan = jsonObject.get("january").getAsInt();

                        int fab = jsonObject.get("february").getAsInt();

                        int mar = jsonObject.get("march").getAsInt();
                        if (jsonObject.has("april")) {
                            april = jsonObject.get("april").getAsInt();
                        }
                       if(jsonObject.has("may")){
                           may=jsonObject.get("may").getAsInt();
                       }

                        if (jsonObject.has("june")) {
                            june=jsonObject.get("june").getAsInt();
                        }
                        if (jsonObject.has("july")) {
                            june=jsonObject.get("july").getAsInt();
                        }
                        if (jsonObject.has("august")) {
                            august=jsonObject.get("august").getAsInt();
                        }

                        if (jsonObject.has("september")) {
                            september=jsonObject.get("september").getAsInt();
                        }

                        if (jsonObject.has("october")) {
                            october=jsonObject.get("october").getAsInt();
                        }


                        if (jsonObject.has("november")) november = jsonObject.get("november").getAsInt();

                        if (jsonObject.has("december")) {
                            november=jsonObject.get("december").getAsInt();
                        }



                        int ytd = jsonObject.get("ytd").getAsInt();


                        JsonObject locationdata=jsonObject.get("location").getAsJsonObject();
                        Location location=new Location(locationdata);
                        String location1=location.toString();
                        locationLIst.add(location1);





                        VisitoreData visitoreData = new VisitoreData(branch,address, city,ZIP,jan ,fab,mar,april,may,june,july,
                                august,september,october,november,december,ytd,location1);

                        visitoreDataList.add(visitoreData);
//                        for(CirculationData circulation:circulationDataList)
//                        {
//                            System.out.println(circulation);
//                        }
                       count++;
                    }

                    offset += limit;
                } else {
                    throw new RuntimeException("Failed to extract visitor data. Status code: " + response.statusCode());
                }

            }

            System.out.println("Extracted " + count + " visitor records.");
            return visitoreDataList;
        }
//        public static void main(String arg[])
//        {
//            CirculationDataExtractor ce=new CirculationDataExtractor();
//          try {
//              ce.extractCirculationData();
//          }catch(Exception e){
//              System.out.println(e);
//          }
        }




