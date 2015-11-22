package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.Register;
import com.android.cs414groupnewandroid.objects.Topping;

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
 * Created by darkbobo on 11/15/15.
 */
public class GetToppingsController implements Runnable {

    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
	Context context;
    Register model;

    public GetToppingsController(Context context, Register model) {
		this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
		String url = "http://10.0.2.2:7777/menu/toppings/";
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
                ArrayList<Topping> top = parseToppings(result);
                model.getCatalog().setToppings(top);
            }
        } catch (ClientProtocolException e) {
            System.out.println(e.getStackTrace());
            Log.e("GetToppingsController", "ERROR", e);
        } catch (IOException e) {
            Log.e("GetToppingsController", e.getStackTrace().toString());
        }
        OrderFragment.syncHandler.sendEmptyMessage(2);
    }

    ArrayList<Topping> parseToppings(String s){
        Scanner sc = new Scanner(s);
        String temp;
        ArrayList<Topping> list = new ArrayList<Topping>();
        while (sc.hasNextLine()) {
            temp = sc.nextLine().trim();
            if (temp.equals("<toppings>")) {
                Topping tempT;
                temp = sc.nextLine().trim();;
                temp = temp.replace("/", "");
                temp = temp.replace("<itemid>", "");
                int id = Integer.parseInt(temp);
                temp = sc.nextLine().trim();;
                temp = temp.replace("/", "");
                temp = temp.replace("<shortname>", "");
                String sh = temp;
                temp = sc.nextLine().trim();;
                temp = temp.replace("/", "");
                temp = temp.replace("<fullname>", "");
                tempT = new Topping(sh, temp);
                tempT.setItemID(id);
                list.add(tempT);
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
