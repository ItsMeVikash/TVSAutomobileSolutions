package vikash.kumar.tvsautomobilesolutions.util;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

import vikash.kumar.tvsautomobilesolutions.BuildConfig;
import vikash.kumar.tvsautomobilesolutions.activities.StaffDetails;

public class CameraUtils {
    public static boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static Bitmap optimizeBitmap(int sampleSize, String filePath) {
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }
    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    public static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static Uri getOutputMediaFileUri(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }

    /**
     * Creates and returns the image or video file before opening the camera
     */
    public static File getOutputMediaFile(int type,Context context) {
        String name = "pic" + ".jpg";
        File cache = context.getExternalCacheDir();
        File mediaFile;

        if (type == StaffDetails.MEDIA_TYPE_IMAGE) {
            mediaFile= new File(cache, name);
        } else {
            return null;
        }

        return mediaFile;
    }

}