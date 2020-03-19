package com.fortuna.rgpmobile.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SuppressWarnings("ALL")
public class Util {

    public static int nShowMsg = 0;

    public static void showmsg(Context context, String sTitle, String sMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(sTitle)
                .setMessage(sMsg)
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        nShowMsg = 10;
                    }
                });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    public static void popup(Context context, String sMsg) {
        Toast.makeText(context, sMsg, Toast.LENGTH_LONG).show();
    }

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean writeFile(String sFileName, String content) {
        try {
            File file = new File(sFileName);
            FileWriter filewriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(filewriter);
            out.append(content);
            out.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String generateNum(int digits) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < digits; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    public static String numberToTerbilang(double number) {
        String bilangan[] = new String[]{"", "satu ", "dua ", "tiga ", "empat ", "lima ", "enam ", "tujuh ",
                "delapan ", "sembilan ", "sepuluh ", "sebelas "};

        StringBuffer sb = new StringBuffer();

        if (number < Double.valueOf(12)) {
            sb.append(bilangan[(int) number]);
        }

        if (number >= 12 && number < 20) {
            sb.append(numberToTerbilang(number - 10));
            sb.append("belas ");
        }

        if (number >= 20 && number < 100) {
            sb.append(numberToTerbilang(number / 10));
            sb.append("puluh ");
            sb.append(numberToTerbilang(number % 10));
        }

        if (number >= 100 && number < 200) {
            sb.append("seratus ");
            sb.append(numberToTerbilang(number % 100));
        }

        if (number >= 200 && number < 1000) {
            sb.append(numberToTerbilang(number / 100));
            sb.append("ratus ");
            sb.append(numberToTerbilang(number % 100));
        }

        if (number >= 1000 && number < 2000) {
            sb.append("seribu ");
            sb.append(numberToTerbilang(number % 1000));
        }

        if (number >= 2000 && number < 1000000) {
            sb.append(numberToTerbilang(number / 1000));
            sb.append("ribu ");
            sb.append(numberToTerbilang(number % 1000));
        }

        if (number >= 1000000 && number < 1000000000) {
            sb.append(numberToTerbilang(number / 1000000));
            sb.append("juta ");
            sb.append(numberToTerbilang(number % 1000000));
        }

        if (number >= 1000000000 && number < 1000000000000L) {
            sb.append(numberToTerbilang(number / 1000000000));
            sb.append("milyar ");
            sb.append(numberToTerbilang(number % 1000000000));
        }
        return sb.toString();
    }


    public static String getJam(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat readFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;

        date = readFormat.parse(dateStr);
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        String jamKlikButton = String.valueOf(hours);

        System.out.println("JAM KLIK BUTTON " + jamKlikButton);

        return jamKlikButton;

    }


    public static BigDecimal fiveHund() {

        BigDecimal five = new BigDecimal(500);

        return five;

    }


    public static void hideKeyboard( Activity activity ) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService( Context.INPUT_METHOD_SERVICE );
        View f = activity.getCurrentFocus();
        if( null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom( f.getClass() ) )
            imm.hideSoftInputFromWindow( f.getWindowToken(), 0 );
        else
            activity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
    }
}