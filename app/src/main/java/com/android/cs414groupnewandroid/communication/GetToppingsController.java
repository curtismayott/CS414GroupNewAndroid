package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.controllers.OrderEditListener;
import com.android.cs414groupnewandroid.fragments.OrderFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


/**
 * Created by darkbobo on 11/15/15.
 */
public class GetToppingsController implements Runnable {

    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
	Context context;

    public GetToppingsController(Context context) {
		this.context = context;
    }

    /*
    public ArrayList<Topping> parseToppings(String res) {
        ArrayList<Topping> temp = new ArrayList<Topping>();
        if (res != null) {
            Toast.makeText(view.context, res, Toast.LENGTH_LONG).show();
            String[] split = res.split("<");
            if (split[0].contains("toppingList>")) {
                System.out.println(split[0] + '\t' + split[1]);
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        split[0] = split[0].replaceAll("toppinglist>", "");
                    }
                    createToppingFromXML(split[i]);
                }
            }
        }
        return temp;
    }

    private Topping createToppingFromXML(String s) {
        String[] temp = s.split("/");
        temp[0] = temp[0].replaceAll("topping><id>", "");
        temp[0] = temp[0].substring(0, temp[0].length() - 1);
        temp[1] = temp[1].replaceAll("id><short>", "");
        temp[1] = temp[1].substring(0, temp[1].length() - 1);
        temp[2] = temp[2].replaceAll("short><full>", "");
        temp[2] = temp[2].substring(0, temp[2].length() - 1);
        Topping t = new Topping(temp[1], temp[2]);
        t.setItemID(Integer.parseInt(temp[0]));
        return t;
    }

    public String GET(String url) {
        Looper.prepare();
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        // make GET request to the given URL
        HttpResponse httpResponse;
        try {
            httpResponse = httpclient.execute(new HttpGet(url));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                result = convertToString(in);
            }
        } catch (Exception e) {
            Toast.makeText(view.context, e.toString(), Toast.LENGTH_LONG).show();
            result = "Toppings server-connect error";
        }
        return "Toppings server-connect error";

    }
    */

    @Override
    public void run() {
		Looper.prepare();
		String url = "http://10.0.2.2:7777/menu/getToppings";
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
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
				Log.e("GetToppingsController", result);
				/*
				TODO
				********************** PARSING HERE ****************************
				*/
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
			Log.e("GetToppingsController", e.toString());
        }
		OrderFragment.syncHandler.sendEmptyMessage(2);
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
