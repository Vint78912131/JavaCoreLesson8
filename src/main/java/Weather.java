import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Weather {
    private ArrayList<WeatherResponse> wr;

    public ArrayList<WeatherResponse> getWr() {
        return wr;
    }

    public Weather(Integer limit) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.weather.yandex.ru")
                .addPathSegment("v2")
                .addPathSegment("forecast")
                .addQueryParameter("lat", "55.8204")
                .addQueryParameter("lon", "37.3302")
                .addQueryParameter("lang", "ru_RU")
                .addQueryParameter("limit", limit.toString())
                .addQueryParameter("hours", "false")
                .addQueryParameter("extra", "true")
                .build();

        Request request = new Request.Builder()
                .addHeader("X-Yandex-API-Key", "81402e9b-8fc1-4e4a-941b-3c101867ebcf")
                .url(url)
                .get()
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String body = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        String city = objectMapper.readTree(body).at("/geo_object/locality/name").asText();

        ArrayNode forcasts = (ArrayNode) objectMapper.readTree(body).at("/forecasts");

        wr = new ArrayList<WeatherResponse>();
        for (int i = 0; i < forcasts.size(); i++) {
            JsonNode individualElement = forcasts.get(i);
            String date = individualElement.get("date").asText();
            String morningT = individualElement.get("parts").get("morning").get("temp_avg").asText();
            String morningC = individualElement.get("parts").get("morning").get("condition").asText();
            wr.add(new WeatherResponse(city, date, "expected in the morning " + morningC,Integer.parseInt(morningT)));
            String dayT = individualElement.get("parts").get("day").get("temp_avg").asText();
            String dayC = individualElement.get("parts").get("day").get("condition").asText();
            wr.add(new WeatherResponse(city, date, "expected in the afternoon " + dayC,Integer.parseInt(dayT)));
            String eveningT = individualElement.get("parts").get("evening").get("temp_avg").asText();
            String eveningC = individualElement.get("parts").get("evening").get("condition").asText();
            wr.add(new WeatherResponse(city, date, "expected in the evening " + eveningC,Integer.parseInt(eveningT)));
            String nightT = individualElement.get("parts").get("night").get("temp_avg").asText();
            String nightC = individualElement.get("parts").get("night").get("condition").asText();
            wr.add(new WeatherResponse(city, date, "expected at night " + nightC,Integer.parseInt(nightT)));
        }
    }
}
