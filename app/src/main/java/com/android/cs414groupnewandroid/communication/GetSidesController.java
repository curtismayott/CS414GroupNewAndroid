package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.OrderItem;
import com.android.cs414groupnewandroid.objects.PIZZA_STATUS;
import com.android.cs414groupnewandroid.objects.Register;
import com.android.cs414groupnewandroid.objects.Side;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;

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
public class GetSidesController implements Runnable {
    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    Context context;
    Register model;

    public GetSidesController(Context context, Register model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
        Looper.prepare();
        String url = "http://10.0.2.2:7777/menu/sides/";
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
                    //Log.e("GetSaucesController", result);

                    ArrayList<Side> top = parseSides(result);
                    model.getCatalog().setSides(top);
                } catch (CannotResolveClassException e){
                    Log.e("ERROR_ON_CREATION", e.getLocalizedMessage());
                }
            }
        } catch (ClientProtocolException e) {
            Log.e("GetToppingsController", e.getStackTrace().toString());
        } catch (IOException e) {
            Log.e("GetToppingsController", e.getStackTrace().toString());
        }
        OrderFragment.syncHandler.sendEmptyMessage(2);
    }

    private ArrayList<Side> parseSides(String result) {
        Scanner sc = new Scanner(result);
        String temp;
        ArrayList<Side> list = new ArrayList<Side>();
        while (sc.hasNextLine()) {
            temp = sc.nextLine();
            if (temp.contains("price defined-in")) {
                double Oprice = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                int orderId = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                temp = sc.nextLine().trim();
                int oId = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                int iId = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                String name = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                double price = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                Side s = new Side(name, price);
                s.setOrderID(orderId);
                OrderItem oi = (OrderItem) s;
                oi.setOrderID(iId);
                oi.setItemID(oId);
                oi.setPrice(Oprice);
                oi.setStatus(PIZZA_STATUS.NEW);
                list.add(s);
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