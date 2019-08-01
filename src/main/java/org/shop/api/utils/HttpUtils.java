package org.shop.api.utils;


import java.io.IOException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * HttpUtils工具类
 *
 * @author
 */
public class HttpUtils {

    /**
     * 请求方式：post
     */
    public static String POST = "post";

    public static Gson gson = new Gson();

    /**
     * 编码格式：utf-8
     */
    private static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 报文头部json
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * 请求超时时间
     */
    private static final int CONNECT_TIMEOUT = 60 * 1000;

    /**
     * 传输超时时间
     */
    private static final int SO_TIMEOUT = 60 * 1000;

    /**
     * 日志
     */
    private static Logger loggger = Logger.getLogger(HttpUtils.class);


    /**
     * @param protocol
     * @param url
     * @param paraMap
     * @return
     * @throws Exception
     */
    public static String doPost(String protocol, String url,
                                Map<String, Object> paraMap, Map<String,String> headers) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse resp = null;
        String rtnValue = null;
        try {
            if (protocol.equals("http")) {
                httpClient = HttpClients.createDefault();
            } else {
                // 获取https安全客户端
                httpClient = HttpUtils.getHttpsClient();
            }

            HttpPost httpPost = new HttpPost(url);
            if(headers!=null&&headers.size()>0){
                for(Entry<String,String> entry : headers.entrySet()){
                    httpPost.setHeader(entry.getKey(),entry.getValue());
                  /*  httpPost.setHeader("x-csrf-token","1909287225");
                    httpPost.setHeader("x-requested-with","XMLHttpRequest");
                    httpPost.setHeader("cookie"," wanplus_token=05028c2e9c27d6fa9a017f33c5fca7b7;  wanplus_csrf=_csrf_tk_1892510009; ");*/
                }
            }
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (null != paraMap && paraMap.size() > 0) {
                for (Entry<String, Object> entry : paraMap.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue().toString()));
                }
            }

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SO_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).build();// 设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(list, CHARSET_UTF_8));
            resp = httpClient.execute(httpPost);
            rtnValue = EntityUtils.toString(resp.getEntity(), CHARSET_UTF_8);

        } catch (Exception e) {
            loggger.error(e.getMessage());
            throw e;
        } finally {
            if (null != resp) {
                resp.close();
            }
            if (null != httpClient) {
                httpClient.close();
            }
        }

        return rtnValue;
    }

    /**
     * 获取https，单向验证
     *
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient getHttpsClient() throws Exception {
        try {
            TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(
                        X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                public void checkServerTrusted(
                        X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext
                    .getInstance(SSLConnectionSocketFactory.TLS);
            sslContext.init(new KeyManager[0], trustManagers,
                    new SecureRandom());
            SSLContext.setDefault(sslContext);
            sslContext.init(null, trustManagers, null);
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpClientBuilder clientBuilder = HttpClients.custom()
                    .setSSLSocketFactory(connectionSocketFactory);
            clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
            CloseableHttpClient httpClient = clientBuilder.build();
            return httpClient;
        } catch (Exception e) {
            throw new Exception("http client 远程连接失败", e);
        }
    }

    /**
     * post请求
     *
     * @param msgs
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws UnknownHostException
     * @throws IOException
     */
    public static String post(Map<String, Object> msgs, String url)
            throws ClientProtocolException, UnknownHostException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost(url);
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
            if (null != msgs) {
                for (Entry<String, Object> entry : msgs.entrySet()) {
                    if (entry.getValue() != null) {
                        valuePairs.add(new BasicNameValuePair(entry.getKey(),
                                entry.getValue().toString()));
                    }
                }
            }
            request.setEntity(new UrlEncodedFormEntity(valuePairs, CHARSET_UTF_8));
            CloseableHttpResponse resp = httpClient.execute(request);
            return EntityUtils.toString(resp.getEntity(), CHARSET_UTF_8);
        } finally {
            httpClient.close();
        }
    }

    /**
     * get请求
     *
     * @param msgs
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws UnknownHostException
     * @throws IOException
     */
    public static String get(Map<String, Object> msgs, String url)
            throws ClientProtocolException, UnknownHostException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
            if (null != msgs) {
                for (Entry<String, Object> entry : msgs.entrySet()) {
                    if (entry.getValue() != null) {
                        valuePairs.add(new BasicNameValuePair(entry.getKey(),
                                entry.getValue().toString()));
                    }
                }
            }
            // EntityUtils.toString(new UrlEncodedFormEntity(valuePairs),
            // CHARSET);
            url = url + "?" + URLEncodedUtils.format(valuePairs, CHARSET_UTF_8);
            HttpGet request = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SO_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).build();// 设置请求和传输超时时间
            request.setConfig(requestConfig);
            CloseableHttpResponse resp = httpClient.execute(request);
            return EntityUtils.toString(resp.getEntity(), CHARSET_UTF_8);
        } finally {
            httpClient.close();
        }
    }

    public static String get(Map<String, Object> msgs, String url, int timeOut)
            throws ClientProtocolException, UnknownHostException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
            if (null != msgs) {
                for (Entry<String, Object> entry : msgs.entrySet()) {
                    if (entry.getValue() != null) {
                        valuePairs.add(new BasicNameValuePair(entry.getKey(),
                                entry.getValue().toString()));
                    }
                }
            }
            // EntityUtils.toString(new UrlEncodedFormEntity(valuePairs),
            // CHARSET);
            url = url + "?" + URLEncodedUtils.format(valuePairs, CHARSET_UTF_8);
            HttpGet request = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SO_TIMEOUT)
                    .setConnectTimeout(timeOut).build();// 设置请求和传输超时时间
            request.setConfig(requestConfig);
            CloseableHttpResponse resp = httpClient.execute(request);
            return EntityUtils.toString(resp.getEntity(), CHARSET_UTF_8);
        } finally {
            httpClient.close();
        }
    }


    public static String getWithOutParam(String url, int timeout)
            throws ClientProtocolException, UnknownHostException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SO_TIMEOUT)
                    .setConnectTimeout(timeout).build();// 设置请求和传输超时时间
            request.setConfig(requestConfig);
            CloseableHttpResponse resp = httpClient.execute(request);
            return EntityUtils.toString(resp.getEntity(), CHARSET_UTF_8);
        } finally {
            httpClient.close();
        }
    }

    public static <T> T post(Map<String, Object> msgs, String url,
                             Class<T> clazz) throws ClientProtocolException,
            UnknownHostException, IOException {
        String json = HttpUtils.post(msgs, url);
        T t = gson.fromJson(json,clazz);
        return t;
    }

    public static <T> T get(Map<String, Object> msgs, String url, Class<T> clazz)
            throws ClientProtocolException, UnknownHostException, IOException {
        String json = HttpUtils.get(msgs, url);
        T t = gson.fromJson(json,clazz);
        return t;
    }

    public static String postWithJson(Map<String, Object> msgs, String url)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String jsonParam = gson.toJson(msgs);
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", APPLICATION_JSON);
            post.setEntity(new StringEntity(jsonParam, CHARSET_UTF_8));
            CloseableHttpResponse response = httpClient.execute(post);

            return new String(EntityUtils.toString(response.getEntity()).getBytes("iso8859-1"), CHARSET_UTF_8);
        } finally {
            httpClient.close();
        }
    }

    public static <T> T postWithJson(Map<String, Object> msgs, String url,
                                     Class<T> clazz) throws ClientProtocolException,
            UnknownHostException, IOException {
        String json = HttpUtils.postWithJson(msgs, url);
        T t = gson.fromJson(json,clazz);
        return t;
    }

    //获取cookie
    public static Map<String,String> getCookie(Map<String, Object> msgs, String url)
            throws ClientProtocolException, UnknownHostException, IOException {
        Map<String,String> cookieMap = new HashMap<String,String>();
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse resp = httpClient.execute(request);
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                Cookie cc = cookies.get(i);
                cookieMap.put(cc.getName(),cc.getValue());
            }
            return cookieMap;
        } finally {
            httpClient.close();
        }
    }


}
