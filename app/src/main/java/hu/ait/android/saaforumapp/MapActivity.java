package hu.ait.android.saaforumapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        final LatLng rhodes = new LatLng(35.15, -89.9893);
        setupRhodesMarker(rhodes);
        final LatLng sewanee = new LatLng(35.2052, -85.9177);
        setupSewaneeMarker(sewanee);
        final LatLng centre = new LatLng(37.6457, -84.7815);
        setupCentreMarker(centre);
        final LatLng oglethorpe = new LatLng(33.8762, -84.3347);
        setupOglethorpeMarker(oglethorpe);
        final LatLng bsc = new LatLng(33.5159, -86.8515);
        setupBSCMarker(bsc);
        final LatLng berry = new LatLng(34.2904, -85.1892);
        setupBerryMarker(berry);
        final LatLng hendrix = new LatLng(35.1000, -92.4420);
        setupHendrixMarker(hendrix);
        final LatLng millsaps = new LatLng(32.3224, -90.1783);
        setupMillsapsMarker(millsaps);

        onMarkerTouch(rhodes, sewanee, centre, oglethorpe, bsc, berry, hendrix, millsaps);
    }

    private void onMarkerTouch(final LatLng rhodes, final LatLng sewanee, final LatLng centre, final LatLng oglethorpe, final LatLng bsc, final LatLng berry, final LatLng hendrix, final LatLng millsaps) {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getPosition().equals(rhodes)) {
                    Toast.makeText(MapActivity.this, R.string.rhodes_college_toast, Toast.LENGTH_SHORT).show();
                    return true;
                } else if (marker.getPosition().equals(sewanee)) {
                    Toast.makeText(MapActivity.this, R.string.sewanee_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else if (marker.getPosition().equals(centre)) {
                    Toast.makeText(MapActivity.this, R.string.centre_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else if (marker.getPosition().equals(oglethorpe)) {
                    Toast.makeText(MapActivity.this, R.string.oglethorpe_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else if (marker.getPosition().equals(bsc)) {
                    Toast.makeText(MapActivity.this, R.string.bsc_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else if (marker.getPosition().equals(berry)) {
                    Toast.makeText(MapActivity.this, R.string.berry_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else if (marker.getPosition().equals(hendrix)) {
                    Toast.makeText(MapActivity.this, R.string.hendrix_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else if (marker.getPosition().equals(millsaps)) {
                    Toast.makeText(MapActivity.this, R.string.millsaps_toast,
                            Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    return true;
                }
            }
        });
    }

    private void setupMillsapsMarker(LatLng millsaps) {
        MarkerOptions millsapsMarker = new MarkerOptions().position(millsaps).title(getString(R.string.millsaps_majors));
        millsapsMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("millsaps", 72, 64)));
        mMap.addMarker(millsapsMarker);
    }

    private void setupHendrixMarker(LatLng hendrix) {
        MarkerOptions hendrixMarker = new MarkerOptions().position(hendrix).title(getString(R.string.hendrix_warriors));
        hendrixMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("hendrix", 72, 64)));
        mMap.addMarker(hendrixMarker);
    }

    private void setupBerryMarker(LatLng berry) {
        MarkerOptions berryMarker = new MarkerOptions().position(berry).title(getString(R.string.berry_vikings));
        berryMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("berry", 72, 64)));
        mMap.addMarker(berryMarker);
    }

    private void setupBSCMarker(LatLng bsc) {
        MarkerOptions bscMarker = new MarkerOptions().position(bsc).title(getString(R.string.bsc_panthers));
        bscMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("bsc", 72, 64)));
        mMap.addMarker(bscMarker);
    }

    private void setupOglethorpeMarker(LatLng oglethorpe) {
        MarkerOptions oglethorpeMarker = new MarkerOptions().position(oglethorpe).title(getString(R.string.oglethorpe_petrels));
        oglethorpeMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("oglethorpe", 72, 64)));
        mMap.addMarker(oglethorpeMarker);
    }

    private void setupCentreMarker(LatLng centre) {
        MarkerOptions centreMarker = new MarkerOptions().position(centre).title(getString(R.string.centre_college_colonels));
        centreMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("centre", 72, 64)));
        mMap.addMarker(centreMarker);
    }

    private void setupSewaneeMarker(LatLng sewanee) {
        MarkerOptions sewaneeMarker = new MarkerOptions().position(sewanee).title(getString(R.string.univ_of_south));
        sewaneeMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("sewanee", 72, 64)));
        mMap.addMarker(sewaneeMarker);
    }

    private void setupRhodesMarker(LatLng rhodes) {
        MarkerOptions rhodesMarker = new MarkerOptions().position(rhodes).title(getString(R.string.rhodes_lynx));
        rhodesMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("rhodes", 72, 64)));
        mMap.addMarker(rhodesMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rhodes));
    }

    public Bitmap resizeBitmap(String drawableName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(drawableName,
                "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }
}
