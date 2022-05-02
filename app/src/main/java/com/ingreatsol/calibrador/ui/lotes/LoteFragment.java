package com.ingreatsol.calibrador.ui.lotes;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ingreatsol.calibrador.databinding.FragmentLoteBinding;
import com.ingreatsol.calibrador.viewmodel.LocationViewModel;
import com.ingreatsol.calibrador.viewmodel.LotesViewModel;
import com.ingreatsol.calibrador.viewmodel.MapLoteViewModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class LoteFragment extends Fragment {

    private FragmentLoteBinding binding;
    private Context ctx;

    private LotesViewModel mViewModel;
    private LocationViewModel locationViewModel;
    private MapLoteViewModel mapLoteViewModel;
    private Button button = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = requireActivity().getApplicationContext();

        mViewModel = new ViewModelProvider(this).get(LotesViewModel.class);
        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        mapLoteViewModel = new ViewModelProvider(requireActivity()).get(MapLoteViewModel.class);
        binding = FragmentLoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapLoteViewModel.setMap(binding.map);

        button = binding.buttonIniciarMedicion;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Location location = locationViewModel.getLocation().getValue();
        if (location != null){
            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            mapLoteViewModel.getMapController().setCenter(startPoint);
        }

        button.setOnClickListener(v -> {
            if (!locationViewModel.canGetLocation())
                locationViewModel.startUsingGPS();
            else
                locationViewModel.stopUsingGPS();
        });
    }

    public void IniciarMedicion(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mapLoteViewModel.getMap().onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        mapLoteViewModel.getMap().onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}