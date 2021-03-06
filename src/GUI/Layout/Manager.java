package GUI.Layout;

import Data.Users;
import GUI.Tools.Tool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static GUI.DrawSettings.DrawSettings.color;
import static GUI.run.TimerThread.admModel;

public class Manager extends WhiteBoard{


    VBox V_list = new VBox();

    HBox H_list = new HBox();

    public VBox Button_list = new VBox();

    VBox V_Print = new VBox();
    public TextArea chatBox = new TextArea();



    public static String KICK_ICON = "/images/kickoff.png";

    Image kick = new Image(getClass().getResourceAsStream(KICK_ICON),30, 30, true, true);

    String local_Host;

    int port;

    ServerSocket s;

    List<Users> user_list = new LinkedList<Users>();
    public List<Button> user_button_list = new LinkedList<Button>();

    int user_number = 0;
//    int max_user_number = 6;


    public String manager_name = "Manager";

//    Button test1 = new Button("test 1 ");





    public Manager(Users user,String canvas_name) {

        chatBox.setWrapText(true);


        manager_name = user.getUser_name();


        Label label2 = new Label("       Canvas name: "+canvas_name);
        label2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        Label label3 = new Label("       Manager Name: " +manager_name);
        label3.setFont(Font.font("Verdana", FontWeight.BOLD, 15));



        V_list.getChildren().add(H_list);


        H_list.setAlignment(Pos.CENTER);

        V_Print.getChildren().add(label2);
        V_Print.getChildren().add(label3);
        V_Print.setAlignment(Pos.CENTER);

//        V_Print.getChildren().add(test1);
        topControls.getChildren().add(V_Print);



//        getChildren().add(left_right);


        Label label1 = new Label("Current Users: ");
        V_list.getChildren().add(label1);

//        class BottomList extends ArrayList<Button>{
//            public Button getfromName(String Name){}
//        }
//
////        BottonList.add();
////        BottonList.remove();



        V_list.getChildren().add(Button_list);
        V_list.setPadding(new Insets(5));

        Pane spacePane = new Pane();

        V_list.setVgrow(spacePane, Priority.ALWAYS);

        V_list.getChildren().add(spacePane);



        Label chat_label = new Label("Chat Box");


        V_list.getChildren().add(chat_label);


        chatBox.setPrefSize(300,400);
        chatBox.setEditable(false);

        TextArea inputTxt = new TextArea();
        inputTxt.setPrefSize(230,50);

        Button send_btn = new Button("Send");
        send_btn.setPrefSize(70,70);

        send_btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                admModel.Send_CHAT_MESSAGE(inputTxt.getText());
//                chatBox.appendText(inputTxt.getText());
                inputTxt.setText(null);
            }
        });



//
//        test1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent arg0) {
////                generate_user(user);
//            }
//        });


        V_list.getChildren().add(chatBox);


        HBox chat_input = new HBox();

        chat_input.getChildren().add(inputTxt);
        chat_input.getChildren().add(send_btn);

        V_list.getChildren().add(chat_input);

        V_list.setMargin(chat_input,new Insets(15,0,0,0) );


        setRight(V_list);

        V_list.toFront();

        chatBox.setText("Hello! "+manager_name+"\nYour are the manager of canvas"+canvas_name);

    }


    public void kick_user(Button btn){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you sure to kick this client out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Button_list.getChildren().remove(btn);
            admModel.Send_KICK_USER(btn.getText());
//            System.out.println(btn.getText());
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }
    public String join_request_dialog(String client_name){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setContentText(client_name +" want to join");

        ButtonType buttonTypeOne = new ButtonType("Allow");
        ButtonType buttonTypeTwo = new ButtonType("Reject");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return "T";
        }
        else if (result.get() == buttonTypeTwo) {
            return "F";

        }
        else
            return "F";

    }











    public void generate_user(String user){

            user_button_list.add(new Button());
            user_button_list.get(user_number).setText(user);
            user_button_list.get(user_number).setPrefSize(300,40);
            Button_list.getChildren().add(user_button_list.get(user_number));

            Button btn = user_button_list.get(user_number);
            btn.setGraphic(new Circle(10,10,5, Color.GREEN));

            btn.setOnMouseEntered(new EventHandler<MouseEvent>
                        () {
                    @Override
                    public void handle(MouseEvent t) {
    //                btn4.setStyle("-fx-background-color:#dae7f3;");
                        btn.setGraphic(new ImageView(kick));
                    }
                });

                 btn.setOnMouseExited(new EventHandler<MouseEvent>
                    () {
                @Override
                public void handle(MouseEvent t) {
                    btn.setGraphic(new Circle(10,10,5, Color.GREEN));
                }
            });

            btn.setOnAction(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent arg0) {
                            kick_user(btn);
                        }
                    });

            user_number+=1;

    }



//            user_button_list.get(user_number).setOnAction(new EventHandler<ActionEvent>() {
//
//                public void handle(ActionEvent arg0) {
//                    V_list.getChildren().remove(user_button_list.get(user_number));
//
//
//                }
//            });


    }







//    ListView<String> list = new ListView<String>();
//    ObservableList<String> items = FXCollections.observableArrayList(
//            "Manager");
//        list.setItems(items);
//
//                list.setStyle("-fx-background-color: yellow");
//
//
//                HBox hBox = new HBox();
//                hBox.setSpacing(5);
//
//                list.setPrefWidth(100);
//                list.setPrefHeight(70);
//
//                Text name = new Text("Manager");
//                name.setFont(new Font("Arial", 20));
//                ImageView statusImageView = new ImageView();
//                Image statusImage = new Image("images/online.png", 16, 16, true, true);
//                statusImageView.setImage(statusImage);
//
//                hBox.getChildren().addAll(statusImageView, name);
//
//
//                left_right.getChildren().add(new WhiteBoard());
//                left_right.getChildren().add(list);
//
