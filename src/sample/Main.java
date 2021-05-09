package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;


public class Main extends Application{
    static void Getcoviddata(XYChart.Series series) throws IOException, ParseException, java.text.ParseException {

        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject) obj;
        JSONArray daily_cases_array = (JSONArray) covid_json.get("cases_time_series");
        int i = 1;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse("2021-04-01");
        for (Object o : daily_cases_array) {
            JSONObject daily = (JSONObject) o;
            Date d2 = sdformat.parse((String) daily.get("dateymd"));
            //  System.out.println("Date" + d2);
            if (d2.compareTo(d1) >= 0) {


                // JSONObject daily = (JSONObject)o;
                series.getData().add(new XYChart.Data(i, Integer.parseInt((String) daily.get("totaldeceased"))));
                i++;
            }
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        NumberAxis xAxis = new NumberAxis(1, 30, 1);
        xAxis.setLabel("Dates");
        NumberAxis yAxis = new NumberAxis(0, 400000, 1000);
        yAxis.setLabel("Number of people infected");

        LineChart linechart = new LineChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();

        Getcoviddata(series);

        series.setName("Number of people infected in the month of april");
        linechart.getData().add(series);
        Group root = new Group();
        root.getChildren().add(linechart);
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Number of people infected in the month of april");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[] args)
    {
        launch(args);
    }

}