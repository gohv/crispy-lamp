package view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.PlaceAdFragment;
import io.realm.Realm;
import model.Ad;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import model.LocationResults;
import xyz.georgihristov.myadds.MainActivity;
import xyz.georgihristov.myadds.R;


/**
 * Created by gohv on 03.03.17.
 */
public class ContactsActivity extends AppCompatActivity{
    private final String API_KEY = "AIzaSyCNg1jhuVQ7TpNi0LakymeNZTKnw8s20xI";//please dont use my key :)

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.numberEditText)EditText numberEditText;
    @BindView(R.id.fbRadioButton)RadioButton fbRadioButton;
    @BindView(R.id.locationRadioButton)RadioButton locationRadioButton;
    private LocationManager locationManager;
    private LocationResults result;
    private OkHttpClient client;
    private Request request;
    private Response response;
    private NetworkInfo networkInfo;
    private ConnectivityManager manager;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private Ad dataToSend;
    private Realm realm;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        result = new LocationResults();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        askPermission();
        realm = Realm.getDefaultInstance();
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();
        dataToSend = (Ad) getIntent().getParcelableExtra(PlaceAdFragment.MY_AD);


        locationRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermission();
                String address = result.getFormatted_address();
                if (address == null) {
                    Toast.makeText(ContactsActivity.this, "Getting Address..Please try again in a moment", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ContactsActivity.this, String.valueOf(result.getFormatted_address()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        fbRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkInfo == null) {
                    Toast.makeText(ContactsActivity.this, "Please Check your Internet Connection",
                            Toast.LENGTH_SHORT).show();
                } else {
                   fbShare();
                }
            }
        });



    }
    private boolean isMarshmallow() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private String getLocation(double lat, double lon) {
        String forecastURI =
                "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                        + lat + ","
                        + lon + "&key="
                        + API_KEY;
        return forecastURI;
    }

    private class Executor extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject ret = getLocationInfo(result.getLatitude(), result.getLongitude());
            JSONObject location;
            try {

                //Get JSON Array called "results" and then get the 0th complete object as JSON
                location = ret.getJSONArray("results").getJSONObject(0);
                // Get the value of the attribute whose name is "formatted_string"
                String location_string = location.getString("formatted_address");
                result.setFormatted_address(location_string);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    private InputStream getStream(String url) throws IOException {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(url)
                .build();
        response = client.newCall(request).execute();
        return response.body().byteStream();
    }

    public JSONObject getLocationInfo(double lat, double lng) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream stream = getStream(getLocation(lat, lng));
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            if (location != null) {
                result.setLatitude(location.getLatitude());
                result.setLongitude(location.getLongitude());
                getLocation(result.getLatitude(), result.getLongitude());
                new Executor().execute();

                //this was handled!
                locationManager.removeUpdates(this);
            } else if (location == null) {
                //handle

            }
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private void askPermission() {
        String[] neededPermissions = {"android.permission.ACCESS_FINE_LOCATION"};
        int permissionReqestCode = 200;
        if (isMarshmallow()) {
            requestPermissions(neededPermissions,permissionReqestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {


                    askPermission();
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                break;
        }
    }
    @OnClick(R.id.button) void save(){
        String number = numberEditText.getText().toString();
        dataToSend.setProductLocation(result.getFormatted_address());
        dataToSend.setContactNumber(number);
        basicCRUD(realm);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(PlaceAdFragment.MY_AD,dataToSend);
        Log.d("TEST",dataToSend.toString());
        startActivity(intent);
    }
    private void basicCRUD(Realm realm) {
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Ad ad = realm.createObject(Ad.class);
                String name = dataToSend.getProductName();
                String description = dataToSend.getProductDescription();
                String price = dataToSend.getProductPrice();
                String contactNumber = dataToSend.getContactNumber();
                String photo = dataToSend.getProductPhoto();
                String category = dataToSend.getCategory();
                String subCategory = dataToSend.getSubCategory();
                String location = dataToSend.getProductLocation();
                ad.setProductName(name);
                ad.setProductLocation(location);
                ad.setProductDescription(description);
                ad.setProductPrice(price);
                ad.setContactNumber(contactNumber);
                ad.setProductPhoto(photo);
                ad.setCategory(category);
                ad.setSubCategory(subCategory);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fbShare(){
        /*JUST AN EXAMPLE OF FB SHARE, IT WONT SHARE THE DATA FROM THE PARCEL OR DATABASE!*/

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(ContactsActivity.this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Hello Facebook")
                    .setContentDescription(
                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();
            SharePhoto sharePhoto1 = new SharePhoto.Builder()
                    .setImageUrl(Uri.parse(dataToSend.getProductPhoto()))
            .build();
            shareDialog.show(linkContent);
        }

    }
}
