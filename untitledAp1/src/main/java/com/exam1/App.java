package com.exam1;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    static String query_visitore = " staging_table1";

    //static String apiUrl="https://data.cityofchicago.org/resource/6v4c-vqh3.json";

    static String query_circulation = " staging_circulation";

    static String visitor_by = "https://data.cityofchicago.org/resource/74j2-zzz4.json";
    static String circulation_by = "https://data.cityofchicago.org/resource/6v4c-vqh3.json";
    static String  dim_visitore="visitore_dim";
    static String final_visitore="final_visitore";

    static String  dim_circulation="circulation_dim";
    static String final_circulation="final_circulation";


    public static void main(String arg[]) throws IOException, InterruptedException {
        // VisitoreDataExtractor vd=new VisitoreDataExtractor();

        try {
            CirculationDataExtractor cd = new CirculationDataExtractor(visitor_by);
            List<VisitoreData> visitoreDataList = (List<VisitoreData>) cd.extractData();
            DatabaseInsertion di = new DatabaseInsertion(query_visitore, visitoreDataList,dim_visitore,final_visitore);
            di.insertCirculationData();

        } catch (Exception e) {
            System.out.println(e);
        }
        try {

            CirculationDataExtractor cd1 = new CirculationDataExtractor(circulation_by);
            List<VisitoreData> visitoreDataList1 = (List<VisitoreData>) cd1.extractData();
            DatabaseInsertion di1 = new DatabaseInsertion(query_circulation, visitoreDataList1,dim_circulation,final_circulation);
            di1.insertCirculationData();

        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
