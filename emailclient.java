package com.example.webserviceclient;

import mypackage1.EmailServiceImplService;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class emailclient extends JFrame {
    private JTextArea textArea1;
    private JTextArea textArea2;
    private static String _payload="";
    private static String _url="";
    private EmailServiceImplService emailServiceImpl;
    private emailclient()
    {
        emailServiceImpl=new EmailServiceImplService();
        this.setLayout(null);
        this.setTitle("my email");
        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        JButton button1=new JButton();
        JButton button2=new JButton();
        textArea1=new JTextArea();
        textArea2=new JTextArea();
        button1.setText("verify");
        button2.setText("send");
        button1.setBounds(280,20,68,50);
        button2.setBounds(280,130,68,50);
        JScrollPane js1=new JScrollPane(textArea1);
        JScrollPane js2=new JScrollPane(textArea2);
        js1.setBounds(40,20,200,50);
        js2.setBounds(40,80,200,150);
        textArea1.setLineWrap(true);
        textArea2.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea2.setWrapStyleWord(true);
        js1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        js2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(button1);
        this.add(button2);
        this.add(js1);
        this.add(js2);
        this.setVisible(true);
        button1.addActionListener(e -> {
        _url=textArea1.getText();
        String result = emailServiceImpl.getEmailServiceImplPort().validateEmailAddress(_url);
        System.out.println(result);
        });
        button2.addActionListener(e -> {
        _url=textArea1.getText();
        _payload=textArea2.getText();

        String url[]=_url.split(",");
        List<String> list = Arrays.asList(url);
        String result = emailServiceImpl.getEmailServiceImplPort().sendEmailBatch(list,_payload);
        System.out.println(result);
        });
    }
    public static void main(String[] args) {
        emailclient email=new emailclient();
    }
}
