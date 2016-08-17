package mobello.amtrust.com.utility;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import org.acra.ACRA;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mobello.amtrust.com.widget.ProgressHUD;

/**
 * Created by Parthi on 18-May-16.
 */
public class Helper {

    public static ProgressHUD progressHUD;

    public static void showProgress(Context context) {
        if (context != null) {
            progressHUD = ProgressHUD.show(context, "Loading...", true, true);
            progressHUD.setCancelable(false);
        }
    }

    public static void dismissProgress() {
        progressHUD.dismiss();
    }

    public static String getCountryCode(Context context){
        if(context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String countryIso = telephonyManager.getSimCountryIso();
            return Iso2Phone.getPhone(countryIso);
        }
        return  null;
    }

    public static String getIMEINumber(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static void showMessageToast(Context context, String msg) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void sendReport(Context context , Exception exception){
        if(context != null) {
            BugSender bugReportSender = new BugSender(context, exception);
            ACRA.getErrorReporter().setReportSender(bugReportSender);
        }
    }

    public static final String convertMd5(final String key) {
        try {
            if (key != null) {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(key.getBytes());
                byte messageDigest[] = digest.digest();

                StringBuffer hexString = new StringBuffer();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        else
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Bitmap rotateBitmap(Context context, String imagePath) {
        try {
            Bitmap bmRotated = null;
            if(context != null && imagePath != null) {
                ExifInterface exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                Matrix matrix = new Matrix();
                Bitmap d = new BitmapDrawable(context.getResources(), imagePath).getBitmap();
                if(d != null) {
                    int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap bitmap = Bitmap.createScaledBitmap(d, 512, nh, true);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_NORMAL:
                            return bitmap;
                        case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                            matrix.setScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.setRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                            matrix.setRotate(180);
                            matrix.postScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_TRANSPOSE:
                            matrix.setRotate(90);
                            matrix.postScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.setRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_TRANSVERSE:
                            matrix.setRotate(-90);
                            matrix.postScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.setRotate(-90);
                            break;
                        default:
                            return bitmap;
                    }
                    bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                }
            }
            return bmRotated;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = AmTrustApp.getInstance().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
