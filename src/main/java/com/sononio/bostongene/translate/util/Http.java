package com.sononio.bostongene.translate.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Util for creating http requests easier.
 */
public class Http {
    /**
     * Creates GET request
     * @param url
     * @param headers headers of request
     * @param params params of request
     * @return server message
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static String get(String url,
                             Map<String, String> headers,
                             Map<String, String> params) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HttpClient httpClient = createClient();
        String urlWithParams = url + createQueryString(params);

        HttpGet request = new HttpGet(urlWithParams);
        headers.forEach(request::setHeader);
        HttpResponse response = httpClient.execute(request);
        String result = resultString(response);
        return result;
    }

    /**
     * Creates POST request
     * @param url
     * @param body body of request
     * @param headers headers of request
     * @param params params of request
     * @return server message
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static String post(String url,
                              String body,
                              Map<String, String> headers,
                              Map<String, String> params) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HttpClient httpClient = createClient();
        String urlWithParams = url + createQueryString(params);
        HttpPost request = new HttpPost(urlWithParams);
        headers.forEach(request::setHeader);
        request.setEntity(new StringEntity(body));

        HttpResponse response = httpClient.execute(request);
        String result = resultString(response);
        return result;
    }

    /**
     * Creates new default HttpClient
     * @return new HttpClient
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static HttpClient createClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build());
        HttpClientBuilder clientBuilder = HttpClients.custom().setSSLSocketFactory(
                sslsf);
        CloseableHttpClient client;
        client = clientBuilder.build();
        return client;
    }

    /**
     * Creates request query string from map.
     * @param params params to convert to query string
     * @return query string
     * @throws UnsupportedEncodingException
     */
    private static String createQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder urlWithParamsBuilder = new StringBuilder("");
        if (params.size() > 0) {
            urlWithParamsBuilder.append('?');
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlWithParamsBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                urlWithParamsBuilder.append("=");
                urlWithParamsBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                urlWithParamsBuilder.append("&");
            }
            String urlWithParams = urlWithParamsBuilder.toString();
            urlWithParams = urlWithParams.substring(0, urlWithParams.length() - 1);
            return urlWithParams;
        }
        return "";
    }

    /**
     * Reads result message from server response
     * @param response
     * @return
     * @throws IOException
     */
    private static String resultString(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }
}
