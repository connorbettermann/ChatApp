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

public class GroupServer
{
    //initialize socket and input stream
    private Socket[]          socket   = null;
    private ServerSocket[]    server   = null;
    private DataInputStream[] in       =  null;
    private DataOutputStream[] out     = null;
    private int MAX = 10;

    public JFrame sFrame = new JFrame("GroupServer");
    public JTextField sDataField = new JTextField(40);
    public JTextArea sMessageArea = new JTextArea(8, 60);


    // constructor with port
    public GroupServer(int port)
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

            server = new ServerSocket[MAX];
            int i = joinUser(socket, server, port);


            // takes input from the client socket
            in[i] = new DataInputStream(
                    new BufferedInputStream(socket[i].getInputStream()));

            out[i] = new DataOutputStream(socket[i].getOutputStream());

            String[] line = new String[MAX];
            for(int j = 0; j < MAX; j++)
            {
                line[j] = "";
            }



            // reads message from client until "Over" is sent
            while (!line[i].equals("Over"))
            {
                try
                {

                    line[i] = in[i].readUTF();
                    sMessageArea.append(line[i]);
                    out[i].writeUTF(line[i]);
                    out[i].flush();

                }
                catch(IOException ex)
                {
                    System.out.println(ex);
                }
            }
            System.out.println("Closing connection");

            // close connection
            for(int k = 0; k < MAX; k++) {
                socket[k].close();
                in[k].close();
                out[k].close();
            }
        }
        catch(IOException exx)
        {
            System.out.println(exx);
        }
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }

    private int joinUser(Socket[] in_socket, ServerSocket[] in_server, int port)
    {
        try
        {
            int i = 0;
            while(server[i] != null)
            {
                i++;
            }
            in_server[i] = new ServerSocket(port);

            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket[i] = server[i].accept();
            System.out.println("Client accepted");
            sMessageArea.setText("Client accepted\n");
            return i;
        }catch(IOException e)
        {
            System.out.println(e);
        }
        return -1;
    }
}

