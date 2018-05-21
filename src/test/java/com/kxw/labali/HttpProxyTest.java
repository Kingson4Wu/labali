package com.kxw.labali;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

/**
 * Created by kingsonwu on 18/5/14.
 */
public class HttpProxyTest {

    @Test
    public void httpProxy() throws IOException {

        CloseableHttpClient httpService = HttpClients.custom().build();

        //访问目标地址
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        //请求返回
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpService.execute(httpGet);

            try {
                int statusCode = httpResp.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    System.out.println("成功");
                }
            } catch (Exception e) {

            } finally {
                httpResp.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jdkConnection() {
        //HttpClientConnection


    }

    /**
     * https://www.cnblogs.com/littleatp/p/4729781.html
     * 方式一：Java支持以System.setProperty的方式设置http代理及端
     * 方式二：使用Proxy对象，在建立连接时注入到URLConnection
     *
     * @throws Exception
     */
    @Test
    public void testHttpURLConnection() throws Exception {

        URL localURL = new URL("http://www.baidu.com");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

        //httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        //httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }
        System.out.println(resultBuffer.toString());
    }

}
