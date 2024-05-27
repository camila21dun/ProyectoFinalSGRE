package co.edu.uniquindio.sgre.viewController;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;


import java.io.IOException;

public class EmailService {
/*
    private static final String API_KEY = System.getenv("SENDGRID_API_KEY");

    public static void enviarCorreo(String destinatario, String asunto, String contenido) {
        Email from = new Email("camila21dun@gmail.com");
        Email to = new Email(destinatario);
        Content content = new Content("text/plain", contenido);
        Mail mail = new Mail(from, asunto, to, content);

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error sending email: " + ex.getMessage());
        }
    }

 */
}