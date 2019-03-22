package com.oo.businessplan.common.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Component;

import com.oo.businessplan.common.util.XmlUtil;

@Component
public class EmailServer {
	
	private List<EmailConfig> emailConfigs;
	
	private String[] templates;
	
	private static final long MAX_LENGTH=10000L;
	
	private Long maxlength;
	
	public void sendEmail(String target,String subject,File[] files,Map<String,String> params) throws UnsupportedEncodingException{
		  sendEmail(target, subject, files, 0,params,0);
	}
	
	public void sendEmail(String target,String subject,File[] files,int configNum,Map<String,String> params,int template) throws UnsupportedEncodingException{
		
		  if (target==null||target.trim().equals("")) {
			  return;
		  }
		  if (subject==null||subject.trim().equals("")) {
			  return;
		  }
		  if (emailConfigs==null||emailConfigs.size()<=0||configNum<0||configNum>emailConfigs.size()) {
			  return;
		  }
		  
		  Thread sendEmail = new Thread(new SendEmailThread(target, subject, params, files, configNum, template));
		  sendEmail.start();
	}
	
class SendEmailThread implements Runnable{
		
		private File[] files;
		private Map<String,String> params;
		private String target ;
		private String subject;
		private int configNum;
		private int template;
		
		public SendEmailThread(String target,String subject,Map<String,String> params,File[] files,int configNum,int template){
			this.target = target;
			this.subject = subject;
			this.params = params;
			this.files = files;
			this.configNum = configNum;
			this.template = template;
		}
	
		@Override
		public void run() {
			
			EmailConfig config = emailConfigs.get(configNum);
			Properties props = System.getProperties();
			props.put("mail.smtp.host", config.getMail_smtp_host());
	        props.put("mail.smtp.auth",config.getMail_smtp_auth());
			
	        Session session = Session.getInstance(props, config);        
	        session.setDebug(true);
	        	        
			try {
				MimeMessage email = generalEmail(session, config, target, subject);
				Map<String,Object> valMap = new HashMap<>();
		        valMap.put("value", params);
		        valMap.put("template", template);
		        String content = generalContent(valMap);
		        
		        MimeBodyPart bp = new MimeBodyPart();
				bp.setContent(content,  "text/html;charset=utf-8");
				Multipart mp = new MimeMultipart(); // 整个邮件：正文+附件
				mp.addBodyPart(bp);
				 
				addAttachMents(mp, files);
	
				email.setContent(mp);
			    email.setSentDate(new Date());
				email.saveChanges();
				Transport trans = session.getTransport("smtp");
				trans.send(email);
			} catch (AddressException e1) {
				e1.printStackTrace();
			} catch (MessagingException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        
	}
}
	
	 private MimeMessage generalEmail(Session session,EmailConfig config,String target,String subject) throws AddressException, MessagingException{
		 
		 MimeMessage email = new MimeMessage(session);
		 email.setFrom(new InternetAddress(config.getEmailName()));
		 email.addRecipient(Message.RecipientType.TO, new InternetAddress(target));
		 email.setSubject(subject);
		 
		 return email;
	 }
	 
	  private void addAttachMents(Multipart mp,File[] files) throws MessagingException, UnsupportedEncodingException{
		//添加附件
			 if (files!=null&&files.length>0) {
				long maxl = 0;
				for (int i = 0; i < files.length; i++) {
					maxl = maxl+files[i].length();
				}
				maxlength = maxlength==null?MAX_LENGTH:maxlength;//如果没有默认值，则取自带的默认最大长度
				if (maxl>maxlength*1024) {
					return;
				}
				for (int i = 0; i < files.length; i++) {
					BodyPart bpart = new MimeBodyPart();
					DataSource dataSource = new FileDataSource(files[i]);
					bpart.setDataHandler(new DataHandler(dataSource));
					bpart.setFileName(MimeUtility.encodeWord(files[i].getName()));
					mp.addBodyPart(bpart);
				}			
			 }
	  }
	
	  
	  
	
	/**
	 * 根据模板文件生成对应的字符串
	 * 
	 * @param params
	 * @return
	 */
	public String generalContent(Map<String,Object> params){
	
		
		  String template = templates[Integer.parseInt(params.get("template").toString())];

		  String xmlStr = XmlUtil.readXmlAsString(template);
		  
		  @SuppressWarnings("unchecked")
		  Map<String,String> valMap = (Map<String, String>)params.get("value");
		  
		  String content = replare(xmlStr, valMap);
		  
		  return content;
	}
	
	/**
	 * 
	 * 字符替换的原理：
	 * 获取字符串str，转化为字符数组chs，设置录入开关flag，默认为true，
	 * 然后遍历数组chs，
	 * 1.如果当前循环的字符为$并且下一个字符为{则表示遇到了占位符，此时关闭开关，设置为false，
	 * 2.如果开关为false并且当前的字符为}则表示这个字符为占位符的结束符号，重新打开开关，
	 *   然后把缓冲器key的值作为关键字获取params参数中的值，加入到sb中。
	 * 3.如果开关为true，则表示当前字符为正常的内容，可以直接加入到sb中
	 * 4.否则为占位符，则作为关键字字符加入到key中
	 * 注意，上述的顺序不能改变
	 * @param str  需要替换值的字符串
	 * @param params  替换的参数
	 * @return
	 */
	private String  replare(String str,Map<String,String> params){
		
		  String content = null;  
		  
		  StringBuilder sb = new StringBuilder(); 
		  
		  StringBuilder key =new StringBuilder();//存储关键字
		  char[] chs = str.toCharArray();
		  boolean flag = true;
		  for (int i = 0,len = chs.length; i < len; i++) {
			  if (chs[i]=='$'&&chs[i+1]=='{') {
				 flag = false;
				 i++;//跳过{这个无用的字符
			  }
			  else if (!flag&&chs[i]=='}') {
				  flag = true;//重新打开开关
				  String keyStr = key.toString();
				  String val = params.get(keyStr);
				  val = val==null?"":val;
				  sb.append(val);
			  }
			  else if (flag) {
				  sb.append(chs[i]);
			  }
			  else {
				   key.append(chs[i]);
			  }
		  }
		  if (flag) {
			  content = sb.toString();
		  }	  
		  //替换模板字符串中的占位符
		 /* Set<String> keys = params.keySet();
		  for (String key : keys) {
			 sb.append("${").append(key).append("}");
			 String val = params.get(key)==null?null:params.get(key).toString();
           xmlStr = xmlStr.replaceAll(sb.toString(), val);
           sb.delete(0,sb.length());
		  }*/
		    /*b a
		     ...................... */
		  
		  return content;
	}
			
	
	

	public List<EmailConfig> getEmailConfigs() {
		return emailConfigs;
	}

	public void setEmailConfigs(List<EmailConfig> emailConfigs) {
		this.emailConfigs = emailConfigs;
	}

	public EmailConfig getDefaultEmailConfig() throws Exception{
		 if (emailConfigs!=null&&emailConfigs.size()>0) {
			 return emailConfigs.get(0);
		 }
		 throw new Exception();
	}

	public Long getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Long maxlength) {
		this.maxlength = maxlength;
	}

	public String[] getTemplates() {
		return templates;
	}

	public void setTemplates(String[] templates) {
		this.templates = templates;
	}


	 
	 
	 

}
