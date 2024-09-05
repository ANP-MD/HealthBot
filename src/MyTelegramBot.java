import org.json.JSONArray;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MyTelegramBot extends TelegramLongPollingBot {

    private static final String TOKEN = "";
    private static final String USERNAME = "";
    private static final String JSON_FILE_PATH = "users.json";
    private Set<Long> users = new HashSet<>();
    
    public MyTelegramBot() {
        loadUsersFromJson();
        startPeriodicMessaging();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                User user = message.getFrom();
                if (text.equals("/subscribe")) {
                    addUser(user.getId());
                    sendMessage(user.getId(), "You have been added to the list.");
                }
                else if (text.equals("/check")) {
                    addUser(user.getId());
                    sendMessage(user.getId(), "Checking...");
                    startMessaging();
                }
            }
        }
    }

    public void sendMessage(Long who, String what){
    	   SendMessage sm = SendMessage.builder()
    	                    .chatId(who.toString()) //Who are we sending a message to
    	                    .text(what).build();    //Message content
    	   try {
    	        execute(sm);                        //Actually sending the message
    	   } catch (TelegramApiException e) {
    	        throw new RuntimeException(e);      //Any error will be printed here
    	   }
    	}

    private void addUser(Long userId) {
        users.add(userId);
        saveUsersToJson();
    }

    private void loadUsersFromJson() {
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                JSONArray jsonArray = new JSONArray(json.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    users.add(jsonArray.getLong(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUsersToJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            JSONArray jsonArray = new JSONArray(users);
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startPeriodicMessaging() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	
            	//

            	//
                for (Long userId : users) {
                	Connection connection = null;
                    Statement statement = null;
                    
                    try {
                        // Load Oracle JDBC driver

                        Class.forName("oracle.jdbc.driver.OracleDriver");

                        // Connect to the database
                        connection = DriverManager.getConnection("URL", "USER", "PASS");

                        // Create a statement
                        statement = connection.createStatement();
                        connection.close();
                        // Execute a query
                        }
                      
                        catch(Exception e) {
                            sendMessage(userId, "RPRAC is down.");
                        }
                    
                    String websiteUrl = "URL"; // Replace with your URL
                    boolean isUp = isWebsiteUp(websiteUrl);
                    
                    if (!isUp) {
                        sendMessage(userId, "");
                    } 
                    
                    //check psiholog
                    if(checkTcpPing("IP", PORT, "DB")) {
                    }else {
                        sendMessage(userId, "");
                    }
                    
                    //check prisonphone
                    if(checkTcpPing("IP", PORT, "DB")) {
                    }else {
                        sendMessage(userId, "");
                    }
                    
                }
            }
        }, 0, 120000); // 120 seconds interval
    }
    
    private static boolean isWebsiteUp(String websiteUrl) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(websiteUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout in milliseconds
            connection.setReadTimeout(5000); // Timeout in milliseconds
            
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400); // HTTP status codes 2xx and 3xx indicate success
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    private void startMessaging() {

                for (Long userId : users) {
                	Connection connection = null;
                    Statement statement = null;
                    
                    try {
                        // Load Oracle JDBC driver

                        Class.forName("oracle.jdbc.driver.OracleDriver");

                        // Connect to the database
                        connection = DriverManager.getConnection("URL", "USER", "PASS");

                        // Create a statement
                        statement = connection.createStatement();
                        sendMessage(userId, "");
                        connection.close();

                        // Execute a query
                        }
                      
                        catch(Exception e) {
                            sendMessage(userId, "");
                        }
                    
                    //check ecancelaria
                    String websiteUrl = "URL"; // Replace with your URL
                    boolean isUp = isWebsiteUp(websiteUrl);
                    
                    if (isUp) {
                        sendMessage(userId, "");
                    } else {
                        sendMessage(userId, "");
                    }
                    //check psiholog
                    if(checkTcpPing("IP", PORT, "DB")) {
                        sendMessage(userId, "");
                    }else {
                        sendMessage(userId, "");
                    }
                    
                    //check prisonphone
                    if(checkTcpPing("IP", PORT, "DB")) {
                        sendMessage(userId, "");
                    }else {
                        sendMessage(userId, "");
                    }
                    
                }
            }
    
    private static boolean checkTcpPing(String host, int port, String dbName) {
        try (Socket socket = new Socket(host, port)) {
return true;} 
        catch (IOException e) {
return false;}
    }
    
            
    

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
