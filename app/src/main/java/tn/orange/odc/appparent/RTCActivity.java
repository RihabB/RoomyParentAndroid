package tn.orange.odc.appparent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.webrtc.MediaStream;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tn.orange.odc.appparent.Joystick.JoyStickClass;
import tn.orange.odc.appparent.Network.RobotSocketManager;
import tn.orange.odc.appparent.Streaming.PeerConnectionParameters;



public class RTCActivity extends Activity {

    private String callerId;


    @Bind(R.id.glview_call)
    GLSurfaceView vsv;

    @Bind(R.id.call)
    Button callBtn;


    @Bind(R.id.layout_joystick)
    RelativeLayout layout_joystick;

    JoyStickClass js;
    Socket sock = null;


    String myId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(
                LayoutParams.FLAG_FULLSCREEN
                        | LayoutParams.FLAG_KEEP_SCREEN_ON
                        | LayoutParams.FLAG_DISMISS_KEYGUARD
                        | LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_rtc);

        ButterKnife.bind(this);



        vsv.setPreserveEGLContextOnPause(true);
        vsv.setKeepScreenOn(true);


        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stringToSend = "call:" + myId;
                sock.emit(RobotSocketManager.PARENT_ORDER_EVENT, stringToSend);


            }
        });


        //  mSocketAddress += (":" + getResources().getString(R.string.port) + "/");




        //controlling robot config

        sock = RobotSocketManager.getRobotControllingSocket();

        // socket connection
        sock.connect();


        sock.on("connect", onConnection);
        sock.on("message", onMessage);
        initializeJoystick();


    }


    private Emitter.Listener onConnection = new Emitter.Listener() {
        @Override
        public void call(Object... args) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(RTCActivity.this, "Your connected to the socket server", Toast.LENGTH_SHORT).show();


                }
            });
        }


    };


    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String recievedString = args[0].toString();

                    if (recievedString.contains("call")) {

                        String[] parts = recievedString.split(":");
                        String idToCall = parts[1];

                    }


                    Toast.makeText(RTCActivity.this, "Message: " + recievedString, Toast.LENGTH_SHORT).show();


                }
            });
        }


    };


    private void initializeJoystick() {
        js = new JoyStickClass(RTCActivity.this
                , layout_joystick, R.drawable.joy22);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    int direction = js.get8Direction();


                    sock.emit(RobotSocketManager.PARENT_ORDER_EVENT, direction);


                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        });

    }



    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {


        super.onDestroy();
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }






}