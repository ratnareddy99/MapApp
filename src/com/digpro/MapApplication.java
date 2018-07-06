package com.digpro;

import com.digpro.codetest.MarkerInfo;
import com.digpro.codetest.Services;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapApplication extends Application implements MapComponentInitializedListener {
   protected GoogleMapView mapComponent;
    protected GoogleMap map;

    private Button btnReload;
    private Button btnStopAutoReload;

    private void checkCenter(LatLong center) {

    }

    public void start(final Stage stage) throws Exception {


        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInitializedListener(this);
        BorderPane bp = new BorderPane();
        ToolBar tb = new ToolBar();

        btnReload = new Button("Reload");
        btnReload.setOnAction(e -> {
            reload();
        });
        btnStopAutoReload = new Button("Stop Auto Reload");
        tb.getItems().addAll(btnReload, btnStopAutoReload);
        bp.setTop(tb);
        bp.setCenter(mapComponent);
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void mapInitialized() {
        LatLong center = new LatLong(47.606189, -122.335842);
        mapComponent.addMapReadyListener(() -> {
            checkCenter(center);
        });

        MapOptions options = new MapOptions();
        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN)
                .clickableIcons(false)
                .disableDefaultUI(false)
                .disableDoubleClickZoom(true)
                .keyboardShortcuts(false)
                .styleString("[{'featureType':'landscape','stylers':[{'saturation':-100},{'lightness':65},{'visibility':'on'}]},{'featureType':'poi','stylers':[{'saturation':-100},{'lightness':51},{'visibility':'simplified'}]},{'featureType':'road.highway','stylers':[{'saturation':-100},{'visibility':'simplified'}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]");

        map = mapComponent.createMap(options, false);
        map.setHeading(123.2);

       map.fitBounds(new LatLongBounds(new LatLong(100.606189, -122.335842), center));

        List<Marker> mList = new ArrayList<>();

        List<MarkerInfo> markersList = new Services().readCoordinatesFromURL();
        for (MarkerInfo markerInfo : markersList
                ) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong markerLatLong = new LatLong(markerInfo.getLatitude(), markerInfo.getLongitude());
            markerOptions.position(markerLatLong)
                    .label(markerInfo.getName())
                    .visible(true);
            Marker mark = new Marker(markerOptions);
            mList.add(mark);
        }


        map.addMarkers(mList);

    }

    public void reload() {
        map.clearMarkers();
        mapInitialized();
    }

}
