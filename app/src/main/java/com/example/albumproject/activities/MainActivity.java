package com.example.albumproject.activities;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.albumproject.BuildConfig;
import com.example.albumproject.R;
import com.example.albumproject.adapters.MyPagerAdapter;
import com.example.albumproject.data.Constant;
import com.example.albumproject.data.ImageData;
import com.example.albumproject.models.FileMainModel;
import com.example.albumproject.models.FolderMainModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import android.provider.MediaStore.Images;

public class MainActivity extends AppCompatActivity
        implements PopupMenu.OnMenuItemClickListener {
    TextView txtTitle;
    View btnBack;
    View btnSearch;
    View btnCamera;
    TabLayout tabLayout;
    ViewPager viewPager;
    //    int REQUEST_PERMISSION = 11;
    ArrayList<FileMainModel> listImage;
    int limitList = 10000;
    int offsetList = 0;
    boolean isMore = true;
    boolean isLoad = false;
    ImageView imgAvatar;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    //capture a photo
    public static final int REQUEST_PERMISSION = 11;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_WRITE = 101;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private String mCurrentPhotoPath;
//    static final int REQUEST_IMAGE_CAPTURE = 1;

    static final int CAPTURE_IMAGE_REQUEST = 1;
    File photoFile = null;
    Uri photoURI = null;
    Boolean isLoginSuccess = false;
    MyPagerAdapter adapter;

    ArrayList<FileMainModel> listLibraryImage;
    ArrayList<FolderMainModel> listFolderImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
        getViews();
        addControl();
        initClick();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        listImage.clear();
        listLibraryImage.clear();
        loadListImage(offsetList, limitList);
        loadListLibraryImage(offsetList, limitList);
        listFolderImage = getPicturePaths();
        this.adapter.setNewData(listImage, listLibraryImage,listFolderImage);
    }

    @SuppressLint("ClickableViewAccessibility")
    void getViews() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnBack = (View) findViewById(R.id.btnBack);
        btnSearch = (View) findViewById(R.id.btnSearch);
        btnCamera = (View) findViewById(R.id.btnCamera);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        imgAvatar = (ImageView) findViewById(R.id.imgLoginGG);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        listImage = new ArrayList<>();
        listLibraryImage = new ArrayList<>();
        loadListImage(offsetList, limitList);
        loadListLibraryImage(offsetList, limitList);
        listFolderImage =  getPicturePaths();
    }

    private void addControl() {
        adapter = new MyPagerAdapter(getApplicationContext(),getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, listImage, listLibraryImage,listFolderImage);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    void initClick() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndOpenCamera();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(imgAvatar);
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogin:
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
                break;
            case R.id.itemLogout:
                if (isLoginSuccess == true) {
                    signOut();
                } else {
                    alertView("B???n vui l??ng ????ng nh???p tr?????c", getDrawable(R.drawable.ic_fail), "Th???t b???i");
                }

                break;
            default:
                break;
        }
        return true;
    }


    public void loadListImage(int skip, int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        }
        int item = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean permission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PERMISSION_GRANTED;
        final String[] columns = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN
        };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
//        MergeCursor cursor = new MergeCursor(new Cursor[]{
//                getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " ASC LIMIT " + limit + " OFFSET " + skip),
//        });

        MergeCursor cursor = new MergeCursor(new Cursor[]{
                getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC"),
        });
        if (cursor.getCount() == 0) {
            isMore = false;
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String url = cursor.getString(column_index);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String name = cursor.getString(column_index);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
            Long millis = cursor.getLong(column_index);
            ImageData data = new ImageData(name, url, "date");
            LocalDate date = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).toLocalDate();
            FileMainModel itemImage = new FileMainModel(date, getLibraryName(url.toString()));

            FileMainModel imageExist = listImage.stream()
                    .filter(image -> date.equals(image.date))
                    .findAny()
                    .orElse(null);

            if (imageExist != null) {
                imageExist.files.add(data);
            } else {
                itemImage.files.add(data);
                listImage.add(itemImage);
            }
            cursor.moveToNext();
        }
        offsetList += limit;
        if (listImage.size() % 10 == 0) {
            isLoad = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    loadListImage(offsetList, limitList);
                    loadListLibraryImage(offsetList, limitList);
                } else {
                    // User refused to grant permission.
                }
            }
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(MainActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }
            case MY_PERMISSIONS_REQUEST_WRITE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                }
                // other 'case' lines to check for other
                // permissions this app might request
        }
    }


    public void onMsgFromFragToMain(String sender, String strValue) {
        if (sender.equals("FRAGMENT_IMAGE")) {
            if (strValue.equals("loadMore")) {
                loadListImage(offsetList, limitList);
//                loadListLibraryImage(offsetList, limitList);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "[signInWithCredential:success]");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            isLoginSuccess = true;
                            user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                @Override
                                public void onSuccess(GetTokenResult result) {
                                    String idToken = result.getToken();
                                    //Do whatever
                                    Log.e(TAG, "[GetTokenResult result = ]" + idToken);
//                                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                                }
                            });
                        } else {
                            isLoginSuccess = false;
                            alertView("????ng nh???p nh???t b???i", getDrawable(R.drawable.ic_fail), "Th???t b???i");
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "[signInWithCredential:failure]", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e(TAG, "[firebaseAuthWithGoogle:]" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                handleSignInResult(task);
            } catch (ApiException e) {
                Log.e(TAG, "[Google sign in failed]", e);
            }
        } else if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo  = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Bitmap photo = null;
            try {
                File file1 = new File(mCurrentPhotoPath);
                if (file1.exists()) {
                    Log.e("IMAGE", file1.toString());
                }
                photo = BitmapFactory.decodeFile(mCurrentPhotoPath);
//                SaveImage(photo);

                long millis = System.currentTimeMillis();
                String fname = "Image-" + millis + ".jpg";
                saveBitmap(this, photo, Bitmap.CompressFormat.JPEG, "image/jpeg", fname);

                Log.e("IMAGE", photo.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(MainActivity.this);

                    }
                });
        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
