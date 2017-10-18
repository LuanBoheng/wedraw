package Model;

import Data.Message;
import GUI.Layout.WhiteBoard;
import GUI.run.LoadingPageController;
import GUI.run.TimerThread;
import GUI.run.run;
import Network.Network;

import java.time.LocalDateTime;

import static GUI.Layout.Client.chatBox;

/**
 * Created by luanboheng on 17/10/2017.
 */
public class AdminInteraction {
    String userName;
    Network net;


    @Override
    public String toString() {
        return "<Admin>"+userName+",\t"+net;
    }

    //Chatbox chatbox;
    //where Chatbox is a type that have methods that include add new lines on the Chat display field
    //and get text form the textfield
    //This interaction model will be called by timer and the actions that "sent"button "kick"button
    //and some method that allow join request


    public AdminInteraction(Network net, String userName){
        this.net =net;
        this.userName = userName;

    }


    public void handleMessage(Message m){
        switch (m.type()){
            case Message.CREATE_CANVAS:handle_CREATE_CANVAS(m);break;
            case Message.SAVE_CANVAS:handle_SAVE_CANVAS(m);break;
            case Message.LOAD_CANVAS:handle_LOAD_CANVAS(m);break;
            case Message.CHAT_MESSAGE:handle_CHAT_MESSAGE(m);break;
            case Message.JOIN_REQUEST:handle_JOIN_REQUEST(m);break;
            case Message.JOIN_RESPONSE:handle_JOIN_RESPONSE(m);break;
            case Message.KICK_USER:handle_KICK_USER(m);break;
            case Message.USER_GOT_KICKED:handle_USER_GOT_KICKED(m);break;
        }
    }



    public void handle_LOAD_CANVAS(Message m){}

    public void handle_CREATE_CANVAS(Message m){
        String stringMessage = LocalDateTime.now()+":\n"+m.content();
        //弹窗，显示 stringMessages
        System.out.println(stringMessage);
        LoadingPageController controllerL = run.c;
        if (stringMessage.contains("success")){
            run.c.show_whiteboard(run.c.manager_whiteboard);

        }else{
            run.c.dialog_fail();
        }

    }


    public void handle_SAVE_CANVAS(Message m){
        String stringMessage = LocalDateTime.now()+":\n"+m.content();
        //弹窗，显示 stringMessage
        run.c.save_success();

    }

    public void handle_CHAT_MESSAGE(Message m){
        String stringMessage = LocalDateTime.now()+"\t"+m.username()+":\n"+m.content();

        chatBox.appendText(stringMessage);
    }

    public void handle_JOIN_REQUEST(Message m){
        //弹出弹窗一个true 或者 false 到 response 里面去
        String response= run.c.join_request(m.username());

        Send_JOIN_RESPONSE(m.username(), response);

        Send_CHAT_MESSAGE("user \""+m.username()+"\" join the canvas");

    }

    public void handle_JOIN_RESPONSE(Message m){
        if (m.content()==null){

           run.c.client_join_response();

        }
        else{

            run.c.show_whiteboard(run.c.manager_whiteboard);

        }



    }

    public void handle_KICK_USER(Message m){
        Send_CHAT_MESSAGE("user \""+m.content()+"\" has been kicked out");
    }

    public void handle_USER_GOT_KICKED(Message m){
        //弹窗，你被踢了
        //炸裂，退出程序
        run.c.kicked();

    }

    public void Send_UNDO(){
        Message message =  new Message(userName,Message.UNDO,null);
        net.sendMessage(message);
    }


    public void Send_CREATE_CANVAS(String canvasName) {
        Message message =  new Message(userName,Message.CREATE_CANVAS,canvasName);
        try {

            net.sendMessage(message);
        }catch (Exception e){

            run.timerThread.stop();
            TimerThread.timer.cancel();
            run.c.logs.setText("  ");

            run.c.dialog_fail();


        }
    }

    public void Send_SAVE_CANVAS(String canvasName){
        //canvasName 是本地保存的 canvasName
        Message message =  new Message(userName,Message.SAVE_CANVAS,canvasName);
        net.sendMessage(message);
    }

    public void Send_LOAD_CANVAS(String canvasName){
        //canvasName 是本地保存的 canvasName
        Message message =  new Message(userName,Message.LOAD_CANVAS,canvasName);
        net.sendMessage(message);
    }

    public void Send_CHAT_MESSAGE  (String text){
        text = ""+LocalDateTime.now() +"\n"+text;
        Message message =  new Message(userName,Message.CHAT_MESSAGE,text);
        net.sendMessage(message);
    }

    public void Send_KICK_USER (String UserToBeKicked){
        Message message =  new Message(userName,Message.KICK_USER,UserToBeKicked);
        net.sendMessage(message);
    }

    public void Send_JOIN_REQUEST (String canvasName){
        Message message =  new Message(userName,Message.JOIN_REQUEST,canvasName);
        net.sendMessage(message);
    }

    public void Send_JOIN_RESPONSE (String name, String response){
        String[] name_response = new String[2];
        name_response[0] = name;
        name_response[1] = response;
        Message message =  new Message(userName,Message.JOIN_RESPONSE,response);
        net.sendMessage(message);
    }

}
