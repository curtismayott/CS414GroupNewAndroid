package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.Drink;
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
public class GetDrinksController  implements Runnable {

    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    Context context;
    Register model;

    public GetDrinksController(Context context, Register model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
        String url = "http://10.0.2.2:7777/menu/drinks/";
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        // make GET request to the given URL
        HttpResponse httpResponse;
        try {
            httpResponse = httpclient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                result = convertToString(in);
                parseDrinks(result);
            }
        } catch (ClientProtocolException e) {
            System.out.println(e.getStackTrace());
            Log.e("GetToppingsController", "ERROR", e);
        } catch (IOException e) {
            Log.e("GetToppingsController", e.getStackTrace().toString());
        }
        OrderFragment.syncHandler.sendEmptyMessage(2);
    }

    private ArrayList<Drink> parseDrinks(String result) {
        Scanner sc = new Scanner(result);
        String temp;
        ArrayList<Drink> drink = new ArrayList<>();
        while (sc.hasNextLine()) {
            temp = sc.nextLine();
            if (temp.contains("price")) {
                temp = temp.replaceAll("<.*?>", "");
                double OrderItemPrice = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                int orderID = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                String status = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                int orderItemId = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                int itemId = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                String name = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine();
                double price = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();

                model.getCatalog().addItem(new Drink(name, price));
            }
        }
        return drink;
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
