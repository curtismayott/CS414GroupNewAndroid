package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.OrderItem;
import com.android.cs414groupnewandroid.objects.PIZZA_STATUS;
import com.android.cs414groupnewandroid.objects.PizzaSize;
import com.android.cs414groupnewandroid.objects.Register;
import com.android.cs414groupnewandroid.objects.SideItem;
import com.android.cs414groupnewandroid.objects.Special;
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
public class GetSpecialsController implements Runnable {
    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    Context context;
    Register model;

    public GetSpecialsController(Context context, Register model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
        Looper.prepare();
        String url = "http://10.0.2.2:7777/menu/specials/";
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
                    ArrayList<Special> s = parseSpecials(result);
                    model.getCatalog().setSpecials(s);
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

    private ArrayList<Special> parseSpecials(String result) {
        Scanner sc = new Scanner(result);
        String temp;
        ArrayList<Special> list = new ArrayList<Special>();
        while (sc.hasNextLine()) {
            temp = sc.nextLine().trim();
            if (temp.contains("specialid")) {
                temp = temp.replaceAll("<.*?>", "");
                Special spec;
                int sid = Integer.parseInt(temp);
                temp = sc.nextLine().trim();
                String type = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                String name = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                sc.nextLine();
                double price = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                int orderID = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                String status = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                String orderIid = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                String itemID = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                String name2 = temp.replaceAll("<.*?>", "");
                temp = sc.nextLine().trim();
                double price2 = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                sc.nextLine();
                int numToppings = Integer.parseInt(temp.replaceAll("<.*?>", ""));
                temp = sc.nextLine().trim();
                double discountedPrice = Double.parseDouble(temp.replaceAll("<.*?>", ""));
                spec = new Special();
                spec.setItemType(type);
                spec.setSpecialID(sid);
                spec.setName(name);
                spec.setDiscountedPrice(discountedPrice);
                if (type.equals("Side")) {
                    OrderItem oi = new OrderItem(price, Integer.parseInt(orderIid));
                    spec.setSideItem((SideItem)oi);
                    oi.setOrderID(orderID);
                    oi.setStatus(PIZZA_STATUS.NEW);
                    oi.setItemID(Integer.parseInt(itemID));
                    ((SideItem) oi).setName(name);
                    oi.setPrice(price2);
                }
                else if (type.equals("Pizza")) {
                    PizzaSize size = new PizzaSize(status, Integer.parseInt(itemID));
                    size.setFullName(itemID);
                    spec.setSize(size);
                    spec.setSpecialID(orderID);
                    size.setPrice(Double.parseDouble(itemID));
                    spec.setNumToppings(Integer.parseInt(name));
                }
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