//                displayMessage(getBaseContext(), photoFile.getAbsolutePath());
                Log.i("FILE", photoFile.getAbsolutePath());

                if (photoFile != null) {

                    photoURI = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".provider", photoFile);


                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                }
            } catch (Exception ex) {
//                displayMessage(getBaseContext(), ex.getMessage().toString());
                Log.e("[ERROR]", ex.getMessage().toString());
            }
        }
    }

    public void checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PERMISSION_GRANTED) {

            if (getFromPref(MainActivity.this, ALLOW_KEY)) {

                showSettingsAlert();

            } else if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA)
                    != PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        } else {
            openCamera();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private static void galleryAddPic(Context context, String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Albumproject");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        long millis = System.currentTimeMillis() % 1000;
        String fname = "Image-" + millis + ".jpg";
        File imageFile = new File(myDir, fname);
        String savedImagePath = null;
        savedImagePath = imageFile.getAbsolutePath();
        try {
            OutputStream fOut = new FileOutputStream(imageFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the image to the system gallery
        galleryAddPic(this, savedImagePath);

        // Show a Toast with the save location
        // String savedMessage = context.getString(R.string.saved_message, savedImagePath);
    }

    public Uri saveBitmap(@NonNull final Context context, @NonNull final Bitmap bitmap,
                          @NonNull final Bitmap.CompressFormat format,
                          @NonNull final String mimeType,
                          @NonNull final String displayName) throws IOException {


        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/AlbumProject");
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
//        LocalDate date2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()).toLocalDate();
//        Log.d("OK",DateTimeFormatter.ofPattern("dd MMMM yyyy").format(date2));
        final ContentResolver resolver = context.getContentResolver();
        Uri uri = null;

        try {
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            uri = resolver.insert(contentUri, values);
            if (uri == null)
                throw new IOException("Failed to create new MediaStore record.");

            try (final OutputStream stream = resolver.openOutputStream(uri)) {
                if (stream == null)
                    throw new IOException("Failed to open output stream.");

                if (!bitmap.compress(format, 95, stream))
                    throw new IOException("Failed to save bitmap.");
            }

            Cursor cursor = null;
            try {
                final String[] columns = {MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DATE_TAKEN
                };
                cursor = getContentResolver().query(uri, columns, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                Log.e("OK", cursor.getString(column_index));
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
                String date = cursor.getString(column_index);
                Log.e("OK", date);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
                Long millis1 = cursor.getLong(column_index);
                LocalDate date1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis1), ZoneId.systemDefault()).toLocalDate();
                Log.e("OK", DateTimeFormatter.ofPattern("dd MMMM yyyy").format(date1));
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return uri;
        } catch (IOException e) {

            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null);
            }
            throw e;
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount acct) {
        acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Picasso.with(this).load(personPhoto).into(imgAvatar);
            alertView("????ng nh???p th??nh c??ng", getDrawable(R.drawable.ic_success), "Th??nh c??ng");
        } else {
            imgAvatar.setImageResource(R.drawable.ic_account_circle);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
//                        revokeAccess();
                        alertView("????ng xu???t th??nh c??ng", getDrawable(R.drawable.ic_success), "Th??nh c??ng");
                    }
                });
    }

    private String getLibraryName(String url) {
        String result = "";
        if (url.contains("Screenshot")) {
            result = Constant.SCREEN_SHOTS;
        } else if (url.contains("Messenger")) {
            result = Constant.MESSENGER;
        } else if (url.contains("Picture")) {
            result = Constant.CAMERA;
        } else if (url.contains("Facebook")) {
            result = Constant.FACEBOOK;
        } else if (url.contains("Zalo")) {
            result = Constant.ZALO;
        }
        return result;
    }

    public void loadListLibraryImage(int skip, int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        }
        final String[] columns = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };
        final String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        MergeCursor cursor = new MergeCursor(new Cursor[]{
                getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null,
                        orderBy + " DESC LIMIT " + limit + " OFFSET " + skip),
        });
        if (cursor.getCount() == 0) {
            isMore = false;
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String url = cursor.getString(column_index);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String name = cursor.getString(column_index);
            Long millis = cursor.getLong(column_index);
            ImageData data = new ImageData(name, url, "date");
            LocalDate date = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).toLocalDate();
            FileMainModel itemLibraryImage = new FileMainModel(date, getLibraryName(url.toString()));
            FileMainModel imageLibraryExist = listLibraryImage.stream()
                    .filter(image -> getLibraryName(url).equals(getLibraryName(image.files.toString())))
                    .findAny()
                    .orElse(null);
            if (imageLibraryExist != null) {
                imageLibraryExist.files.add(data);
            } else {
                itemLibraryImage.files.add(data);
                listLibraryImage.add(itemLibraryImage);
            }
            cursor.moveToNext();
        }
        offsetList += limit;
        if (listLibraryImage.size() % 10 == 0) {
            isLoad = false;
        }
    }

    private ArrayList<FolderMainModel> getPicturePaths(){
        ArrayList<FolderMainModel> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                FolderMainModel folds = new FolderMainModel();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);
                    folds.addPics();
                    picFolders.add(folds);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addPics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0;i < picFolders.size();i++){
            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
        }

        //reverse order ArrayList
       /* ArrayList<FolderMainModel> reverseFolders = new ArrayList<>();

        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
            reverseFolders.add(picFolders.get(i));
        }*/

        return picFolders;
    }

    private void alertView(String message, Drawable icon, String title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setIcon(icon)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }
}

