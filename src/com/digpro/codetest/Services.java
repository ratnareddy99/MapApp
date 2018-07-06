package com.digpro.codetest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Services {

    public List<MarkerInfo> readCoordinatesFromURL(){
        List<MarkerInfo> markersList=new ArrayList<>();
        try {
            URL game = new URL("http://daily.digpro.se/bios/servlet/bios.servlets.web.RecruitmentTestServlet");
            URLConnection connection = game.openConnection();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(connection.getInputStream(), Charset.forName("ISO-8859-1")));
            String inputLine;
            Pattern regex = Pattern.compile("[#]");
            String[] coordiantes;

            while ((inputLine = in.readLine()) != null) {
                Matcher m=regex.matcher(inputLine);
                if(!m.find()) {
                    MarkerInfo markerInfo=new MarkerInfo();
                    coordiantes = inputLine.split(",", 3);
                    markerInfo.setLongitude(Double.valueOf(coordiantes[0]));
                    markerInfo.setLatitude(Double.valueOf(coordiantes[1]));
                    markerInfo.setName(coordiantes[2]);
                    markersList.add(markerInfo);
                }

            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return markersList;
    }
}
