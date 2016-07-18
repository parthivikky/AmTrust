package mobello.amtrust.com.utility;

import android.os.FileObserver;
import android.util.Log;

/**
 * Created by Parthi on 07-Jul-16.
 */
public class FileTracker extends FileObserver {

    public String absolutePath;


    public FileTracker(String path) {
        super(path,FileObserver.CREATE);
        absolutePath = path;
    }

    @Override
    public void onEvent(int event, String path) {
        Log.i("screen shot", path);
        switch (event) {
            case FileObserver.ACCESS:
                Log.i("FileObserver", "ACCESS: " + path);
                break;
            case FileObserver.ATTRIB:
                Log.i("FileObserver", "ATTRIB: " + path);
                break;
            case FileObserver.CLOSE_NOWRITE:
                Log.i("FileObserver", "CLOSE_NOWRITE: " + path);
                break;
            case FileObserver.CLOSE_WRITE:
                Log.i("FileObserver", "CLOSE_WRITE: " + path);
                break;
            case FileObserver.CREATE:
                Log.i("FileObserver", "CREATE: " + path);
                break;
            case FileObserver.DELETE:
                Log.i("FileObserver", "DELETE: " + path);
                break;
            case FileObserver.DELETE_SELF:
                Log.i("FileObserver", "DELETE_SELF: " + path);
                break;
            case FileObserver.MODIFY:
                Log.i("FileObserver", "MODIFY: " + path);
                break;
            case FileObserver.MOVE_SELF:
                Log.i("FileObserver", "MOVE_SELF: " + path);
                break;
            case FileObserver.MOVED_FROM:
                Log.i("FileObserver", "MOVED_FROM: " + path);
                break;
            case FileObserver.MOVED_TO:
                Log.i("FileObserver", "MOVED_TO: " + path);
                break;
            case FileObserver.OPEN:
                Log.i("FileObserver", "OPEN: " + path);
                break;
            default:
                Log.i("FileObserver", "DEFAULT(" + event + "): " + path);
                break;
        }
    }
}
