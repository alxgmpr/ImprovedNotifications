/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @FXML
    private Button taskButton1;
    
    @FXML
    private Button taskButton2;
    
    @FXML
    private Button taskButton3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event){
        
        if (task1 == null || task1.getTaskState() == TaskState.STOPPED) {
            
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            
            taskButton1.setText("End task 1");
        } else {
            
            task1.end();
            taskButton1.setText("Task 1");
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            taskButton1.setText("Task 1");
        }
        
        if (message.equals("Task2 done.")) {
            task2 = null;
            taskButton2.setText("Task 2");
        }
        
        if (message.equals("Task3 done.")) {
            task3 = null;
            taskButton3.setText("Task 3");
        }
        
        
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null || task2.getTaskState() == TaskState.STOPPED) {
            if (task2 != null) {
                task2.end();
            }
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            task2.start();
            
            taskButton2.setText("Stop task 2");
        } else {
            task2.end();
            taskButton2.setText("Task 2");
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null || task3.getTaskState() == TaskState.STOPPED) {
            if (task3 != null) {
                task3.end();
            }
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
            taskButton3.setText("Stop task 3");
        } else {
            task3.end();
            taskButton3.setText("Task 3");
        }
    } 
}
