package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.PizzaSize;
import com.android.cs414groupnewandroid.objects.Register;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jim on 11/22/2015.
 */
public class GetSizesController implements Runnable {
    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    Context context;
    Register model;

    public GetSizesController(Context context, Register model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
        Looper.prepare();
        String url = "http://10.0.2.2:7777/menu/sizes/";
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        // make GET request to the given URL
        HttpResponse httpResponse;
        try {
            httpResponse = httpclient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                try {
                    InputStream in = entity.getContent();
                    result = convertToString(in);
                    ArrayList<PizzaSize> top = parseSizes(result);
                    model.getCatalog().setSizes(top);
                } catch (Exception e){
                    Log.e("ERROR_ON_CREATION", ".." , e);
                }
            }
        } catch (ClientProtocolException e) {
            Log.e("GetToppingsController", e.getStackTrace().toString());
        } catch (IOException e) {
            Log.e("GetToppingsController", e.getStackTrace().toString());
        }
        OrderFragment.syncHandler.sendEmptyMessage(2);
    }

    private ArrayList<PizzaSize> parseSizes(String result) {
        Scanner sc = new Scanner(result);
        String temp;
        ArrayList<PizzaSize> list = new ArrayList<PizzaSize>();
        while (sc.hasNextLine()) {
            temp = sc.nextLine();
            Log.e("STRING:!@#!@#:", temp);
            if (temp.contains("itemID")) {
                double id = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                String shorty = temp.replaceAll("<.*?>", "").replaceAll("&quot;", "''");
                temp = sc.nextLine().trim();
                String full = temp.replaceAll("<.*?>", "").replaceAll("&quot;", "''");
                PizzaSize size = new PizzaSize(shorty, full, id);
                list.add(size);
            }
        }
        return list;
    }


    private String convertToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buff = new StringBuffer();
        String line = null;
        try {
            line = reader.readLine();
            while (line != null)
            {
                buff.append(line + "\n");
                line = reader.readLine();
            }
            is.close();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "!@#!@#!@#", Toast.LENGTH_LONG).show();
        }
        return buff.toString();
    }
}