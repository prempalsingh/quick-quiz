package byldathon.com.quickquiz;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by prempal on 14/3/15.
 */
public class ApplicationWrapper extends Application {
    public static final boolean LOG_DEBUG = true;
    public static final boolean LOG_INFO = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "m0BbiSlRIFuhv4LrYq74sK9N08pT3gr2aLFLqzcI", "375T2to7u9lAzBfB6tiTdi1WLzZwRfogSr41JNk0");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
    }
}