package com.fortuna.rgpmobile.relawan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fortuna.rgpmobile.DB.TbKabupaten;
import com.fortuna.rgpmobile.DB.TbKecamatan;
import com.fortuna.rgpmobile.DB.TbKelurahan;
import com.fortuna.rgpmobile.DB.TbPenugasanPulau;
import com.fortuna.rgpmobile.DB.TbProvinsi;
import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.util.ConnectivityWS;
import com.fortuna.rgpmobile.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class DaftarKoordinatorActivity extends BaseActivity {

    Toolbar toolbar_daftar_koor;
    private EditText editNIK, editNama, editAlamat, editNoHp, editTPS, editEmail, editNamaReferensi, editPasswordAwal, editKodePos, editKonfirmPasswordAwal, editKeterangan, editRT, editRW;
    private ImageView imageFotoKTP;
    private Spinner spinPenugasan, spinKelurahan, spinProvinsi, spinKabupaten, spinKecamatan;
    private Button btnSimpan, btnCari, btnFoto, btnCekKdPos, btnEditRelawan;
    private String image;
    private String kdArea;
    private String idRelawanupline;
    private String userID;
    private String idRelawan;
    private String KD_PENUGASAN;
    private String NM_PENUGASAN;
    private String KD_PROP;
    private String NM_PROP;
    private String KD_KOTAKAB;
    private String NM_KOTAKAB;
    private String provinsi;
    private String kabupaten;
    private String kecamatan, idRelawanUpline2;
    private String KD_KEC;
    private String NM_KEC;
    private String KODEPOS;
    private String kelurahan;
    private String KD_KEL;
    private String NM_KEL;
    private String kdProp;
    private String kabkode;
    private String kecKode;
    private String kode_pos;
    private String kdKel;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int SCANNER_OCR = 1;

    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory().toString() + "/RGP/Foto/";
    protected static final String PHOTO_TAKEN = "photo_taken";
    private static final String TAG = "RGPOCRActivity.java";
    public static final String lang = "eng";
    protected String _path;
    protected boolean _taken;
    private String fileName;
    protected Uri outputFileUri = null;
    Uri output = null;

    Snackbar snackbar;
    private SpinnerAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    String kudo, kdPenugasan;
    private String kdKordArea;

    public String getKudo() {
        return kudo;
    }

    public void setKudo(String kudo) {
        this.kudo = kudo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_daftar_koordinator);


        setupToolbar();
        initComponent();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_daftar_koor = findViewById(R.id.toolbar_daftar_koor);
        setSupportActionBar(toolbar_daftar_koor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @SuppressLint("LongLogTag")
    private void initComponent() {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        kdKordArea = String.valueOf(pref.getString("kdAreaTugasKey", ""));

        editNIK = findViewById(R.id.editNIK);
        editNama = findViewById(R.id.editNama);
        editAlamat = findViewById(R.id.editAlamat);
        editNoHp = findViewById(R.id.editNoHp);
        editEmail = findViewById(R.id.editEmail);
        editTPS = findViewById(R.id.editTPS);
        editNamaReferensi = findViewById(R.id.editNamaReferensi);
        editPasswordAwal = findViewById(R.id.editPasswordAwal);
        editKodePos = findViewById(R.id.editKodePos);
        editKonfirmPasswordAwal = findViewById(R.id.editKonfirmPasswordAwal);
        editKeterangan = findViewById(R.id.editKeterangan);
        editRT = findViewById(R.id.editRT);
        editRW = findViewById(R.id.editRW);

        /*editProv.setEnabled(false);
        editKota.setEnabled(false);
        editKecamatan.setEnabled(false);*/

        imageFotoKTP = findViewById(R.id.imageFotoKTP);
        spinPenugasan = findViewById(R.id.spinPenugasan);
        spinKelurahan = findViewById(R.id.spinKelurahan);
        spinProvinsi = findViewById(R.id.spinProvinsi);
        spinKabupaten = findViewById(R.id.spinKabupaten);
        spinKecamatan = findViewById(R.id.spinKecamatan);

        spinProvinsi.setOnItemSelectedListener(new SpinProvinsiClick());
        spinKabupaten.setOnItemSelectedListener(new SpinKabupatenClick());
        spinKecamatan.setOnItemSelectedListener(new SpinKecamatanClick());
        spinKelurahan.setOnItemSelectedListener(new SpinKelurahanClick());

        editKodePos.setEnabled(false);
        editNama.setEnabled(false);
        editAlamat.setEnabled(false);
        spinPenugasan.setEnabled(false);
        editTPS.setEnabled(false);
        editNoHp.setEnabled(false);
        editEmail.setEnabled(false);
        editNamaReferensi.setEnabled(false);
        editPasswordAwal.setEnabled(false);
        editKonfirmPasswordAwal.setEnabled(false);
        editKeterangan.setEnabled(false);
        editRT.setEnabled(false);
        editRW.setEnabled(false);

        btnCekKdPos = findViewById(R.id.btnCekKdPos);
        btnCekKdPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKodePos.requestFocus();
                String kdpos = editKodePos.getText().toString();
                if (kdpos.isEmpty()) {
                    snackbar = Snackbar.make(coordinatorLayout, "NIK dan Kode Pos tidak boleh kosong!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    checkKodePos();
                    editNama.setEnabled(true);
                    editAlamat.setEnabled(true);
                    spinKelurahan.setEnabled(true);
                    spinPenugasan.setEnabled(true);
                    editNoHp.setEnabled(true);
                    editTPS.setEnabled(true);
                    editEmail.setEnabled(true);
                    editNamaReferensi.setEnabled(true);
                    editPasswordAwal.setEnabled(true);
                    editKonfirmPasswordAwal.setEnabled(true);
                    editKeterangan.setEnabled(true);
                    editRT.setEnabled(true);
                    editRW.setEnabled(true);
                }
            }
        });

        btnEditRelawan = findViewById(R.id.btnEdit);
        btnEditRelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRelawan();
            }
        });

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNIK.requestFocus();
                String nik = editNIK.getText().toString();
                if (nik.isEmpty()) {
                    snackbar = Snackbar.make(coordinatorLayout, "NIK dan Kode Pos tidak boleh kosong!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    uploadData();
                }

            }
        });

        btnCari = findViewById(R.id.btnCek);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNIK.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                editNIK.requestFocus();
                String nik = editNIK.getText().toString();
                if (nik.isEmpty()) {
                    snackbar = Snackbar.make(coordinatorLayout, "NIK dan Kode Pos tidak boleh kosong!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (nik.length() < 16) {
                    snackbar = Snackbar.make(coordinatorLayout, "NIK tidak valid!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    cekRelawan();
                    editKodePos.setEnabled(true);

                }
            }
        });

        btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                if (editNIK.getText().toString().length() == 0)
                    Util.showmsg(DaftarKoordinatorActivity.this, "Peringatan :", getString(R.string.msg_scan_pelanggan));
                else {
                    Log.v("SCANNER_OCR", "Starting Camera app");
                    startCameraActivity(view);
                }

                /*if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }*/

            }
        });

        coordinatorLayout = findViewById(R.id.coordinatorLayoutKonfig);

        editKodePos.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)) {

                    checkKodePos();

                    return true;

                }
                return false;
            }
        });

        String[] paths = new String[]{PHOTO_PATH, PHOTO_PATH + "tessdata/"};

        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path
                            + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }

        if (!(new File(PHOTO_PATH + "tessdata/" + lang + ".traineddata"))
                .exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/eng.traineddata");
                // GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(PHOTO_PATH
                        + "tessdata/eng.traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                // while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                // gin.close();
                out.close();

                Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                Log.e(TAG,
                        "Was unable to copy " + lang + " traineddata " + e.toString());
            }
        }

        // init2
        File dir2 = new File(PHOTO_PATH);
        if (!dir2.exists()) {
            if (!dir2.mkdirs()) {
                Log.v("ISA", "ERROR: Creation of directory " + PHOTO_PATH + " on sdcard failed");
            } else {
                Log.v("ISA", "Created directory " + PHOTO_PATH + " on sdcard");
            }
        }

        loadSpinnerProvinsi();

    }

    // Simple android photo capture: http://labs.makemachine.net/2010/03/simple-android-photo-capture/
    protected void startCameraActivity(View v) {

        fileName = composeFileFoto();
        _path = PHOTO_PATH + fileName;

        File file = new File(_path);
        output = Uri.fromFile(file);
        //outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, SCANNER_OCR);

    }

    /* Nama Foto */
    String composeFileFoto() {
        return "KTP_" + editNIK + ".jpg";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageFotoKTP.setImageBitmap(photo);
        }*/

        Log.i("SCANNER_OCR", "resultCode: " + resultCode + " On Request: " + requestCode);

        if (resultCode != RESULT_OK) {
            Util.showmsg(this, "Peringatan : ", "Gagal Mengambil Foto");
        } else {
            switch (requestCode) {
                case SCANNER_OCR:

                    outputFileUri = output;
                    onPhotoTaken1();
                    viewFoto();

                    break;

                default:
                    break;
            }
        }
    }

    public void viewFoto() {

        imageFotoKTP.setImageURI(outputFileUri);

    }

    protected void onPhotoTaken1() {
        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        options.inJustDecodeBounds = true;

        Bitmap bitmap = ShrinkBitmap(_path, 1280, 720);
        imageFotoKTP.setImageBitmap(bitmap);

    }

    Bitmap ShrinkBitmap(String file, int width, int height) {

        Bitmap scaledBitmap = null;

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int actualHeight = bmpFactoryOptions.outHeight;
        int actualWidth = bmpFactoryOptions.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        bmpFactoryOptions.inSampleSize = calculateInSampleSize(bmpFactoryOptions, actualWidth, actualHeight);
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmpFactoryOptions.inDither = false;
        bmpFactoryOptions.inPurgeable = true;
        bmpFactoryOptions.inInputShareable = true;
        bmpFactoryOptions.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) bmpFactoryOptions.outWidth;
        float ratioY = actualHeight / (float) bmpFactoryOptions.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateTime = sdf.format(Calendar.getInstance().getTime());
        String nikKoor = editNIK.getText().toString();
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);

        Canvas canvas = new Canvas(scaledBitmap);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setAntiAlias(true);
        tPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setTypeface(typeface);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        canvas.drawText(dateTime + " " + nikKoor, (middleX - bmp.getWidth() / 2) + 15, canvas.getHeight() - 10, tPaint);

        ExifInterface exif;
        try {
            exif = new ExifInterface(file);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    public int calculateInSampleSize(BitmapFactory.Options bmpFactoryOptions, int reqWidth, int reqHeight) {
        final int height = bmpFactoryOptions.outHeight;
        final int width = bmpFactoryOptions.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(DaftarKoordinatorActivity.PHOTO_TAKEN, _taken);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Log.i(TAG, "onRestoreInstanceState()");
        if (savedInstanceState.getBoolean(DaftarKoordinatorActivity.PHOTO_TAKEN)) {
            onPhotoTaken1();
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void cekRelawan() {

        String url = "http://202.152.27.8:49183/rgp-0/ws/getdatarelawanbyid";
        String nik = editNIK.getText().toString();

        final JSONObject response = ConnectivityWS.checkDataRelawanByNIK(url, nik);
        System.out.println(response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                JSONArray jsonArray = response.getJSONArray("DATA");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    String idrelawan = jonObj.getString("id_relawan");
                    String nama = jonObj.getString("nama");
                    String alamat = jonObj.getString("alamat");
                    String kdpos = jonObj.getString("kd_pos");
                    String kdprop = jonObj.getString("kd_prop");
                    String kdkota = jonObj.getString("kd_kotakab");
                    String kdkec = jonObj.getString("kd_kec");
                    String kdkel = jonObj.getString("kd_kel");
                    String stsRelawanelawan = jonObj.getString("sts_relawan");
                    String kdAreaTugas = jonObj.getString("kd_area_tugas");
                    String kdTps = jonObj.getString("kd_tps");
                    String noHp = jonObj.getString("no_hp");
                    String email = jonObj.getString("email");
                    String nmReff = jonObj.getString("nm_reff");
                    String password = jonObj.getString("password");
                    String catatan = jonObj.getString("catatan");
                    String nik2 = jonObj.getString("nik");
                    String imgKtp = jonObj.getString("img_ktp");
                    String idRelawanUplineEdit = jonObj.getString("id_relawan_upline");
                    String dateCreate = jonObj.getString("date_create");
                    String useridCreate = jonObj.getString("userid_create");
                    String rt = jonObj.getString("rt");
                    String rw = jonObj.getString("rw");

                    editKodePos.setText(kdpos);
                    editNama.setText(nama);
                    editAlamat.setText(alamat);
                    editNoHp.setText(noHp);
                    editEmail.setText(email);
                    editNamaReferensi.setText(nmReff);
                    editPasswordAwal.setText(password);
                    idRelawanUpline2 = idRelawanUplineEdit;
                    System.out.println("ide relawan upline cek relawan = " + idRelawanUplineEdit);
                    editRT.setText(rt);
                    editRW.setText(rw);
                    editKeterangan.setText(catatan);
                    editTPS.setText(kdTps);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("PilpresApp", 0);
                    SharedPreferences.Editor q = pref.edit();
                    q.putString("idrelawan", idrelawan);

                    checkKodePos();

                }
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Relawan Sudah Terdaftar");
            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Relawan Belum Terdaftar, Silahkan isi data!");
                clearText();
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }


    }

    @SuppressLint("ApplySharedPref")
    private void checkKodePos() {
        String url = "http://202.152.27.8:49183/rgp-0/ws/getLokasibykdpos";
        String kdPos = editKodePos.getText().toString();

        final JSONObject response = ConnectivityWS.checkDataByKodePos(url, kdPos);
        System.out.println(response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                JSONArray jsonArray = response.getJSONArray("DATA");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    String kdKordArea = jonObj.getString("kd_kord_area");
                    String kdLokasi = jonObj.getString("kd_lokasi");
                    String kdPulau = jonObj.getString("kd_pulau");
                    String kd_prop = jonObj.getString("kd_prop");
                    String kdKotakab = jonObj.getString("kd_kotakab");
                    String kdKec = jonObj.getString("kd_kec");
                    String kdKel = jonObj.getString("kd_kel");
                    String noPicprop = jonObj.getString("no_picprop");
                    String noPickotakab = jonObj.getString("no_pickotakab");
                    String noPickec = jonObj.getString("no_pickec");
                    String noPickel = jonObj.getString("no_pickel");
                    String nmPulau = jonObj.getString("nm_pulau");
                    String nmProp = jonObj.getString("nm_prop");
                    String nmKotakab = jonObj.getString("nm_kotakab");
                    String nmKec = jonObj.getString("nm_kec");


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("PilpresApp", 0);
                    SharedPreferences.Editor j = pref.edit();
                    j.putString("kd_prop", kd_prop);
                    j.putString("kdKotakab", kdKotakab);
                    j.putString("kdKec", kdKec);
                    j.putString("kdKel", kdKel);
                    j.putString("noPicprop", noPicprop);
                    j.putString("noPickotakab", noPickotakab);
                    j.putString("noPickec", noPickec);
                    j.putString("noPickel", noPickel);
                    j.putString("nmPulau", nmPulau);
                    j.putString("noPicprop", noPicprop);
                    j.putString("kdKordAreaLokasi", kdKordArea);
                    j.putString("kdLokasi", kdLokasi);
                    j.putString("kdPulau", kdPulau);
                    j.commit();


                }
                loadSpinnerPenugasan();
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Silahkan Lanjutkan Pengisian Data");

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Pos Tidak Valid");
                disableEditText();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void disableEditText() {
        editNama.setEnabled(false);
        editAlamat.setEnabled(false);
        spinKelurahan.setEnabled(false);
        spinPenugasan.setEnabled(false);
        editTPS.setEnabled(false);
        editNoHp.setEnabled(false);
        editEmail.setEnabled(false);
        editNamaReferensi.setEnabled(false);
        editPasswordAwal.setEnabled(false);
        editKonfirmPasswordAwal.setEnabled(false);
        editKeterangan.setEnabled(false);
    }

    @SuppressLint({"ApplySharedPref", "ShowToast"})
    private void loadSpinnerPenugasan() {


        System.out.println("kd koordinator area = " + kdKordArea);

        if (kdKordArea.isEmpty()) {
            penugasanPulau();
            Toast.makeText(DaftarKoordinatorActivity.this, "Penugasan Pulau", Toast.LENGTH_LONG);
        } else if (kdKordArea.length() == 3) {
            penugasanPropinsi();
            Toast.makeText(DaftarKoordinatorActivity.this, "Penugasan Propinsi", Toast.LENGTH_LONG);
        } else if (kdKordArea.length() == 5) {
            penugasanKota();
            Toast.makeText(DaftarKoordinatorActivity.this, "Penugasan Kota", Toast.LENGTH_LONG);
        } else if (kdKordArea.length() == 9) {
            penugasanKecamatan();
            Toast.makeText(DaftarKoordinatorActivity.this, "Penugasan Kecamatan", Toast.LENGTH_LONG);
        } else if (kdKordArea.length() == 13) {
            penugasanKelurahan();
            Toast.makeText(DaftarKoordinatorActivity.this, "Penugasan Kelurahan", Toast.LENGTH_LONG);
        }


    }

    private void penugasanPropinsi() {

        String url1 = "http://202.152.27.8:49183/rgp-0/ws/getPenugasan";
        final JSONObject response = ConnectivityWS.getPenugasan(url1, kdKordArea);
        System.out.println("mam nub = " + response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbPenugasanPulau(getApplicationContext()).delete();
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> penugasan = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TbPenugasanPulau mpulau = new TbPenugasanPulau(getApplicationContext());
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("KD_KORD_AREA")) {
                                mpulau.setKD_PULAU(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("NM_PROP")) {
                                mpulau.setNM_PULAU(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mpulau.save();

                    KD_PENUGASAN = jonObj.getString("KD_KORD_AREA");
                    NM_PENUGASAN = jonObj.getString("NM_PROP");

                    penugasan.add(NM_PENUGASAN);

                    System.out.println("DATA SPINNER " + penugasan.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, penugasan);
                    spinPenugasan.setAdapter(dataAdapter);

                    String pulau = spinPenugasan.getSelectedItem().toString();
                    System.out.println("string penugasan = " + pulau);
                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Koordinator Area Tidak Valid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    private void penugasanKelurahan() {
        String url1 = "http://202.152.27.8:49183/rgp-0/ws/getPenugasan";
        final JSONObject response = ConnectivityWS.getPenugasan(url1, kdKordArea);
        System.out.println("mam nub = " + response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbPenugasanPulau(getApplicationContext()).delete();
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> penugasan = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TbPenugasanPulau mpulau = new TbPenugasanPulau(getApplicationContext());
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("KD_KORD_AREA")) {
                                mpulau.setKD_PULAU(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("NM_KEL")) {
                                mpulau.setNM_PULAU(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mpulau.save();

                    KD_PENUGASAN = jonObj.getString("KD_KORD_AREA");
                    NM_PENUGASAN = jonObj.getString("NM_KEL");

                    penugasan.add(NM_PENUGASAN);

                    System.out.println("DATA SPINNER " + penugasan.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, penugasan);
                    spinPenugasan.setAdapter(dataAdapter);

                    String pulau = spinPenugasan.getSelectedItem().toString();
                    System.out.println("string penugasan = " + pulau);
                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Koordinator Area Tidak Valid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    private void penugasanKecamatan() {

        String url1 = "http://202.152.27.8:49183/rgp-0/ws/getPenugasan";
        final JSONObject response = ConnectivityWS.getPenugasan(url1, kdKordArea);
        System.out.println("mam nub = " + response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbPenugasanPulau(getApplicationContext()).delete();
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> penugasan = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TbPenugasanPulau mpulau = new TbPenugasanPulau(getApplicationContext());
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("KD_KORD_AREA")) {
                                mpulau.setKD_PULAU(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("NM_KEC")) {
                                mpulau.setNM_PULAU(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mpulau.save();

                    KD_PENUGASAN = jonObj.getString("KD_KORD_AREA");
                    NM_PENUGASAN = jonObj.getString("NM_KEC");

                    penugasan.add(NM_PENUGASAN);

                    System.out.println("DATA SPINNER " + penugasan.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, penugasan);
                    spinPenugasan.setAdapter(dataAdapter);

                    String pulau = spinPenugasan.getSelectedItem().toString();
                    System.out.println("string penugasan = " + pulau);


                }


            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Koordinator Area Tidak Valid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    private void penugasanKota() {

        String url1 = "http://202.152.27.8:49183/rgp-0/ws/getPenugasan";
        final JSONObject response = ConnectivityWS.getPenugasan(url1, kdKordArea);
        System.out.println("mam nub = " + response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbPenugasanPulau(getApplicationContext()).delete();
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> penugasan = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TbPenugasanPulau mpulau = new TbPenugasanPulau(getApplicationContext());
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("KD_KORD_AREA")) {
                                mpulau.setKD_PULAU(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("NM_KOTAKAB")) {
                                mpulau.setNM_PULAU(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mpulau.save();

                    KD_PENUGASAN = jonObj.getString("KD_KORD_AREA");
                    NM_PENUGASAN = jonObj.getString("NM_KOTAKAB");

                    penugasan.add(NM_PENUGASAN);

                    System.out.println("DATA SPINNER " + penugasan.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, penugasan);
                    spinPenugasan.setAdapter(dataAdapter);

                    String pulau = spinPenugasan.getSelectedItem().toString();
                    System.out.println("string penugasan = " + pulau);
                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Koordinator Area Tidak Valid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    private void penugasanPulau() {

        String url1 = "http://202.152.27.8:49183/rgp-0/ws/getPenugasan";
        final JSONObject response = ConnectivityWS.getPenugasan(url1, kdKordArea);
        System.out.println("mam nub = " + response);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbPenugasanPulau(getApplicationContext()).delete();
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> penugasan = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TbPenugasanPulau mpulau = new TbPenugasanPulau(getApplicationContext());
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("KD_PULAU")) {
                                mpulau.setKD_PULAU(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("NM_PULAU")) {
                                mpulau.setNM_PULAU(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mpulau.save();

                    KD_PENUGASAN = jonObj.getString("KD_PULAU");
                    NM_PENUGASAN = jonObj.getString("NM_PULAU");

                    penugasan.add(NM_PENUGASAN);

                    System.out.println("DATA SPINNER " + penugasan.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, penugasan);
                    spinPenugasan.setAdapter(dataAdapter);

                    String pulau = spinPenugasan.getSelectedItem().toString();
                    System.out.println("string penugasan = " + pulau);

                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Koordinator Area Tidak Valid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    private void loadSpinnerProvinsi() {

        String url = "http://202.152.27.8:49183/rgp-0/ws/getprop";
        final JSONObject response = ConnectivityWS.getProvinsi(url);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbProvinsi(getApplicationContext()).delete();
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> provinsi = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TbProvinsi getProp = new TbProvinsi(getApplicationContext());
                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("KD_PROP")) {
                                getProp.setKD_PROP(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("NM_PROP")) {
                                getProp.setNM_PROP(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getProp.save();

                    KD_PROP = jonObj.getString("KD_PROP");
                    NM_PROP = jonObj.getString("NM_PROP");

                    provinsi.add(NM_PROP);

                    System.out.println("DATA SPINNER " + provinsi.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinsi);
                    spinProvinsi.setAdapter(dataAdapter);

                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Gagal mengambil data");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

    }

    private class SpinProvinsiClick implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            provinsi = spinProvinsi.getSelectedItem().toString();
            System.out.println("string provinsi = " + provinsi);
            loadSpinnerKabupaten();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void loadSpinnerKabupaten() {

        String url = "http://202.152.27.8:49183/rgp-0/ws/getkotakab";
        TbProvinsi getProp = new TbProvinsi(getApplicationContext());
        getProp = getProp.retrieveKodeProvinsi(provinsi);
        kdProp = String.valueOf(getProp.getKD_PROP());
        System.out.println("papang = " + kdProp);
        final JSONObject response = ConnectivityWS.getKabupaten(url, kdProp);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbKabupaten(getApplicationContext()).delete();
                TbKabupaten getKab = new TbKabupaten(getApplicationContext());
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> kab = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("kd_kotakab")) {
                                getKab.setKd_kotakab(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("nm_kotakab")) {
                                getKab.setNm_kotakab(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getKab.save();

                    KD_KOTAKAB = jonObj.getString("kd_kotakab");
                    NM_KOTAKAB = jonObj.getString("nm_kotakab");

                    kab.add(NM_KOTAKAB);

                    System.out.println("DATA SPINNER " + kab.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kab);
                    spinKabupaten.setAdapter(dataAdapter);

                    kabupaten = spinKabupaten.getSelectedItem().toString();
                    System.out.println("string spinKabupaten = " + kabupaten);

                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Provisi Kosong");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

    }

    private class SpinKabupatenClick implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            kabupaten = spinKabupaten.getSelectedItem().toString();
            System.out.println("string kabupaten = " + kabupaten);
            loadSpinnerKecamatan();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void loadSpinnerKecamatan() {

        String url = "http://202.152.27.8:49183/rgp-0/ws/getkec";
        TbKabupaten getKab = new TbKabupaten(getApplicationContext());
        getKab = getKab.retrieveKodeKabupaten(kabupaten);
        String kdKab = String.valueOf(getKab.getKd_kotakab());
        kabkode = "";

        if (kdKab.length() == 1) {
            kabkode = "0" + kdKab;
            /*kdKab.replaceAll(kdKab,"0"+kdKab);*/
        } else {
            kabkode = kdKab;
        }

        System.out.println("papang = " + kdProp);
        System.out.println("papang2 = " + kabkode);
        final JSONObject response = ConnectivityWS.getkecamatan(url, kdProp, kabkode);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbKecamatan(getApplicationContext()).delete();
                TbKecamatan getKec = new TbKecamatan(getApplicationContext());
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> penugasan = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("kd_kec")) {
                                getKec.setKd_kec(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("nm_kec")) {
                                getKec.setNm_kec(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getKec.save();

                    KD_KEC = jonObj.getString("kd_kec");
                    NM_KEC = jonObj.getString("nm_kec");

                    penugasan.add(NM_KEC);

                    System.out.println("DATA SPINNER " + penugasan.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, penugasan);
                    spinKecamatan.setAdapter(dataAdapter);

                    kecamatan = spinKecamatan.getSelectedItem().toString();
                    System.out.println("string spinKecamatan = " + kecamatan);

                }

            } else {
                Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Kecamatan Kosong");
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

    }

    private class SpinKecamatanClick implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            kecamatan = spinKecamatan.getSelectedItem().toString();
            System.out.println("string kabupaten = " + kabupaten);
            loadSpinnerKelurahan();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void loadSpinnerKelurahan() {

        String url = "http://202.152.27.8:49183/rgp-0/ws/getkel";
        TbKecamatan getKec = new TbKecamatan(getApplicationContext());
        getKec = getKec.retrieveKodeKecamatan(kecamatan);
        String kdKec = String.valueOf(getKec.getKd_kec());
        kecKode = "";
        if (kdKec.length() == 1) {
            kecKode = "0" + kdKec;
        } else {
            kecKode = kdKec;
        }

        System.out.println("papang = " + kdProp);
        System.out.println("papang2 = " + kabkode);
        System.out.println("papang3 = " + kecKode);

        final JSONObject response = ConnectivityWS.getkelurahan(url, kdProp, kabkode, kecKode);
        try {
            if (response != null && response.has("CODE") && response.get("CODE").equals("00")) {
                new TbKelurahan(getApplicationContext()).delete();
                TbKelurahan getKel = new TbKelurahan(getApplicationContext());
                final JSONArray jsonArray = response.getJSONArray("DATA");
                ArrayList<String> kel = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jonObj = (JSONObject) jsonArray.get(i);
                    System.out.print("tes array kelurahan= " + jonObj);
                    Iterator keys = jonObj.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = ((String) keys.next()).trim();
                            if (key.equals("kd_kel")) {
                                getKel.setKd_kel(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("nm_kel")) {
                                getKel.setNm_kel(String.valueOf(jonObj.get(key)));
                            }
                            if (key.equals("kd_pos")) {
                                getKel.setKd_pos(String.valueOf(jonObj.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getKel.save();

                    KD_KEL = jonObj.getString("kd_kel");
                    NM_KEL = jonObj.getString("nm_kel");
                    KODEPOS = jonObj.getString("kd_pos");

                    kel.add(NM_KEL);

                    System.out.println("DATA SPINNER " + kel.toString());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kel);
                    spinKelurahan.setAdapter(dataAdapter);

                    kelurahan = spinKelurahan.getSelectedItem().toString();
                    System.out.println("string spinKelurahan = " + kelurahan);

                }

            } else {
                /*Util.showmsg(DaftarKoordinatorActivity.this, null, "Kode Kelurahan Kosong");*/
            }

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

    }

    private class SpinKelurahanClick implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            kelurahan = spinKelurahan.getSelectedItem().toString();
            System.out.println("string kelurahan = " + kabupaten);


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void uploadData() {

        UploadData uploadData = new UploadData();
        uploadData.execute();

    }


    @SuppressLint("StaticFieldLeak")
    public class UploadData extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = new ProgressDialog(DaftarKoordinatorActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Loading...");
                    dialog.show();

                }
            });

        }

        @SuppressLint("SimpleDateFormat")
        @Override
        protected String doInBackground(String... arg0) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("PilpresApp", 0); // 0 - for private mode

            String namaKel = spinKelurahan.getSelectedItem().toString();
            TbKelurahan db = new TbKelurahan(DaftarKoordinatorActivity.this);
            db = db.retrieveKodeKelurahan(namaKel);
            kdKel = String.valueOf(db.getKd_kel());

            System.out.println("propinsi = " + kdProp);
            System.out.println("kabupaten = " + kabkode);
            System.out.println("kecamatan = " + kecKode);
            System.out.println("kelurahan = " + kdKel);

            String idrel = pref.getString("idrelawan", "");
            String namaPenugasan = spinPenugasan.getSelectedItem().toString();
            System.out.println("kode pulau save = " + namaPenugasan);

            TbPenugasanPulau tp = new TbPenugasanPulau(DaftarKoordinatorActivity.this);
            tp = tp.retrieveKodePenugasan(namaPenugasan);
            String konot = String.valueOf(tp.getKD_PULAU());
            System.out.println("kode tugas = " + konot);

            image = fileName;
            idRelawanupline = idrel;
            userID = "36";
            idRelawan = konot;
            System.out.print("kanciang = " + idRelawan);

            String pathName = DaftarKoordinatorActivity.PHOTO_PATH + fileName;

            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            String encodedString = Base64.encodeToString(byte_arr, Base64.DEFAULT);

            /*nameValuePairs.add(new BasicNameValuePair("PHOTO_FILE", encodedString));*/
            System.out.print("culo = " + encodedString);

            String pass = editPasswordAwal.getText().toString();
            String cpass = editKonfirmPasswordAwal.getText().toString();
            if (!pass.equals(cpass)) {
                Toast.makeText(DaftarKoordinatorActivity.this, "Password Not matching", Toast.LENGTH_SHORT).show();
            }

            try {
                // here is your URL path, change the IP number to
                // point to your own server
                URL url = new URL("http://202.152.27.8:49183/rgp-0/ws/relawan/edit");

                JSONObject postDataParams = new JSONObject();
                //add name pair values to the connection
                postDataParams.put("ID_RELAWAN", idRelawan);
                postDataParams.put("NIK", editNIK.getText().toString());
                postDataParams.put("NAMA", editNama.getText().toString());
                postDataParams.put("ALAMAT", editAlamat.getText().toString());
                postDataParams.put("RT", editRT.getText().toString());
                postDataParams.put("RW", editRW.getText().toString());
                postDataParams.put("KD_POS", editKodePos.getText().toString());
                postDataParams.put("KD_PROP", kdProp);
                postDataParams.put("KD_KOTAKAB", kabkode);
                postDataParams.put("KD_KEL", kdKel);
                postDataParams.put("KD_KEC", kecKode);
                postDataParams.put("STS_RELAWAN", "1");
                postDataParams.put("NO_HP", editNoHp.getText().toString());
                postDataParams.put("KD_TPS", editTPS.getText().toString());
                postDataParams.put("EMAIL", editEmail.getText().toString());
                postDataParams.put("NM_REFF", editNamaReferensi.getText().toString());
                postDataParams.put("PASSWORD", editPasswordAwal.getText().toString());
                postDataParams.put("CATATAN", editKeterangan.getText().toString());
                postDataParams.put("DATE_CREATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                postDataParams.put("IMG_KTP", encodedString);
                postDataParams.put("ID_RELAWAN_UPLINE", idRelawanupline);
                postDataParams.put("USERID_CREATE", userID);
                postDataParams.put("KD_AREA_TUGAS", konot);


                Log.e("params", postDataParams.toString());
                Log.e("URL", url.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");//method=post
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();//execute the connection

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.e("responseCode", "responseCode " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    //code 200 connection OK
                    //code 500 server not responding
                    //code 404 file not found
                    //this part is to capture the server response
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    //Log.e("response",conn.getInputStream().toString());

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    do {
                        sb.append(line);
                        Log.e("MSG sb", sb.toString());
                    } while ((line = in.readLine()) != null);

                    in.close();
                    Log.e("response", conn.getInputStream().toString());
                    Log.e("textmessage", sb.toString());

                    setKudo(sb.toString());
                    return sb.toString();//server response message


                } else {
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    Util.showmsg(DaftarKoordinatorActivity.this, "Error", "Time out!");
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                //error on connection
                return new String("Exception: " + e.getMessage());
            }
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jon = new JSONObject(getKudo());

                        if (jon.getString("CODE").equals("00")) {
                            dialog.dismiss();
                            clearText();
                            snackbar = Snackbar.make(coordinatorLayout, "Pendaftaran Berhasil", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            snackbar = Snackbar.make(coordinatorLayout, "Pendaftaran Gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            dialog.dismiss();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        snackbar = Snackbar.make(coordinatorLayout, "Pendaftaran Gagal!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });

        }
    }

    private void editRelawan() {

        EditRelawan editRelawan = new EditRelawan();
        editRelawan.execute();

    }


    @SuppressLint("StaticFieldLeak")
    public class EditRelawan extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = new ProgressDialog(DaftarKoordinatorActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Loading...");
                    dialog.show();

                }
            });

        }

        @SuppressLint("SimpleDateFormat")
        @Override
        protected String doInBackground(String... arg0) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("PilpresApp", 0); // 0 - for private mode

            String namaKel = spinKelurahan.getSelectedItem().toString();
            TbKelurahan db = new TbKelurahan(DaftarKoordinatorActivity.this);
            db = db.retrieveKodeKelurahan(namaKel);
            kdKel = String.valueOf(db.getKd_kel());

            System.out.println("propinsi = " + kdProp);
            System.out.println("kabupaten = " + kabkode);
            System.out.println("kecamatan = " + kecKode);
            System.out.println("kelurahan = " + kdKel);

            String idrel = pref.getString("idrelawan", "");
            String namaPenugasan = spinPenugasan.getSelectedItem().toString();
            System.out.println("kode pulau save = " + namaPenugasan);

            TbPenugasanPulau tp = new TbPenugasanPulau(DaftarKoordinatorActivity.this);
            tp = tp.retrieveKodePenugasan(namaPenugasan);
            String konot = String.valueOf(tp.getKD_PULAU());
            System.out.println("kode tugas = " + konot);

            image = fileName;
            idRelawanupline = idrel;
            userID = "36";
            idRelawan = konot;
            System.out.print("kanciang = " + idRelawan);

            System.out.print("idRelawanupline login = " + idRelawanupline);
            System.out.print("idRelawanupline edit = " + idRelawanUpline2);

            String pathName = DaftarKoordinatorActivity.PHOTO_PATH + fileName;

            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            String encodedString = Base64.encodeToString(byte_arr, Base64.DEFAULT);

            /*nameValuePairs.add(new BasicNameValuePair("PHOTO_FILE", encodedString));*/
            System.out.print("culo = " + encodedString);

            String pass = editPasswordAwal.getText().toString();
            String cpass = editKonfirmPasswordAwal.getText().toString();
            if (!pass.equals(cpass)) {
                Toast.makeText(DaftarKoordinatorActivity.this, "Password Not matching", Toast.LENGTH_SHORT).show();
            }

            if (!idRelawanupline.equals(idRelawanUpline2)) {
                Util.showmsg(DaftarKoordinatorActivity.this, "Peringatan", "Anda tidak berhak merubah data ini");
            } else {
                try {
                    // here is your URL path, change the IP number to
                    // point to your own server
                    URL url = new URL("http://202.152.27.8:49183/rgp-0/ws/relawan/post");

                    JSONObject postDataParams = new JSONObject();
                    //add name pair values to the connection
                    postDataParams.put("ID_RELAWAN", idRelawan);
                    postDataParams.put("NIK", editNIK.getText().toString());
                    postDataParams.put("NAMA", editNama.getText().toString());
                    postDataParams.put("ALAMAT", editAlamat.getText().toString());
                    postDataParams.put("RT", editRT.getText().toString());
                    postDataParams.put("RW", editRW.getText().toString());
                    postDataParams.put("KD_POS", editKodePos.getText().toString());
                    postDataParams.put("KD_PROP", kdProp);
                    postDataParams.put("KD_KOTAKAB", kabkode);
                    postDataParams.put("KD_KEL", kdKel);
                    postDataParams.put("KD_KEC", kecKode);
                    postDataParams.put("STS_RELAWAN", "1");
                    postDataParams.put("NO_HP", editNoHp.getText().toString());
                    postDataParams.put("KD_TPS", editTPS.getText().toString());
                    postDataParams.put("EMAIL", editEmail.getText().toString());
                    postDataParams.put("NM_REFF", editNamaReferensi.getText().toString());
                    postDataParams.put("PASSWORD", editPasswordAwal.getText().toString());
                    postDataParams.put("CATATAN", editKeterangan.getText().toString());
                    postDataParams.put("DATE_CREATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    postDataParams.put("IMG_KTP", encodedString);
                    postDataParams.put("ID_RELAWAN_UPLINE", idRelawanupline);
                    postDataParams.put("USERID_CREATE", userID);
                    postDataParams.put("KD_AREA_TUGAS", konot);


                    Log.e("params", postDataParams.toString());
                    Log.e("URL", url.toString());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");//method=post
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.connect();//execute the connection

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    Log.e("responseCode", "responseCode " + responseCode);
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        //code 200 connection OK
                        //code 500 server not responding
                        //code 404 file not found
                        //this part is to capture the server response
                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(
                                conn.getInputStream()));
                        //Log.e("response",conn.getInputStream().toString());

                        StringBuffer sb = new StringBuffer("");
                        String line = "";


                        do {
                            sb.append(line);
                            Log.e("MSG sb", sb.toString());
                        } while ((line = in.readLine()) != null);

                        in.close();
                        Log.e("response", conn.getInputStream().toString());
                        Log.e("textmessage", sb.toString());

                        setKudo(sb.toString());
                        return sb.toString();//server response message


                    } else {
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        Util.showmsg(DaftarKoordinatorActivity.this, "Error", "Time out!");
                        return new String("false : " + responseCode);
                    }
                } catch (Exception e) {
                    //error on connection
                    return new String("Exception: " + e.getMessage());
                }
            }
            return null;

        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jon = new JSONObject(getKudo());

                        if (jon.getString("CODE").equals("00")) {
                            dialog.dismiss();
                            clearText();
                            snackbar = Snackbar.make(coordinatorLayout, "Pendaftaran Berhasil", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            snackbar = Snackbar.make(coordinatorLayout, "Pendaftaran Gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            dialog.dismiss();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        snackbar = Snackbar.make(coordinatorLayout, "Pendaftaran Gagal!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });

        }
    }

    private void clearText() {

        editNama.setText("");
        editAlamat.setText("");
        editKodePos.setText("");
        spinPenugasan.setSelection(1);
        editNoHp.setText("");
        editTPS.setText("");
        editEmail.setText("");
        editNamaReferensi.setText("");
        editPasswordAwal.setText("");
        editKonfirmPasswordAwal.setText("");
        editKeterangan.setText("");
        imageFotoKTP.setImageResource(R.drawable.icon_foto_ktp);
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        //Log.i("result",result.toString());
        return result.toString();
    }//end sendPostData


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_global, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
