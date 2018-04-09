package com.example.shurru.weather_forecast;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private final String apiKey="ukARpEXeA9PI9rz6XIlHIUwzmPY0lPEx";
     LocationManager locationmanager;
     LocationListener locationlistener;
     MainActivity activity;
     Location location;
     Context context;
     String result;
     String Current_WeatherText,CurrentIcon,Current_Pressure,Current_Prerssure_Unit,Current_Visibility,Current_Visibility_Unit,Current_Humidity,Current_Humidity_Unit,Current_IsDayTime,Current_TemperatureValue,Current_TemperatureUnit,CurrentWindSpeed,CurrentWindDirection,CurrentWindSpeed_Unit,CurrentWindDirection_Unit;
     String locationKey,localizedName,EffectiveDate,Text,Category,Link,administrativeArea;
     String SunRise,SunSet,TemperatureMin,TemperatureMax,TempUnit,HoursOfSun,DayIconPhrase,DayWindSpeed,DayWindDirection,DayWindSpeed_Unit,DayWindDirection_Unit,NightIconPhrase,NightWindSpeed,NightWindDirection,NightWindSpeed_Unit,NightWindDirection_Unit;
     String SunRise1,SunSet1,TemperatureMin1,TemperatureMax1,TempUnit1,HoursOfSun1,DayIconPhrase1,DayWindSpeed1,DayWindDirection1,DayWindSpeed_Unit1,DayWindDirection_Unit1,NightIconPhrase1,NightWindSpeed1,NightWindDirection1,NightWindSpeed_Unit1,NightWindDirection_Unit1;
     String SunRise2,SunSet2,TemperatureMin2,TemperatureMax2,TempUnit2,HoursOfSun2,DayIconPhrase2,DayWindSpeed2,DayWindDirection2,DayWindSpeed_Unit2,DayWindDirection_Unit2,NightIconPhrase2,NightWindSpeed2,NightWindDirection2,NightWindSpeed_Unit2,NightWindDirection_Unit2;
     String SunRise3,SunSet3,TemperatureMin3,TemperatureMax3,TempUnit3,HoursOfSun3,DayIconPhrase3,DayWindSpeed3,DayWindDirection3,DayWindSpeed_Unit3,DayWindDirection_Unit3,NightIconPhrase3,NightWindSpeed3,NightWindDirection3,NightWindSpeed_Unit3,NightWindDirection_Unit3;
     String SunRise4,SunSet4,TemperatureMin4,TemperatureMax4,TempUnit4,HoursOfSun4,DayIconPhrase4,DayWindSpeed4,DayWindDirection4,DayWindSpeed_Unit4,DayWindDirection_Unit4,NightIconPhrase4,NightWindSpeed4,NightWindDirection4,NightWindSpeed_Unit4,NightWindDirection_Unit4;
     int DayIcon1,NightIcon1,DayIcon2,NightIcon2,DayIcon3,NightIcon3,DayIcon4,NightIcon4;
     String date1,date2,date3,date4;
     String CurrentConditionsResult;
     String forecastResult;
     double longitude;
     double latitude;

     boolean gps_enabled,network_enabled;



    // For Hourly Results.......
    String HourlyResult;
    String[] DateTime=new String[12];
    int[] HourlyIcon=new int[12];
    boolean[] IsDayLight=new boolean[12];
    String[] HourlyIconPhrase=new String[12];
    int[] HourlyTemperatureValue=new int[12];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,100,this);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

       // Log.d("hey",result);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            AsyncTask myasynctask =new AsyncTask();
            myasynctask.execute();

            Toast.makeText(this, "lat" + latitude + "long" + longitude, Toast.LENGTH_LONG).show();


  // To Convert Lattitude and Longitude into Address.........

      /*  Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();


            Log.d("address",address);
            Log.d("state",state);
            Log.d("city",city);
            Log.d("country",country);
            Log.d("postal",postalCode);
            Log.d("knownName",knownName);
            textView=(TextView)findViewById(R.id.textview);
            textView.setText(city);


        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

   class AsyncTask extends android.os.AsyncTask<Void, Void, String>
    {
       ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {

            progressDialog.setTitle("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Fetching....");
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {


                String urll = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey="+apiKey+"&q=" +latitude+"%2C"+longitude;
                UrlConnection urlconnect = new UrlConnection();
                result = urlconnect.Url(urll);

                Log.d("result", urll);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        locationKey= jsonObject.getString("Key");
                        localizedName=jsonObject.getString("LocalizedName");
                        administrativeArea=jsonObject.getJSONObject("AdministrativeArea").getString("LocalizedName");

                        Log.d("result",String.valueOf(latitude));
                        Log.d("result",String.valueOf(longitude));
                        Log.d("result", locationKey);
                        Log.d("result",localizedName);
                        Log.d("result",administrativeArea);
                        //urlconnect.Weather(key);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

               //CURRENT CONDITIONS RESULT------------------>

                if(locationKey!=null)
                {
                    CurrentConditionsResult=urlconnect.CurrentConditions(locationKey);
                    if(CurrentConditionsResult!=null)
                    {
                        try{
                        JSONArray jsonArrayresult=new JSONArray(CurrentConditionsResult);
                        JSONObject jsonObject=jsonArrayresult.getJSONObject(0);

                                Current_WeatherText=jsonObject.getString("WeatherText");
                            Log.d("result",Current_WeatherText);
                                Current_IsDayTime=String.valueOf(jsonObject.getBoolean("IsDayTime"));
                            Log.d("result",Current_IsDayTime);
                                Current_TemperatureValue=String.valueOf(jsonObject.getJSONObject("Temperature").getJSONObject("Metric").getInt("Value"));
                            Log.d("result",Current_TemperatureValue);
                                Current_TemperatureUnit=jsonObject.getJSONObject("Temperature").getJSONObject("Metric").getString("Unit");
                            Log.d("result",Current_TemperatureUnit);
                                CurrentWindSpeed=String.valueOf(jsonObject.getJSONObject("Wind").getJSONObject("Speed").getJSONObject("Metric").getInt("Value"));
                            Log.d("result","CurrentWindSpeed\t"+CurrentWindSpeed);
                                CurrentWindDirection=String.valueOf(jsonObject.getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","CurrentWindDirection\t"+CurrentWindDirection);
                                CurrentWindSpeed_Unit=jsonObject.getJSONObject("Wind").getJSONObject("Speed").getJSONObject("Metric").getString("Unit");
                            Log.d("result","CurrentWindSpeed_Unit\t"+CurrentWindSpeed_Unit);
                                CurrentWindDirection_Unit=jsonObject.getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","CurrentWindDirection_Unit\t"+CurrentWindDirection_Unit);
                                CurrentIcon=String.valueOf(jsonObject.getInt("WeatherIcon"));
                            Current_Pressure=String.valueOf(jsonObject.getJSONObject("Pressure").getJSONObject("Metric").getInt("Value"));
                            Current_Prerssure_Unit=jsonObject.getJSONObject("Pressure").getJSONObject("Metric").getString("Unit");

                            Current_Humidity=String.valueOf(jsonObject.getJSONObject("DewPoint").getJSONObject("Metric").getInt("Value"));
                            Current_Humidity_Unit=jsonObject.getJSONObject("DewPoint").getJSONObject("Metric").getString("Unit");

                            Current_Visibility=String.valueOf(jsonObject.getJSONObject("Visibility").getJSONObject("Metric").getInt("Value"));
                            Current_Visibility_Unit=jsonObject.getJSONObject("Visibility").getJSONObject("Metric").getString("Unit");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    HourlyResult=urlconnect.HourlyData(locationKey);
                    Log.d("result",HourlyResult);

                        try{
                            JSONArray jsonArrayresult_hourly=new JSONArray(HourlyResult);

                            for(int i=0;i<12;i++)//total 12 hours.....
                            {
                                JSONObject jsonObject_hourly=jsonArrayresult_hourly.getJSONObject(i);
//DateTime":"2018-04-02T19:00:00+05:30",
//"EpochDateTime":1522675800,
//"WeatherIcon":35,
//"IconPhrase":"Partly cloudy",
//"IsDaylight":false,
//"Temperature"
                                DateTime[i]=jsonObject_hourly.getString("DateTime");
                                Log.d("result",DateTime[i]);

                                IsDayLight[i]=jsonObject_hourly.getBoolean("IsDaylight");
                                Log.d("result",String.valueOf(IsDayLight[i]));

                                HourlyTemperatureValue[i]=jsonObject_hourly.getJSONObject("Temperature").getInt("Value");
                                Log.d("result",String.valueOf(HourlyTemperatureValue[i]));

                                HourlyIcon[i]=jsonObject_hourly.getInt("WeatherIcon");
                                Log.d("result","CurrentWindSpeed\t"+String.valueOf(HourlyIcon[i]));

                                HourlyIconPhrase[i]=jsonObject_hourly.getString("IconPhrase");
                                Log.d("result","CurrentWindDirection\t"+HourlyIconPhrase[i]);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


        // HOURLY CONDITIONS RESULT....



            if(locationKey!=null)
                {
                    forecastResult=urlconnect.Weather(locationKey);
                    if(forecastResult!=null)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(forecastResult);

                            // Json Data from Object "headline".....
                                JSONObject jsonObject1=jsonObject.getJSONObject("Headline");
                                  EffectiveDate=jsonObject1.getString("EffectiveDate");
                                  Text=jsonObject1.getString("Text");
                                  Category=jsonObject1.getString("Category");
                                  Link=jsonObject1.getString("Link");

                            Log.d("result",EffectiveDate+"   "+Text+"   "+Category+"   "+Link);
                            // Json Data from Object "DailyForecasts".....

                                JSONArray jsonArray=jsonObject.getJSONArray("DailyForecasts");
     //For Day 1---->

                                JSONObject jsonobject2=jsonArray.getJSONObject(0);
                                SunRise= jsonobject2.getJSONObject("Sun").getString("Rise");
                                SunSet= jsonobject2.getJSONObject("Sun").getString("Set");

                            Log.d("result",SunRise+"  "+SunSet);
                                TemperatureMin=String.valueOf(jsonobject2.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value"));
                            Log.d("result","TemperatureMin\t"+TemperatureMin);
                                TempUnit=jsonobject2.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
                            Log.d("result","TempUnit\t"+TempUnit);
                                TemperatureMax=String.valueOf(jsonobject2.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value"));
                            Log.d("result","TemperatureMax\t"+TemperatureMax);
                            /*    HoursOfSun=String.valueOf(jsonobject2.getInt("HoursOfSun"));
                            Log.d("result","HoursOfSun\t"+HoursOfSun);
                                DayIconPhrase=jsonobject2.getJSONObject("Day").getString("IconPhrase");
                            Log.d("result","DayIconPhrase\t"+DayIconPhrase);
                                DayWindSpeed=String.valueOf(jsonobject2.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","DayWindSpeed\t"+DayWindSpeed);
                                DayWindSpeed_Unit=jsonobject2.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","DayWindSpeed_Unit\t"+DayWindSpeed_Unit);
                                DayWindDirection=String.valueOf(jsonobject2.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","DayWindDirection\t"+DayWindDirection);
                                DayWindDirection_Unit=jsonobject2.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","DayWindDirection_Unit\t"+DayWindDirection_Unit);

                                NightIconPhrase=jsonobject2.getJSONObject("Night").getString("IconPhrase");
                            Log.d("result","NightIconPhrase\t"+NightIconPhrase);
                                NightWindSpeed=String.valueOf(jsonobject2.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","NightWindSpeed\t"+NightWindSpeed);
                                NightWindDirection=String.valueOf(jsonobject2.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","NightWindDirection\t"+NightWindDirection);
                                NightWindSpeed_Unit=jsonobject2.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","NightWindSpeed_Unit\t"+NightWindSpeed_Unit);
                                NightWindDirection_Unit=jsonobject2.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","NightWindDirection_Unit\t"+NightWindDirection_Unit);
*/

