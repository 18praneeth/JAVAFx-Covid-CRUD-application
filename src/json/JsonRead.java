package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.net.URLConnection;
import java.util.Date;

public class JsonRead {

    static void printStateData(JSONArray obj) {
        for (Object o : obj) {
            JSONObject state = (JSONObject) o;
            System.out.println("Active: " + state.get("active"));
            System.out.println("Confirmed: " + state.get("confirmed"));
            System.out.println("Deaths: " + state.get("deaths"));
            System.out.println("State: " + state.get("state"));
            System.out.println("=================");
        }
    }
        static void printDailyData(JSONArray obj1) throws java.text.ParseException {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdformat.parse("2021-04-01");
            for(Object o :obj1){
                JSONObject daily = (JSONObject)o;
                Date d2= sdformat.parse((String) daily.get("dateymd")) ;
              //  System.out.println("Date" + d2);
               if(d2.compareTo(d1)>=0) {
                   // JSONObject daily = (JSONObject)o;
                   System.out.println("Date" + daily.get("date"));
                   System.out.println("Number of deceased" + daily.get("totaldeceased"));

               }
            }
        }
        static void printVaccineData(JSONArray obj1) throws java.text.ParseException
        {
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = sdformat.parse("17/01/2021");
            for(Object o :obj1) {
                JSONObject daily = (JSONObject) o;
                Date d2 = sdformat.parse((String) daily.get("testedasof"));
                //  System.out.println("Date" + d2);
                if (d2.compareTo(d1) >= 0) {
                    // JSONObject daily = (JSONObject)o;
                    System.out.println("Date" + daily.get("date"));
                    System.out.println("Number of people vaccinated" + daily.get("totalindividualsvaccinated"));
                }
            }}

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject)obj;

        JSONArray state_wise_array = (JSONArray) covid_json.get("statewise");
        JSONArray daily_cases_array=(JSONArray) covid_json.get("cases_time_series");
        JSONArray vaccine_cases_array=(JSONArray) covid_json.get("tested");
            System.out.println(state_wise_array.size());
        printStateData(state_wise_array);
        System.out.println("====================================================");
        printDailyData(daily_cases_array);
        printVaccineData(vaccine_cases_array);

    }
}
