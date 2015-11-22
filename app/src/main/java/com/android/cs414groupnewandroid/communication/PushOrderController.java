package com.android.cs414groupnewandroid.communication;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.cs414groupnewandroid.controllers.OrderEditListener;
import com.android.cs414groupnewandroid.objects.Order;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jim on 11/20/2015.
 */
public class PushOrderController extends AsyncTask implements HttpHandler {
    OrderEditListener view;
    Order order;

    public PushOrderController(OrderEditListener orderEditListener, Order order) {
        view = orderEditListener;
        this.order = order;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String url = "http://10.0.2.2:7777/order/";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse httpResponse;
        String result = "";
        try {
            httpResponse = httpclient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                result = convertToString(in);
                Toast.makeText(view.context, result, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(view.context, e.toString(), Toast.LENGTH_LONG).show();
            result = "SendOrder error";
        }
        return result;
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
            Toast.makeText(view.context, "!@#!@#!@#", Toast.LENGTH_LONG).show();
        }
        return buff.toString();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
