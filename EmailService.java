package com.example.email;

import javax.jws.WebService;

@WebService
public interface EmailService {
    public String validateEmailAddress(String _url);
    public String sendEmail(String _url,String _payload);
    public String sendEmailBatch(String[] _url,String _payload);
}
