package nwhacks.tutormeupdated.Database;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;

/**
 * Created by Stephen-PC on 1/28/2017.
 */

public abstract class DbConnection {
    private static Firebase rootReference;
    private static GeoFire geoFire;

    public static void initDBConnection(){
        rootReference = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");
        geoFire = new GeoFire(rootReference);
    }

    public static GeoFire getGeoFire(){
        return geoFire;
    }

    public static Firebase getRootReference(){
        return rootReference;
    }


}
