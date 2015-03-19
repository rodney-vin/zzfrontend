package com.zz.backend.configuration.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public class SendCloudMailSenderWebApiImpl implements SendCloudMailSender {
	private Log log = LogFactory.getLog(SendCloudMailSenderWebApiImpl.class);
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		String[] tos = simpleMessage.getTo();
		if(tos == null || tos.length == 0){
			log.error("Mail does not contain To");
		}else{
			String to = tos[0];
			try {
				send(to,simpleMessage.getSubject(),simpleMessage.getText());
			} catch (Exception e) {
				log.error("send mail failed", e);
			}
		}
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		for(SimpleMailMessage msg:simpleMessages){
			send(msg);
		}
	}
	
	@Autowired
	private MailProperties properties;
	
	private void send(String to,String subject,String content) throws ClientProtocolException, IOException {
		String url = properties.getUrl();
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpost = new HttpPost(url);

		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("api_user", properties.getApiuser())); // 使用api_user和api_key进行验证
		nvps.add(new BasicNameValuePair("api_key", properties.getApikey()));
		nvps.add(new BasicNameValuePair("from", properties.getFrom()));
		nvps.add(new BasicNameValuePair("to", "j2ee@qq.com"));
		nvps.add(new BasicNameValuePair("subject", "来自SendCloud的第一封邮件！"));
		nvps.add(new BasicNameValuePair("html",	"你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！"));

		httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		// 请求
		HttpResponse response = httpclient.execute(httpost);
		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			// 读取xml文档
			String result = EntityUtils.toString(response.getEntity());
			log.debug(result);
		} else {
			log.error("mail api return as OK, but contains error");
		}
	}
}