//For Day 2--->
                            JSONObject jsonobject3=jsonArray.getJSONObject(1);
                            date1=jsonobject3.getString("Date").substring(0,10);
                            SunSet1= jsonobject3.getJSONObject("Sun").getString("Set");
                            Log.d("result",SunSet1);
                            TemperatureMin1=String.valueOf(jsonobject3.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value"));
                            Log.d("result","TemperatureMin\t"+TemperatureMin1);
                            TempUnit1=jsonobject3.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
                            Log.d("result","TempUnit\t"+TempUnit1);
                            TemperatureMax1=String.valueOf(jsonobject3.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value"));
                            Log.d("result","TemperatureMax\t"+TemperatureMax1);
                            DayIconPhrase1=jsonobject3.getJSONObject("Day").getString("IconPhrase");
                            Log.d("result","DayIconPhrase\t"+DayIconPhrase1);
                            NightIconPhrase1=jsonobject3.getJSONObject("Night").getString("IconPhrase");
                            Log.d("result","NightIconPhrase\t"+NightIconPhrase1);
                            DayIcon1=jsonobject3.getJSONObject("Day").getInt("Icon");
                            NightIcon1=jsonobject3.getJSONObject("Night").getInt("Icon");
                           /* SunRise1= jsonobject3.getJSONObject("Sun").getString("Rise");



                            HoursOfSun1=String.valueOf(jsonobject3.getInt("HoursOfSun"));
                            Log.d("result","HoursOfSun\t"+HoursOfSun1);

                            DayWindSpeed1=String.valueOf(jsonobject3.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","DayWindSpeed\t"+DayWindSpeed1);
                            DayWindSpeed_Unit1=jsonobject3.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","DayWindSpeed_Unit\t"+DayWindSpeed_Unit1);
                            DayWindDirection1=String.valueOf(jsonobject3.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","DayWindDirection\t"+DayWindDirection1);
                            DayWindDirection_Unit1=jsonobject3.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","DayWindDirection_Unit\t"+DayWindDirection_Unit1);


                            NightWindSpeed1=String.valueOf(jsonobject3.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","NightWindSpeed\t"+NightWindSpeed1);
                            NightWindDirection1=String.valueOf(jsonobject3.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","NightWindDirection\t"+NightWindDirection1);
                            NightWindSpeed_Unit1=jsonobject3.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","NightWindSpeed_Unit\t"+NightWindSpeed_Unit1);
                            NightWindDirection_Unit1=jsonobject3.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","NightWindDirection_Unit\t"+NightWindDirection_Unit1);
*/

   //For Day 3--->

                            JSONObject jsonobject4=jsonArray.getJSONObject(2);
                            date2=jsonobject4.getString("Date").substring(0,10);
                            SunSet2= jsonobject4.getJSONObject("Sun").getString("Set");
                            Log.d("result",SunSet2);
                            TemperatureMin2=String.valueOf(jsonobject4.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value"));
                            Log.d("result","TemperatureMin\t"+TemperatureMin2);
                            TempUnit2=jsonobject4.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
                            Log.d("result","TempUnit\t"+TempUnit2);
                            TemperatureMax2=String.valueOf(jsonobject4.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value"));
                            Log.d("result","TemperatureMax\t"+TemperatureMax2);
                            DayIconPhrase2=jsonobject4.getJSONObject("Day").getString("IconPhrase");
                            Log.d("result","DayIconPhrase\t"+DayIconPhrase2);
                            NightIconPhrase2=jsonobject4.getJSONObject("Night").getString("IconPhrase");
                            Log.d("result","NightIconPhrase\t"+NightIconPhrase2);
                            DayIcon2=jsonobject4.getJSONObject("Day").getInt("Icon");
                            NightIcon2=jsonobject4.getJSONObject("Night").getInt("Icon");
                           /* SunRise2= jsonobject4.getJSONObject("Sun").getString("Rise");



                            HoursOfSun2=String.valueOf(jsonobject4.getInt("HoursOfSun"));
                            Log.d("result","HoursOfSun\t"+HoursOfSun2);
                            DayWindSpeed2=String.valueOf(jsonobject4.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","DayWindSpeed\t"+DayWindSpeed2);
                            DayWindSpeed_Unit2=jsonobject4.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","DayWindSpeed_Unit\t"+DayWindSpeed_Unit2);
                            DayWindDirection2=String.valueOf(jsonobject4.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","DayWindDirection\t"+DayWindDirection2);
                            DayWindDirection_Unit2=jsonobject4.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","DayWindDirection_Unit\t"+DayWindDirection_Unit2);


                            NightWindSpeed2=String.valueOf(jsonobject4.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","NightWindSpeed\t"+NightWindSpeed2);
                            NightWindDirection2=String.valueOf(jsonobject4.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","NightWindDirection\t"+NightWindDirection2);
                            NightWindSpeed_Unit2=jsonobject4.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","NightWindSpeed_Unit\t"+NightWindSpeed_Unit2);
                            NightWindDirection_Unit2=jsonobject4.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","NightWindDirection_Unit\t"+NightWindDirection_Unit2);
*/
  //For Day 4--->

                            JSONObject jsonobject5=jsonArray.getJSONObject(3);
                            date3=jsonobject5.getString("Date").substring(0,10);
                            SunSet3= jsonobject5.getJSONObject("Sun").getString("Set");
                            Log.d("result",SunSet3);
                            TemperatureMin3=String.valueOf(jsonobject5.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value"));
                            Log.d("result","TemperatureMin\t"+TemperatureMin3);
                            TempUnit3=jsonobject5.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
                            Log.d("result","TempUnit\t"+TempUnit3);
                            TemperatureMax3=String.valueOf(jsonobject5.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value"));
                            Log.d("result","TemperatureMax\t"+TemperatureMax3);
                            DayIconPhrase3=jsonobject5.getJSONObject("Day").getString("IconPhrase");
                            Log.d("result","DayIconPhrase\t"+DayIconPhrase3);
                            NightIconPhrase3=jsonobject5.getJSONObject("Night").getString("IconPhrase");
                            Log.d("result","NightIconPhrase\t"+NightIconPhrase3);
                            DayIcon3=jsonobject5.getJSONObject("Day").getInt("Icon");
                            NightIcon3=jsonobject5.getJSONObject("Night").getInt("Icon");
                           /* SunRise3= jsonobject5.getJSONObject("Sun").getString("Rise");



                            HoursOfSun3=String.valueOf(jsonobject5.getInt("HoursOfSun"));
                            Log.d("result","HoursOfSun\t"+HoursOfSun3);

                            DayWindSpeed3=String.valueOf(jsonobject5.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","DayWindSpeed\t"+DayWindSpeed3);
                            DayWindSpeed_Unit3=jsonobject5.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","DayWindSpeed_Unit\t"+DayWindSpeed_Unit3);
                            DayWindDirection2=String.valueOf(jsonobject5.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","DayWindDirection\t"+DayWindDirection3);
                            DayWindDirection_Unit3=jsonobject5.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","DayWindDirection_Unit\t"+DayWindDirection_Unit3);


                            NightWindSpeed3=String.valueOf(jsonobject5.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","NightWindSpeed\t"+NightWindSpeed3);
                            NightWindDirection3=String.valueOf(jsonobject5.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","NightWindDirection\t"+NightWindDirection3);
                            NightWindSpeed_Unit3=jsonobject5.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","NightWindSpeed_Unit\t"+NightWindSpeed_Unit3);
                            NightWindDirection_Unit3=jsonobject5.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","NightWindDirection_Unit\t"+NightWindDirection_Unit3);
                        */
    //For Day 5-->

                            JSONObject jsonobject6=jsonArray.getJSONObject(4);
                            date4=(jsonobject6.getString("Date")).substring(0,10);
                            SunSet4= jsonobject6.getJSONObject("Sun").getString("Set");
                            Log.d("result",SunSet4);

                            TemperatureMin4=String.valueOf(jsonobject6.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value"));
                            Log.d("result","TemperatureMin\t"+TemperatureMin4);
                            TempUnit4=jsonobject6.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
                            Log.d("result","TempUnit\t"+TempUnit4);
                            TemperatureMax4=String.valueOf(jsonobject6.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value"));
                            Log.d("result","TemperatureMax\t"+TemperatureMax4);
                            DayIconPhrase4=jsonobject6.getJSONObject("Day").getString("IconPhrase");
                            Log.d("result","DayIconPhrase\t"+DayIconPhrase4);
                            NightIconPhrase4=jsonobject6.getJSONObject("Night").getString("IconPhrase");
                            Log.d("result","NightIconPhrase\t"+NightIconPhrase4);
                            DayIcon4=jsonobject6.getJSONObject("Day").getInt("Icon");
                            NightIcon4=jsonobject6.getJSONObject("Night").getInt("Icon");
                          /*  SunRise4= jsonobject6.getJSONObject("Sun").getString("Rise");

                            HoursOfSun4=String.valueOf(jsonobject6.getInt("HoursOfSun"));
                            Log.d("result","HoursOfSun\t"+HoursOfSun4);

                            DayWindSpeed4=String.valueOf(jsonobject6.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","DayWindSpeed\t"+DayWindSpeed4);
                            DayWindSpeed_Unit4=jsonobject6.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","DayWindSpeed_Unit\t"+DayWindSpeed_Unit4);
                            DayWindDirection4=String.valueOf(jsonobject6.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","DayWindDirection\t"+DayWindDirection4);
                            DayWindDirection_Unit4=jsonobject6.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","DayWindDirection_Unit\t"+DayWindDirection_Unit4);


                            NightWindSpeed4=String.valueOf(jsonobject6.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getInt("Value"));
                            Log.d("result","NightWindSpeed\t"+NightWindSpeed4);
                            NightWindDirection4=String.valueOf(jsonobject6.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getInt("Degrees"));
                            Log.d("result","NightWindDirection\t"+NightWindDirection4);
                            NightWindSpeed_Unit4=jsonobject6.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Speed").getString("Unit");
                            Log.d("result","NightWindSpeed_Unit\t"+NightWindSpeed_Unit4);
                            NightWindDirection_Unit4=jsonobject6.getJSONObject("Night").getJSONObject("Wind").getJSONObject("Direction").getString("Localized");
                            Log.d("result","NightWindDirection_Unit\t"+NightWindDirection_Unit4);

                           //Log.d("result",HoursOfSun+"  "+DayIconPhrase+"   "+DayWindSpeed+"   "+DayWindSpeed_Unit);
                           //Log.d("result",DayWindDirection+"   "+DayWindDirection_Unit);
                           // Log.d("result",NightIconPhrase+"   "+NightWindSpeed);
                           // Log.d("result",NightWindSpeed_Unit+"   "+NightWindDirection+"   "+NightWindDirection_Unit);
*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            return "";
            }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String aVoid) {
            if (progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }

            //View v=getLayoutInflater().inflate(R.layout.list,null,false);
            setContentView(R.layout.list);
            try {
                WeatherData wd=new WeatherData(activity);
            } catch (ParseException e) {
                e.printStackTrace();
            }


          /*  final SwipeRefreshLayout mySwipeRefreshLayout=findViewById(R.id.swiperefreshlayout);
            mySwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            setContentView(R.layout.list);
                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            try {
                                WeatherData wd=new WeatherData(activity);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            finally {
                            mySwipeRefreshLayout.setRefreshing(false);
                        }
                        }
                    }
            );*/

            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);

        a_builder.setMessage("Do you want to Close this App !!!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }) ;
        AlertDialog alert = a_builder.create();
        alert.setTitle("Alert !!!");
        alert.show();
    }


}

