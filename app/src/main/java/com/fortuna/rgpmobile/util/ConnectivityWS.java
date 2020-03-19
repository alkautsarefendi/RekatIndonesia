package com.fortuna.rgpmobile.util;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConnectivityWS {

    public static JSONObject checkDataRelawanByNIK(String url, String nik) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("NIK", nik));

        if (url.contains("?")) {
            String dataParams = url.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject checkDataByKodePos(String url, String kdPos) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("KD_POS", kdPos));

        if (url.contains("?")) {
            String dataParams = url.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject checkKelurahanByKodePos(String url, String kdPos) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("KD_POS", kdPos));

        if (url.contains("?")) {
            String dataParams = url.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject getPenugasan(String url1, String kdKordArea) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("KD_KORD_AREA", kdKordArea));

        if (url1.contains("?")) {
            String dataParams = url1.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url1);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject getProvinsi(String url) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        if (url.contains("?")) {
            String dataParams = url.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject getKabupaten(String url1, String kdProp) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("KD_PROP", kdProp));

        if (url1.contains("?")) {
            String dataParams = url1.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url1);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject getkecamatan(String url1, String kdProp, String kdKab) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("KD_PROP", kdProp));
        nameValuePairs.add(new BasicNameValuePair("KD_KOTAKAB", kdKab));

        if (url1.contains("?")) {
            String dataParams = url1.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url1);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public static JSONObject getkelurahan(String url1, String kdProp, String kdKab, String kdKec) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("KD_PROP", kdProp));
        nameValuePairs.add(new BasicNameValuePair("KD_KOTAKAB", kdKab));
        nameValuePairs.add(new BasicNameValuePair("KD_KEC", kdKec));

        if (url1.contains("?")) {
            String dataParams = url1.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url1);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            System.out.println("Message : " + sb.toString());
            return new JSONObject(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

}
