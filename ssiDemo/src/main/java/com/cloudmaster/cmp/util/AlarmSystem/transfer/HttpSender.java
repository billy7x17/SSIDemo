/*******************************************************************************
 * @(#)HttpSender.java Dec 20, 2012
 *
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.cloudmaster.cmp.util.AlarmSystem.transfer;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.catalina.util.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author <a href="mailto:gong.x@neusoft.com">gong.x </a>
 * @version $Revision 1.1 $ Dec 20, 2012 1:47:39 PM
 */
public class HttpSender {

    public static String ENCODING = "UTF-8";

    public static final int DEFAULT_TIMEOUT_CONN = 1000 * 60;

    public static final int DEFAULT_TIMEOUT_DATA = 1000 * 60;

    public static final String SERVER_URL = "serverUrl";

    public static final String HDR_ERROR = "X-error-response";

    private int timeout;

    private int datatimeout;

    public HttpSender() {
        this.timeout = DEFAULT_TIMEOUT_CONN;

        this.datatimeout = DEFAULT_TIMEOUT_DATA;
    }

    public HttpSender(int timeout, int datatimeout) {
        this.timeout = timeout;

        this.datatimeout = datatimeout;
    }

    public ResponseObject send(TransportObject object) throws Exception {
        ResponseObject rs = new ResponseObject();
        ByteArrayOutputStream bOs = null;
        DataOutputStream dOs = null;
        DataInputStream dIs = null;
        HttpClient client;
        PostMethod meth = null;
        byte[] rawData;
        try {
            bOs = new ByteArrayOutputStream();
            dOs = new DataOutputStream(bOs);
            object.toStream(dOs);
            bOs.flush();
            rawData = bOs.toByteArray();

            client = new HttpClient();
            client.setConnectionTimeout(this.timeout);
            client.setTimeout(this.datatimeout);
            client.setHttpConnectionFactoryTimeout(this.timeout);

            meth = new PostMethod(object.getValue(SERVER_URL));
            // meth = new UTF8PostMethod(url);
            meth.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODING);
            // meth.addParameter(SERVER_ARGS, new String(rawData,"UTF-8"));
            
            // meth.setRequestBody(new String(rawData));
            // meth.setRequestBody(new String(rawData,"UTF-8"));
            
            byte[] base64Array = Base64.encode(rawData).getBytes();
            meth.setRequestBody(new String(base64Array));
            
            // System.out.println(new String(rawData));

            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(1, false));
            client.executeMethod(meth);

            dIs = new DataInputStream(meth.getResponseBodyAsStream());

            if (meth.getStatusCode() == HttpStatus.SC_OK) {

                Header errHeader = meth.getResponseHeader(HDR_ERROR);

                if (errHeader != null) {
                    rs.setError(meth.getResponseBodyAsString());
                    return rs;
                }

                rs = ResponseObject.fromStream(dIs);

                return rs;
            } else {
                meth.releaseConnection();
                throw new IOException("Connection failure: " + meth.getStatusLine().toString());
            }
        } finally {
            if (meth != null) {
                meth.releaseConnection();
            }
            if (bOs != null) {
                bOs.close();
            }
            if (dOs != null) {
                dOs.close();
            }
            if (dIs != null) {
                dIs.close();
            }
        }
    }

    public ResponseObject send(Object object, Map<String, String> paramMap) throws Exception {
        ResponseObject rs = new ResponseObject();
        ByteArrayOutputStream bOs = null;
        DataOutputStream dOs = null;
        DataInputStream dIs = null;
        HttpClient client;
        PostMethod meth = null;
        byte[] rawData;
        try {
            client = new HttpClient();
            client.setConnectionTimeout(this.timeout);
            client.setTimeout(this.datatimeout);
            client.setHttpConnectionFactoryTimeout(this.timeout);

            meth = new PostMethod(paramMap.get("SERVER_URL"));
            // meth = new UTF8PostMethod(url);
            meth.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODING);
            // meth.addParameter(SERVER_ARGS, new String(rawData,"UTF-8"));
            meth.setRequestBody(object.toString());
            System.out.println(object.toString());

            /**
             * 请求头增加"type"="ruleSync",表示是规则XML同步； 请求头增加"syncType"="***"
             * ，“1”表示新增规则、“2”表示修改规则、“3”表示删除规则 请求头增加"ruleName"="***"
             * ，表示规则XML同步的XML文件名；请求内容是同步的XML文件内容
             */
            meth.addRequestHeader("type", paramMap.get("type"));
            meth.addRequestHeader("syncType", paramMap.get("syncType"));
            meth.addRequestHeader("ruleName", URLEncoder.encode(paramMap.get("ruleName"), "UTF-8"));
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(1, false));

            client.executeMethod(meth);

            dIs = new DataInputStream(meth.getResponseBodyAsStream());

            if (meth.getStatusCode() == HttpStatus.SC_OK) {

                Header errHeader = meth.getResponseHeader(HDR_ERROR);

                if (errHeader != null) {
                    rs.setError(meth.getResponseBodyAsString());
                    return rs;
                }

                rs = ResponseObject.fromStream(dIs);

                return rs;
            } else {
                meth.releaseConnection();
                throw new IOException("Connection failure: " + meth.getStatusLine().toString());
            }
        } finally {
            if (meth != null) {
                meth.releaseConnection();
            }
            if (bOs != null) {
                bOs.close();
            }
            if (dOs != null) {
                dOs.close();
            }
            if (dIs != null) {
                dIs.close();
            }
        }
    }
}
