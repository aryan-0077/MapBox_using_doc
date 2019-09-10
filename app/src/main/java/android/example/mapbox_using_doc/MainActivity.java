package android.example.mapbox_using_doc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Integrating the Mapbox in our app ....
        Mapbox.getInstance(this,"sk.eyJ1IjoiYXJ5YW4tMDA3NyIsImEiOiJjazBkeXZuajgwNDVlM2dtcGRhem03ZXRkIn0.KG4B66PCuxcv282mU11W9A");

        setContentView(R.layout.activity_main);

        mapView = (MapView)findViewById(R.id.mapView);
        // Activities have the ability, under special circumstances, to restore themselves to a previous state
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
            // When the map is Ready we need to display user Current Location
            this.mapboxMap = mapboxMap;

            // We need to set the Style of map Box Map ( Leave it for Later )
            mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });



    }

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check If the Permissions are Enabled , if Not then Request for it
        if(PermissionsManager.areLocationPermissionsGranted(this)) {
            // Then we can Activate MapBox Location Component
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this,loadedMapStyle);
        }
    }
}
