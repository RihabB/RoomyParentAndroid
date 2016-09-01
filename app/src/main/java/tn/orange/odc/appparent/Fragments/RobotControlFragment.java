package tn.orange.odc.appparent.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import tn.orange.odc.appparent.Joystick.JoyStickClass;
import tn.orange.odc.appparent.Network.ApiConstants;
import tn.orange.odc.appparent.Network.RobotSocketManager;
import tn.orange.odc.appparent.R;

/**
 * Created by odc on 21/07/2016.
 */
public class RobotControlFragment extends Fragment {

    @Bind(R.id.txt_x)
    TextView txt_x;

    @Bind(R.id.txt_y)
    TextView txt_y;

    @Bind(R.id.txt_direction)
    TextView txt_direction;

    @Bind(R.id.txt_angle)
    TextView txt_angle;

    @Bind(R.id.txt_distance)
    TextView txt_distance;

    @Bind(R.id.layout_joystick)
    RelativeLayout layout_joystick;

    JoyStickClass js;
    Socket sock = null;

    public RobotControlFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_robot_control, container, false);
        ButterKnife.bind(this, view);


            sock = RobotSocketManager.getRobotControllingSocket();

        // socket connection
        sock.connect();



        sock.on("connect", onConnection);
        sock.on("message", onMessage);
        initializeJoystick();


        return view;

    }




    private Emitter.Listener onConnection = new Emitter.Listener() {
        @Override
        public void call(Object... args) {




            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Your connected to the socket server", Toast.LENGTH_SHORT).show();


                }
            });
        }



    };




    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {




            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Message: " + args[0].toString(), Toast.LENGTH_SHORT).show();


                }
            });
        }



    };



    private void initializeJoystick() {
        js = new JoyStickClass(getActivity()
                , layout_joystick, R.drawable.image_button);
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
                    txt_x.setText("X : " + String.valueOf(js.getX()));
                    txt_y.setText("Y : " + String.valueOf(js.getY()));
                    txt_angle.setText("Angle : " + String.valueOf(js.getAngle()));
                    txt_distance.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();


                    sock.emit(RobotSocketManager.PARENT_ORDER_EVENT, direction);




                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    txt_x.setText("X :");
                    txt_y.setText("Y :");
                    txt_angle.setText("Angle :");
                    txt_distance.setText("Distance :");
                    txt_direction.setText("Direction :");
                }
                return true;
            }
        });

    }


}
