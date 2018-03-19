package org.eap.pli24.eweather;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TempUtils 
{

    public String getWeatherForCities
        (
            String baseUrl, 
            String apiKey,
            List<String> cityIds)
            throws MalformedURLException, IOException {
        StringBuilder sb = new StringBuilder();

        String urlStr= baseUrl + "?id=";
        int count = cityIds.size();
        for (int i = 0; i < count; i++) 
        {
            urlStr += cityIds.get(i);
            if (i < count - 1) 
            {
                urlStr += ",";
            }
        }
        urlStr += "&units=metric&appid=" + apiKey;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");

        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        conn.disconnect();

        return sb.toString();
    }
}
