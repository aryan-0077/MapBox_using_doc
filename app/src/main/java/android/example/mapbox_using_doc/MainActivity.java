package android.example.mapbox_using_doc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
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
    // LocationComponent -> Get the Location
    private LocationComponent locationComponent;
    // For Permissions
    private PermissionsManager permissionsManager;


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

    // PERMISSION WORK START FROM HERE >>>>
    @Override
    public void onPermissionResult(boolean granted) {
        if(granted) {
            enableLocationComponent(mapboxMap.getStyle());
        }else {
            Toast.makeText(getApplicationContext(),"Permission not Granted ",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }


    // WHEN THE MAP IS READY >>>>>
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

    // PERMISSIONS CHECK HERE >>>>
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check If the Permissions are Enabled , if Not then Request for it
        if(PermissionsManager.areLocationPermissionsGranted(this)) {
            // Then we can Activate MapBox Location Component
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this,loadedMapStyle);
            // We just Activated the Location Component now we just need to set it
            locationComponent.setLocationComponentEnabled(true);

            locationComponent.setCameraMode(CameraMode.TRACKING);
        }else {
            // If the Permission not Enabled then ask it ...
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
       // Then go to OnPermissionsResult .....
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
