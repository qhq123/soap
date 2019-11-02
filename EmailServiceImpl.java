package com.example.email;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailServiceImpl implements EmailService{
    private String[] EmailTemplate = {"mailcontent1.html"};
    private String[] EmailTitle = {"this is qhq email1"};
    @Override
    public String validateEmailAddress(String _url) {
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(_url);
        if (m.matches()) return "Y";
        else return "N";
    }

    @Override
    public String sendEmail(String _url, String _payload){
        String accesskeyid="LTAI4Fs69wWhGaM6fUMJhDoM";
        String secret="HTEqOi5275fkYzptogPBdkOBHU8Lo9";
        String sendemailaddress="qhq@service.qinhanqing.xyz";
        String sendername="QHQ";
        String targetaddress="";
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accesskeyid, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        //使用https加密连接
        request.setProtocol(com.aliyuncs.http.ProtocolType.HTTPS);
        //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
        request.setAccountName(sendemailaddress);
        request.setFromAlias(sendername);
        request.setAddressType(1);
        //可以不需要
        //request.setTagName("控制台创建的标签");
        //是否需要回信功能
        request.setReplyToAddress(true);
        targetaddress=_url;
        request.setToAddress(targetaddress);
        //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
        //request.setToAddress("邮箱1,邮箱2");
        int type=0;
        request.setSubject(EmailTitle[type]);
        ClassPathResource mailTemplate = new ClassPathResource(EmailTemplate[type],this.getClass());
        Scanner scanner = null;
        try {
            scanner = new Scanner(mailTemplate.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder htmlBody = new StringBuilder();
        if (scanner != null) {
            while (scanner.hasNextLine()){
                htmlBody.append(scanner.nextLine()).append(System.getProperty("line.separator"));
            }
        }
        htmlBody = new StringBuilder(htmlBody.toString().replace("[_payload]", _payload));
        request.setHtmlBody(htmlBody.toString());
        try {
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            return "N";
        }
        return "Y";
    }

    @Override
    public String sendEmailBatch(String[] _url, String _payload) {
        for (String s : _url) {
            String res=sendEmail(s,_payload);
            if("N".equals(res))
                return "N";
        }
        return "Y";
    }
}
