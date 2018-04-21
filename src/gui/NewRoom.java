package gui;

import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class NewRoom {
    private Stage owner;
    private int widthScene=400;
    private int heightScene=200;
    private int widthStage=400;
    private int heightStage=200;
    private String title = "Nowy Pokój";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;
    private String rootStyle ="-fx-background-color: #FFFFFF;";

    public NewRoom(){
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        setStageProperty();
        setHBoxProperty();
    }

    public void setStageProperty(){
        owner.setScene(scene);
        owner.setTitle(title);
        owner.setWidth(widthStage);
        owner.setHeight(heightStage);
        owner.initModality(Modality.WINDOW_MODAL);
        owner.show();
    }

    public void setHBoxProperty() {
        root.setStyle(rootStyle);
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showNewRoom(Stage rooms){
        HBox nameHBox = setNameHBox();
        HBox gamerHBOX = setGamerHBox();
        HBox bttnHBox = setBttnHBox(rooms);

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(nameHBox, gamerHBOX, bttnHBox);
    }

    public HBox setNameHBox(){
        HBox nameHBox = new HBox();

        Label name = new Label("Name  ");

        TextField nameField = new TextField();
        nameField.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.getChildren().addAll(name, nameField);
        return nameHBox;
    }

    public HBox setGamerHBox(){
        HBox gamerHBox = new HBox();

        Label gamer = new Label("Ilość graczy  ");

        ChoiceBox cb = setChoice();

        gamerHBox.setAlignment(Pos.CENTER);
        gamerHBox.getChildren().addAll(gamer, cb);

        return gamerHBox;
    }

    public ChoiceBox setChoice(){
        ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll("2", "3", "4");
        cb.getSelectionModel().selectFirst();
        return cb;
    }

    public HBox setBttnHBox(Stage rooms){
        HBox bttnHBox = new HBox();

        Button create = new Button("Create room");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ProgressMaking(rooms);
                owner.close();
            }
        });

        bttnHBox.setAlignment(Pos.CENTER_RIGHT);
        bttnHBox.getChildren().addAll(create);
        return bttnHBox;
    }

    public void ProgressMaking(Stage rooms){
        Waiting pForm = new Waiting();
        pForm.Waiting();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                for(int i = 0; i < 100000; i++){
                    System.out.println(i);
                }
                return null ;
            }
        };
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
            rooms.close();

            Game actualGame = new Game();
            actualGame.showActualGame();
        });
        pForm.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }


}
