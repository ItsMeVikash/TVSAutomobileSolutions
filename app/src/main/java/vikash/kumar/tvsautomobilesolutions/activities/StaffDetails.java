package vikash.kumar.tvsautomobilesolutions.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vikash.kumar.tvsautomobilesolutions.R;
import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;
import vikash.kumar.tvsautomobilesolutions.util.CameraUtils;
import vikash.kumar.tvsautomobilesolutions.util.LoginSharedPreferences;

public class StaffDetails extends AppCompatActivity {

    private TextView details;
    private String name;
    private ArrayList<DataModel> detailsList;
    private ImageView imageView;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static String imageStoragePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);
        details=findViewById(R.id.details);
        detailsList=LoginSharedPreferences.getArrayList(getApplicationContext());
        name = getIntent().getStringExtra("name");
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (DataModel dataModel:detailsList){
           if (dataModel.getName().equals(name)){
               String texts="<br/><b>Name:  </b>"+dataModel.getName()+
                            "<br/><br/><b>Post:  </b>"+dataModel.getPost()+
                            "<br/><br/><b>Location:  </b>"+dataModel.getLocation()+
                            "<br/><br/><b>Code:  </b>"+dataModel.getCode()+
                            "<br/><br/><b>Date:  </b>"+dataModel.getDate_of_joining()+
                            "<br/><br/><b>Salary:  </b>"+dataModel.getSalary();
               details.setText(Html.fromHtml(texts));
           }
        }
        imageView = findViewById(R.id.imageView1);

    }
    public void CameraClick(View v) {
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }
        if (CameraUtils.checkPermissions(getApplicationContext())) {
            captureImage();
        } else {
            requestCameraPermission(MEDIA_TYPE_IMAGE);
        }

    }
    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            } else {

                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(StaffDetails.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = sdf.format(Calendar.getInstance().getTime());
                WatermarkText watermarkText = new WatermarkText(dateTime)
                        .setPositionX(0)
                        .setPositionY(0)
                        .setTextColor(Color.WHITE)
                        .setTextFont(R.font.champagne)
                        .setTextShadow(0.1f, 5, 5, Color.BLUE)
                        .setTextAlpha(500)
                        .setRotation(0)
                        .setTextSize(20);
                WatermarkBuilder
                        .create(this, bitmap)
                        .loadWatermarkText(watermarkText)
                        .getWatermark()
                        .setToImageView(imageView);

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE,getApplicationContext());
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StaffDetails.this);
        builder.setTitle("Need Permissions !!");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                 this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
