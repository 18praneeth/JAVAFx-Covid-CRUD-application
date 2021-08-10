package controller;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.Tile.SkinType;
import javafx.scene.chart.XYChart;

//Json
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

public class Chart {

    private static final double TILE_WIDTH  = 3000;
    private static final double TILE_HEIGHT = 500;

    static JSONObject conn() throws IOException, ParseException{
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject) obj;
        return covid_json;
    }

    static void daily_confirmed(XYChart.Series daily_confirmed, XYChart.Series daily_deceased, XYChart.Series daily_recovered) throws java.text.ParseException, IOException, ParseException {

        JSONArray daily_cases_array = (JSONArray) conn().get("cases_time_series");

        for (Object o : daily_cases_array) {
            JSONObject daily = (JSONObject) o;
            daily_confirmed.getData().add(new XYChart.Data((String) daily.get("date"), Integer.parseInt((String) daily.get("dailyconfirmed"))));
            daily_deceased.getData().add(new XYChart.Data((String) daily.get("date"), Integer.parseInt((String) daily.get("dailydeceased"))));
            daily_recovered.getData().add(new XYChart.Data((String) daily.get("date"), Integer.parseInt((String) daily.get("dailyrecovered"))));
        }
    }

    static void vaccinated(XYChart.Series vaccinated_data) throws IOException, ParseException, java.text.ParseException {
        JSONArray daily_cases_array = (JSONArray) conn().get("tested");

        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = date_format.parse("16/1/2021");
        Date d3 = date_format.parse("29/7/2021");
        for (Object o : daily_cases_array) {
            JSONObject daily = (JSONObject) o;
            if (daily.get("testedasof") != "") {
//                System.out.println(daily.get("testedasof"));
                Date d2 = date_format.parse((String) daily.get("testedasof"));
                if (d2.compareTo(d1) >= 0 && d2.compareTo(d3) <= 0) {
                    vaccinated_data.getData().add(new XYChart.Data((String) daily.get("testedasof"), Integer.parseInt((String) daily.get("totalindividualsvaccinated"))));
                }
            }
        }
    }


    public Tile[] chartCreate() throws ParseException, java.text.ParseException, IOException {

        XYChart.Series<String, Number> series1 = new XYChart.Series();
        XYChart.Series<String, Number> series2 = new XYChart.Series();
        XYChart.Series<String, Number> series3 = new XYChart.Series();
        XYChart.Series<String, Number> series4 = new XYChart.Series();
        series1.setName("Daily Confirmed Cases");
        series2.setName("Daily Deceased Cases");
        series3.setName("Daily Recovered Cases");
        daily_confirmed(series1, series2, series3);
        vaccinated(series4);

        Tile[] line_chart = new Tile[4];

        Tile daily_confirmed_case = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Daily Confirmed Case")
                .animated(true)
                .smoothing(true)
                .series(series1)
                .build();

        Tile daily_deceased_case = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Daily Deceased Case")
                .animated(true)
                .smoothing(true)
                .series(series2)
                .build();


        Tile daily_recovered_case = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Daily Recovered Case")
                .animated(true)
                .smoothing(true)
                .series(series3)
                .build();

        Tile vaccinated = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Vaccinated Data")
                .animated(true)
                .smoothing(true)
                .series(series4)
                .build();


        line_chart[0] = daily_confirmed_case;
        line_chart[1] = daily_deceased_case;
        line_chart[2] = daily_recovered_case;
        line_chart[3] = vaccinated;

        return line_chart;
    }
}