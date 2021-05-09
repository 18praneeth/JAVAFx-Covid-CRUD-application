package json;
//package application;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.application.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javafx.scene.layout.StackPane;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.net.URLConnection;
import java.util.Date;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
public class JsonRead extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject)obj;
        JSONArray daily_cases_array=(JSONArray) covid_json.get("cases_time_series");
        NumberAxis xaxis = new NumberAxis(1, 30, 1);
        NumberAxis yaxis = new NumberAxis(0, 200000, 5);

        xaxis.setLabel("Date");
        yaxis.setLabel("Number of cases");


        LineChart linechart = new LineChart(xaxis, yaxis);


        XYChart.Series series = new XYChart.Series();


        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse("2021-04-01");
        int i = 1;
        for (Object o :daily_cases_array) {
            JSONObject daily = (JSONObject) o;
            Date d2 = sdformat.parse((String) daily.get("dateymd"));
            //  System.out.println("Date" + d2);
            if (d2.compareTo(d1) >= 0) {
                // JSONObject daily = (JSONObject)o;
                series.getData().add(new XYChart.Data(i, daily.get("totaldeceased")));
                i++;
            }
            series.setName("Number of people infected in april");

            //adding series to the linechart
            linechart.getData().add(series);

            //setting Group and Scene
            Group root = new Group(linechart);
            root.getChildren().add(linechart);
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Number of people infected in april");
            primaryStage.show();
        }
    }


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
        for (Object o : obj1) {
            JSONObject daily = (JSONObject) o;
            Date d2 = sdformat.parse((String) daily.get("dateymd"));
            //  System.out.println("Date" + d2);
            if (d2.compareTo(d1) >= 0) {
                // JSONObject daily = (JSONObject)o;
                System.out.println("Date" + daily.get("date"));
                System.out.println("Number of deceased" + daily.get("totaldeceased"));

            }
        }
    }
       /* static void printVaccineData(JSONArray obj1) throws java.text.ParseException
        {
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = sdformat.parse("17/01/2021");

            for(Object o :obj1) {
                JSONObject daily = (JSONObject) o;
               String d= (String) daily.get("testedasof");
                Date d2 = sdformat.parse((String) daily.get("testedasof"));
                if(d.equalsIgnoreCase(" "))
                    continue;
                //  System.out.println("Date" + d2);
                if (d2.compareTo(d1) >= 0) {
                    // JSONObject daily = (JSONObject)o;
                    System.out.println("Date" + daily.get("date"));
                    System.out.println("Number of people vaccinated" + daily.get("totalindividualsvaccinated"));
                }
            }}*/




    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject)obj;

        JSONArray state_wise_array = (JSONArray) covid_json.get("statewise");
        JSONArray daily_cases_array=(JSONArray) covid_json.get("cases_time_series");
       // JSONArray vaccine_cases_array=(JSONArray) covid_json.get("tested");
            System.out.println(state_wise_array.size());
        printStateData(state_wise_array);
        System.out.println("====================================================");
        printDailyData(daily_cases_array);
      //  printVaccineData(vaccine_cases_array);
        launch(args);

    }
}
