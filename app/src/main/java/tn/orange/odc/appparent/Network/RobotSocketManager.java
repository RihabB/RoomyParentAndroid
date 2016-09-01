package tn.orange.odc.appparent.Network;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by odc on 22/07/2016.
 */
public class RobotSocketManager {

    public static String PARENT_ORDER_EVENT = "chat";

    public static Socket robotControllingSocket;


    public static Socket getRobotControllingSocket() {


        if (robotControllingSocket == null)
            try {
                robotControllingSocket =  IO.socket(ApiConstants.CHAT_SERVER_URL);
            } catch (URISyntaxException e) {

                Log.d("robot socket",e.getMessage());


            }


        return robotControllingSocket;

    }
}
