package com.ingreatsol.calibrador.viewmodel;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapLoteViewModel extends AndroidViewModel {

    private IMapController mapController;
    private double zoom;
    private MapView map;

    public MapLoteViewModel(@NonNull Application application) {
        super(application);
        Configuration.getInstance().load(application.getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext()));
    }

    public void checkPermissions(){

    }

    private void configureMap(){
        mapController = this.getMap().getController();
        this.setZoom(16D);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        map.setHasTransientState(true);

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplication().getApplicationContext()), map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);

        DisplayMetrics displayMetrics = getApplication().getApplicationContext().getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(displayMetrics.widthPixels / 2, 10);
        map.getOverlays().add(mScaleBarOverlay);
    }

    public MapView getMap() {
        return map;
    }

    public void setMap(MapView map) {
        this.map = map;
        this.configureMap();
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
        mapController.setZoom(this.zoom);
    }

    public IMapController getMapController() {
        return mapController;
    }
}
