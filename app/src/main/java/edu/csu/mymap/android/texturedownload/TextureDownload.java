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
		// ����url�ַ���
		imgURL = imgURL.replace("^l", Integer.toString(level));
		imgURL = imgURL.replace("^r", Integer.toString(row));
		imgURL = imgURL.replace("^c", Integer.toString(coloum));
		if (LoggerConfig.ON) {
			Log.v(TAG, "HttpUrl is: " + imgURL);
		}
		// ����HttpClient��ʵ��
		HttpClient httpClient = new DefaultHttpClient();
		// ����GET������ʵ��
		HttpGet getMethod = new HttpGet(imgURL);
		try {
			// ִ��getMethod
			HttpResponse response = httpClient.execute(getMethod);
			// ��ȡhttp״̬��
			int statusCode = response.getStatusLine().getStatusCode();
			// http�������
			if (LoggerConfig.ON)
				Log.v(TAG, "statusCode of httpResponse: \n" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.err
						.println("Method failed: " + response.getStatusLine());
			}
			// ��ȡ����

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
			// ���������쳣
			e.printStackTrace();
		} finally {
			// �ͷ�����
			// getMethod.;
			getMethod.abort();
		}
		return null;
	}
}
