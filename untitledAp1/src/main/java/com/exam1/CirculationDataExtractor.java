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

public class CirculationDataExtractor {

    String apiUrl;
    CirculationDataExtractor(String apiUrl)
    {
        this.apiUrl=apiUrl;
    }

    public   List<VisitoreData> extractData() throws IOException, InterruptedException {
        List<VisitoreData> visitoreDataList = new ArrayList();
        List<String> locationLIst = new ArrayList();

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
                    int april=0;
                    int may=0;
                    int june=0;
                    int july=0;
                    int august=0;
                    int september=0;
                    int october = 0;
                    int november=0;
                    int december = 0;
                    String address = null;
                    int ZIP = 0;
                    int jan = 0;
                    int fab = 0;
                    int mar = 0;
                    String city=null;
                    String location1 = null;
                    JsonObject jsonObject = iterator.next().getAsJsonObject();
                    System.out.println(jsonObject);
                    String branch = jsonObject.get("branch").getAsString();
                     System.out.println(branch);
                     if(jsonObject.has("address"))
                     {
                          address = jsonObject.get("address").getAsString();
                     }
                    System.out.println(address);
                    if(jsonObject.has("city"))
                    {
                        city = jsonObject.get("city").getAsString();
                    }
                    System.out.println(city);
                   if(jsonObject.has("zip"))
                   {
                       ZIP = jsonObject.get("zip").getAsInt();
                   }
                    System.out.println(ZIP);
                   
                   
                   if(jsonObject.has("january"))
                   {
                       jan = jsonObject.get("january").getAsInt();
                   }

                   
                   
                if(jsonObject.has("february"))
                {
                    fab = jsonObject.get("february").getAsInt();
                }

                   
                if(jsonObject.has("march"))
                {
                    mar = jsonObject.get("march").getAsInt();
                }
                    System.out.println(mar);
                    if (jsonObject.has("april")) {
                        april = jsonObject.get("april").getAsInt();
                    }
                    System.out.println(april);


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
                        december=jsonObject.get("december").getAsInt();
                    }



                    int ytd = 0;
                    if(jsonObject.has("ytd")) {
                        ytd = jsonObject.get("ytd").getAsInt();
                    }
                    JsonObject locationdata;
                    if(jsonObject.has("location")) {

                        locationdata = jsonObject.get("location").getAsJsonObject();

                        Location location = new Location(locationdata);
                         location1 = location.toString();
                        locationLIst.add(location1);
                    }




                VisitoreData visitoreData = new VisitoreData(branch,address, city,ZIP,jan ,fab,mar,april,may,june,july,
                            august,september,october,november,december,ytd,location1);

                    visitoreDataList.add(visitoreData);
//                        for(VisitoreData circulation:visitoreDataList)
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
//        public static void main(String arg[]) {
//           // CirculationDataExtractor ce = new CirculationDataExtractor();
//            try {
//                ce.extractData();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }

}

