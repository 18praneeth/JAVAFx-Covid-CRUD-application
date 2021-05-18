package sample;

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

    private static final double TILE_WIDTH  = 500;
    private static final double TILE_HEIGHT = 500;

    static void getCovidData(XYChart.Series series) throws IOException, ParseException, java.text.ParseException{
        JSONParser jsonparser = new JSONParser();
        URL covid = new URL("https://api.covid19india.org/data.json");
        URLConnection cc = covid.openConnection();
        BufferedReader reader = new BufferedReader((new InputStreamReader(cc.getInputStream())));
        Object obj = jsonparser.parse(reader);
        JSONObject covid_json = (JSONObject) obj;
        JSONArray daily_cases_array = (JSONArray) covid_json.get("cases_time_series");

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse("2020-10-01");

        for (Object o : daily_cases_array) {
            JSONObject daily = (JSONObject) o;
            Date d2 = sdformat.parse((String) daily.get("dateymd"));
            if (d2.compareTo(d1) >= 0) {
                series.getData().add(new XYChart.Data((String) daily.get("dateymd"), Integer.parseInt((String) daily.get("dailyconfirmed"))));
            }
        }

    }

    public Tile chartCreate() throws ParseException, java.text.ParseException, IOException {

        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("Daily Confirmed Cases");
        getCovidData(series2);

        XYChart.Series<String, Number> series3 = new XYChart.Series();
        series3.setName("Outside");
        series3.getData().add(new XYChart.Data("MO", 8));
        series3.getData().add(new XYChart.Data("TU", 53));
        series3.getData().add(new XYChart.Data("WE", 4));
        series3.getData().add(new XYChart.Data("TH", 2));
        series3.getData().add(new XYChart.Data("FR", 4));
        series3.getData().add(new XYChart.Data("SA", 3));
        series3.getData().add(new XYChart.Data("SU", 5));


        Tile lineChartTile = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Daily Confirmed Case")
                .animated(true)
                .smoothing(false)
                .series(series2)
                .build();

        return lineChartTile;
    }
}