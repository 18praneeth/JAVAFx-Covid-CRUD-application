package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;


public class JsonRead {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader(".\\src\\json\\data.json");

        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject)obj;

        String s_name = (String) covid_json.get("name");
        System.out.println(s_name);

    }
}
