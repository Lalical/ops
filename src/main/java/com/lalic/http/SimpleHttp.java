package com.lalic.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SimpleHttp {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttp.class);

    static {
        trustAllHosts();
    }

    public static Response get(String url, Map<String, String> header) {
        return exec(url, header, null, "GET");
    }

    public static Response post(String url, Map<String, String> header, String body) {
        return exec(url, header, body, "POST");
    }

    public static Response exec(String url, Map<String, String> header, String body, String method) {
        URL urlAddr;
        try {
            urlAddr = new URL(url);
        } catch (Exception e) {
            logger.error("illegal url");
            return null;
        }

        HttpURLConnection connection;
        BufferedReader breader;
        BufferedWriter bwriter;

        try {
            connection = (HttpURLConnection) urlAddr.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            if (isEmpty(method)) {
                method = "GET";
            }
            connection.setRequestMethod(method);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.setConnectTimeout(5000);
        connection.setDoOutput("POST".equalsIgnoreCase(method));
        connection.setDoInput(true);
        if (header != null) {
            for (String key : header.keySet()) {
                connection.addRequestProperty(key, header.get(key));
            }
        }

        if ("POST".equalsIgnoreCase(method) && body != null) {
            try {
                bwriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            try {
                bwriter.write(body);
                bwriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bwriter != null) {
                    try {
                        bwriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            breader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String line;
        StringBuilder mess = new StringBuilder();
        try {
            while ((line = breader.readLine()) != null) {
                mess.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                breader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int code = 0;
        try {
            code = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new Response(code, mess.toString(), connection.getHeaderFields());


    }


    private static void trustAllHosts() {

        TrustManager[] trustallCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustallCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {

        }


    }

    public static class Response {

        private int code;

        private String body;

        private Map<String, List<String>> header;

        public Response(int code, String body, Map<String, List<String>> header) {
            this.code = code;
            this.body = body;
            this.header = header;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Map<String, List<String>> getHeader() {
            return header;
        }

        public void setHeader(Map<String, List<String>> header) {
            this.header = header;
        }

        public Map<String, List<String>> addHeader(String key, String value) {
            List<String> listVal = new ArrayList<>();
            listVal.add(value);
            return addHeader(key, listVal);
        }

        public Map<String, List<String>> addHeader(String key, List<String> value) {
            header.put(key, value);
            return header;
        }

    }

    private static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static void main(String[] args) {
//        Response response = get("https://www.qq.com", null);
//        System.out.println("");
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }


}
