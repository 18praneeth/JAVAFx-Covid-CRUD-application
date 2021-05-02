package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


public class JsonRead {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject)obj;

        JSONArray state_wise_array = (JSONArray) covid_json.get("statewise");
        System.out.println(state_wise_array.size());
        for (Object o : state_wise_array) {

            System.out.println(o);
        }

    }
}
