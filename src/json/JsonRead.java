package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class JsonRead {

    static void printStateData(JSONArray obj){
        for (Object o : obj) {
            JSONObject state = (JSONObject)o;
            System.out.println("Active: " + state.get("active"));
            System.out.println("Confirmed: " + state.get("confirmed"));
            System.out.println("Deaths: " + state.get("deaths"));
            System.out.println("State: " + state.get("state"));
            System.out.println("=================");
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject)obj;

        JSONArray state_wise_array = (JSONArray) covid_json.get("statewise");
        System.out.println(state_wise_array.size());
        printStateData(state_wise_array);


    }
}
