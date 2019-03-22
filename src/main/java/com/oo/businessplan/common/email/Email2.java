package com.oo.businessplan.common.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Email2 {
	
	/**
     * 发送邮件
     * @param user 发件人邮箱
     * @param password 授权码（注意不是邮箱登录密码）
     * @param host 
     * @param from 发件人
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return success 发送成功 failure 发送失败
     * @throws Exception
     */
    public String sendMail(String user, String password, String host,
            String from, String to, String subject, String content)
            throws Exception {
        if (to != null){
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            MailAuthenticator auth = new MailAuthenticator();
            auth.USERNAME = user;
            auth.PASSWORD = password;
            Session session = Session.getInstance(props, auth);

            session.setDebug(true);
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                
                if (!to.trim().equals(""))
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(to.trim()));
                message.setSubject(subject);
                MimeBodyPart mbp1 = new MimeBodyPart(); // 正文
                mbp1.setContent(content, "text/html;charset=utf-8");
                Multipart mp = new MimeMultipart(); // 整个邮件：正文+附件
                mp.addBodyPart(mbp1);
                
                //添加附件
                File file = new File("E:"+File.separator+"素材"+File.separator+"bird1.png");
                System.out.println("=============================");
                System.out.println(file.exists());
                if (file.exists()) {
					BodyPart bp = new MimeBodyPart();
					DataSource dataSource = new FileDataSource(file);
					bp.setDataHandler(new DataHandler(dataSource));
					bp.setFileName(MimeUtility.encodeWord(file.getName()));
					mp.addBodyPart(bp);
				}
                                         
                // mp.addBodyPart(mbp2);
                message.setContent(mp);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport trans = session.getTransport("smtp");
                trans.send(message);
                System.out.println(message.toString());
            } catch (Exception e){
                e.printStackTrace();
                return "failure";
            }
            return "success";
        }else{            
            return "failure";
        }
    }
    
    public static void main(String[] args) {

        Email2 email2 = new Email2();
        String user = "1514202395@qq.com";
        String password = "eejyplcqdanfihfa";
        String host = "smtp.qq.com";
        String from = "1514202395@qq.com";
        String to = "1514202395@qq.com";// 收件人
        String subject = "第二次java测试";
        //邮箱内容
        StringBuffer sb = new StringBuffer();
        String yzm ="asdfdasf";
        sb.append("<!DOCTYPE>"+"<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                          + "<div style='width:950px;font-family:arial;'>欢迎使用NET微活动，您的注册码为：<br/><h2 style='color:green'>"+yzm+"</h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>杭州恩意替电子商务有限公司</div>"
                         +"</div>");
        try {
            String res = email2.sendMail(user, password, host, from, to,
                    subject, sb.toString());
            System.out.println(res);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
 

}
