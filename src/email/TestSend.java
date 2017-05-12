package email;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;


import util.ZipUtil;

public class TestSend
{

    public static void main(String[] args) throws MessagingException, IOException
    {

        Map<String,String> map= new HashMap<String,String>();
        //user pwd
        SendMail mail = new SendMail("1015401377@qq.com","llh1084878971,,.");
        map.put("mail.smtp.host", "smtp.qq.com");

        //暂时未成功，需要调试
        /*SendMail mail = new SendMail("14789****@sina.cn","***miya");
        map.put("mail.smtp.host", "smtp.sina.com");*/
        map.put("mail.smtp.auth", "true");
        map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        map.put("mail.smtp.port", "465");//465 为企业邮箱
        map.put("mail.smtp.socketFactory.port", "465");
        mail.setPros(map);
        mail.initMessage();
        String filePath="D:"+File.separator+"TestFile";
        File dir=new File(filePath);
        File [] files=dir.listFiles();
        File zipFile=new File("D:\\TestFile.zip");
        ZipUtil.zipFile(files, zipFile);
        mail.setMultipart(zipFile.getAbsolutePath());
        /*
         * 添加收件人有三种方法：
         * 1,单人添加(单人发送)调用setRecipient(str);发送String类型
         * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
         * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
         */
        List<String> list = new ArrayList<String>();
     //   list.add("1015401377@qq.com");
        //list.add("***92@sina.cn");
//        list.add("1013564041@qq.com");
        ResourceBundle rs=ResourceBundle.getBundle("email");//src/email
        String toEmail=rs.getString("toEmail");
        list.add(toEmail);
        mail.setRecipients(list);
        /*String defaultStr = "283942930@qq.com,429353942@qq.com,2355663819@qq.com,381766286@qq.com;
        StringBuffer sb = new StringBuffer();
        sb.append(defaultStr);
        sb.append(",316121113@qq.com");
        mail.setRecipients(sb);*/
        mail.setSubject("超神");
        //mail.setText("谢谢合作");
        mail.setDate(new Date());
        mail.setFrom("you");
//      mail.setMultipart("D:你你你.txt");
        mail.setContent("小超子", "text/html; charset=UTF-8");
        /*List<String> fileList = new ArrayList<String>();
        fileList.add("D:1.jpg");
        fileList.add("D:activation.zip");
        fileList.add("D:dstz.sql");
        fileList.add("D:软件配置要求.doc");
        mail.setMultiparts(fileList);*/
        System.out.println(mail.sendMessage());
    }

}