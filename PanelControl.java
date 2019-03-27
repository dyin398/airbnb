import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.stage.*;
/**
 * Write a description of class PanelControl here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PanelControl extends Application
{
    // instance variables - replace the example below with your own
    private Scene currentScene;
    private Panel1 panel1;
    private Panel2 panel2;

    /**
     * Constructor for objects of class PanelControl
     */
    public PanelControl()
    {
        panel1 = new Panel1();
        panel2 = new Panel2();
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        currentScene = panel1.getScene();
        setListener(stage);
        setActionButtons(stage);
            
        stage.setTitle("Property Marketplace");
        stage.setScene(currentScene);
        
        //Show the Stage (window)
        stage.show();
    }
    
    private void toggleButtons()
    {
        int toPrice;
        int fromPrice;
        if (currentScene.equals(panel1.getScene())) {
            toPrice = panel1.getToPrice();
            fromPrice = panel1.getFromPrice();
            if (toPrice > fromPrice) {
                panel1.getBackButton().setDisable(false);
                panel1.getForwardButton().setDisable(false);
            }
            else {
                panel1.getBackButton().setDisable(true);
                panel1.getForwardButton().setDisable(true);
            }
        }
        else if (currentScene.equals(panel2.getScene())) {
            toPrice = panel2.getToPrice();
            fromPrice = panel2.getFromPrice();
            if (toPrice > fromPrice) {
                panel2.getBackButton().setDisable(false);
                panel2.getForwardButton().setDisable(false);
            }
            else {
                panel2.getBackButton().setDisable(true);
                panel2.getForwardButton().setDisable(true);
            }
        }
    }
    
    private Scene nextScene()
    {
        if (currentScene.equals(panel1.getScene())) {
            currentScene = panel2.getScene();
        }
        else if (currentScene.equals(panel2.getScene())) {
            currentScene = panel1.getScene();
        }
        return currentScene;
    }
    
    private Scene previousScene()
    {
        if (currentScene.equals(panel1.getScene())) {
            currentScene = panel2.getScene();
        }
        else if (currentScene.equals(panel2.getScene())) {
            currentScene = panel1.getScene();
        }
        return currentScene;
    }
    
    private void setActionButtons(Stage stage)
    {
        panel1.getBackButton().setOnAction(value -> 
        {
           stage.setScene(previousScene());
        }
        );
        
        panel1.getForwardButton().setOnAction(value -> 
        {
           stage.setScene(nextScene());
        }
        );
        
        panel2.getBackButton().setOnAction(value -> 
        {
           stage.setScene(previousScene());
        }
        );
        
        panel2.getForwardButton().setOnAction(value -> 
        {
           stage.setScene(nextScene());
        }
        );
    }
    
    private void setListener(Stage stage)
    {
        panel1.getPriceToButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel2.setPriceTo(newValue);
                panel2.updateToMap(newValue);
            }
            );
        
        panel1.getPriceFromButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel2.setPriceFrom(newValue);
                panel2.updateFromMap(newValue);
            }
            );
            
        panel2.getPriceToButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel1.setPriceTo(newValue);
                panel2.updateToMap(newValue);
            }
            );
        panel2.getPriceFromButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel1.setPriceFrom(newValue);
                panel2.updateFromMap(newValue);
            }
            );
    }
}