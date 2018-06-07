package sample;

import java.net.*;
import java.io.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.*;

public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private Socket          socket2 = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    private DataInputStream in2      = null;
    private DataOutputStream out     = null;
    private DataOutputStream out2 = null;
    private String line = "";
    private String lin2 = "";

    public JFrame sFrame = new JFrame("Server");
    public JTextField sDataField = new JTextField(40);
    public JTextArea sMessageArea = new JTextArea(8, 60);



    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            sFrame.getContentPane().add(sDataField, "North");
            sFrame.getContentPane().add(new JScrollPane(sMessageArea), "Center");

            sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sFrame.pack();
            sFrame.setVisible(true);
            sMessageArea.setEditable(false);

            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");
            sMessageArea.setText("Client accepted\n");

            sMessageArea.append("Waiting for second client...\n");
            socket2 = server.accept();
            sMessageArea.append("Second client accepted\n");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());

            in2 = new DataInputStream(new BufferedInputStream(socket2.getInputStream()));
            out2 = new DataOutputStream(socket2.getOutputStream());

            String line = "";
            String line2 = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    if(in.available() != 0) {
                        line = in.readUTF();
                        sMessageArea.append(line);
                        out2.writeUTF(line);
                        out.writeUTF(line);
                        out2.flush();
                        out.flush();
                    }

                    else if(in2.available() != 0) {
                        line2 = in2.readUTF();
                        sMessageArea.append(line2);
                        out.writeUTF(line2);
                        out2.writeUTF(line2);
                        out.flush();
                        out2.flush();
                    }

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            socket2.close();
            in.close();
            in2.close();
            out.close();
            out2.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }

}

