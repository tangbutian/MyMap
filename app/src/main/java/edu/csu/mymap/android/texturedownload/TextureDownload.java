package edu.csu.mymap.android.texturedownload;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import edu.csu.mymap.android.android.config.LoggerConfig;

public class TextureDownload {
	private static final String TAG = "TextureDownload";

	public static byte[] httpClientDownLoad(String imgURL, int level, int row,
			int coloum) throws HttpException {
		// 处理url字符串
		imgURL = imgURL.replace("^l", Integer.toString(level));
		imgURL = imgURL.replace("^r", Integer.toString(row));
		imgURL = imgURL.replace("^c", Integer.toString(coloum));
		if (LoggerConfig.ON) {
			Log.v(TAG, "HttpUrl is: " + imgURL);
		}
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient();
		// 创建GET方法的实例
		HttpGet getMethod = new HttpGet(imgURL);
		try {
			// 执行getMethod
			HttpResponse response = httpClient.execute(getMethod);
			// 获取http状态码
			int statusCode = response.getStatusLine().getStatusCode();
			// http请求错误
			if (LoggerConfig.ON)
				Log.v(TAG, "statusCode of httpResponse: \n" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.err
						.println("Method failed: " + response.getStatusLine());
			}
			// 读取内容

			byte[] responseBody = IOUtils.toByteArray(response.getEntity()
					.getContent());
			if (LoggerConfig.ON) {
				Log.v(TAG, "response getEntity getContent: \n" + responseBody);
			}
			System.out.println(responseBody.length);
			if(LoggerConfig.ON)
				Log.v(TAG, "The Content length of img: " + responseBody.length);
			return responseBody;
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			// getMethod.;
			getMethod.abort();
		}
		return null;
	}
}
