package com.fortuna.rgpmobile.main;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RPGValidator {

    public String onGetLapor() {

        int connectiontimeout = 15000;
        int sockettimeout = 15000;
        String sURL;
        String status = "GetBroadcast";
        String msg = null;

        sURL = "http://31.220.58.1:8080/rgb/ws/lapor/get";
        System.out.println("Broadcast URL New :" + sURL);

        try {

            HttpParams httpparameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpparameters, connectiontimeout);
            HttpConnectionParams.setSoTimeout(httpparameters, sockettimeout);
            HttpClient httpClient = new DefaultHttpClient(httpparameters);
            HttpGet getRequest = new HttpGet(sURL);
            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            String rsp, output = "";

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            while ((rsp = br.readLine()) != null) {
                output = rsp;
            }

            httpClient.getConnectionManager().shutdown();

            msg = output;
            System.out.println(output
                    + "\n" + "Message : " + msg);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return msg;

    }


    public String[] showData(String status, String resp) {

        String[] data = resp.split(Pattern.quote("|"));
        return data;

    }


    public String[] showDataNew(String resp) {

        return new String[]{resp};

    }


    public JSONObject cekInboxLaporan(String url, String ID_RELAWAN) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("ID_RELAWAN", ID_RELAWAN));

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


    public JSONObject cekInboxBroadcast(String url) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

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


    public JSONObject postLaporan(String url, String ID_RELAWAN, String TGL_KEJADIAN, String STS_LAPORAN,
                                  String JUDUL, String DESKRIPSI, String ID_RELAWAN_TO,
                                  String STS_REPLY, String DATE_CREATE, String USERID_CREATE) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("ID_RELAWAN", ID_RELAWAN));
        nameValuePairs.add(new BasicNameValuePair("TGL_KEJADIAN", TGL_KEJADIAN));
        nameValuePairs.add(new BasicNameValuePair("STS_LAPORAN", STS_LAPORAN));
        nameValuePairs.add(new BasicNameValuePair("JUDUL", JUDUL));
        nameValuePairs.add(new BasicNameValuePair("DESKRIPSI", DESKRIPSI));
        nameValuePairs.add(new BasicNameValuePair("ID_RELAWAN_TO", ID_RELAWAN_TO));
        nameValuePairs.add(new BasicNameValuePair("STS_REPLY", STS_REPLY));
        nameValuePairs.add(new BasicNameValuePair("DATE_CREATE", DATE_CREATE));
        nameValuePairs.add(new BasicNameValuePair("USERID_CREATE", USERID_CREATE));

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


    public JSONObject postBroadcast(String url, String ID_RELAWAN, String TGL_BROADCAST, String ID_BROADCAST,
                                  String JNS_BROADCAST, String DESKRIPSI, String TGL_EXPIRED,
                                  String DATE_CREATE, String USERID_CREATE) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("ID_RELAWAN", ID_RELAWAN));
        nameValuePairs.add(new BasicNameValuePair("TGL_BROADCAST", TGL_BROADCAST));
        nameValuePairs.add(new BasicNameValuePair("ID_BROADCAST", ID_BROADCAST));
        nameValuePairs.add(new BasicNameValuePair("JNS_BROADCAST", JNS_BROADCAST));
        nameValuePairs.add(new BasicNameValuePair("DESKRIPSI", DESKRIPSI));
        nameValuePairs.add(new BasicNameValuePair("TGL_EXPIRED", TGL_EXPIRED));
        nameValuePairs.add(new BasicNameValuePair("DATE_CREATE", DATE_CREATE));
        nameValuePairs.add(new BasicNameValuePair("USERID_CREATE", USERID_CREATE));

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


    public JSONObject checkLogin(String url, String no_hp, String pass) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("NO_HP", no_hp));
        nameValuePairs.add(new BasicNameValuePair("PASSWORD", pass));

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
}
