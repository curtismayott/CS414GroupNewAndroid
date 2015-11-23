package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.Register;
import com.thoughtworks.xstream.XStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by Jim on 11/20/2015.
 */
public class PushOrderController implements Runnable {
    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    Context context;
    Register model;
    Order order;

    public PushOrderController(Context context, Register model, Order order) {
        this.context = context;
        this.model = model;
        this.order = order;
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

	@Override
	public void run() {
		String url = "http://10.0.2.2:7777/order/in/?";
		HttpClient httpclient = new DefaultHttpClient();
		XStream x = new XStream();
		x.setClassLoader(Order.class.getClassLoader());
		String response =  x.toXML(new OrderHolder(order));
		response = URLEncoder.encode(response);

		HttpPost post = new HttpPost(url += response);
		//send response with code 200 (A-OK)
		HttpGet get = new HttpGet(url);
		HttpResponse httpResponse;
		String result = "";
		try {
			httpResponse = httpclient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();
				result = convertToString(in);
				System.out.println(result);
			}
		} catch (Exception e) {
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
			result = "SendOrder error";
		}
		OrderFragment.syncHandler.sendEmptyMessage(4);
	}
}
