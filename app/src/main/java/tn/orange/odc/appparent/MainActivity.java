package tn.orange.odc.appparent;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.sembozdemir.viewpagerarrowindicator.library.ViewPagerArrowIndicator;

import org.json.JSONException;

import java.util.Random;

import tn.orange.odc.appparent.Fragments.SimpleFragment;
import tn.orange.odc.appparent.Network.RobotSocketManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ImgCentral;
    Button BtnCentral;
    TextView text1, text2, text3;
    ImageView Img1, Img2, Img3;
    ViewPager viewPager;
    ViewPagerArrowIndicator viewPagerArrowIndicator;


    Socket sock = null;


    //streaming attributes
    private static final String TAG = "ConnectActivity";
    private static final int CONNECTION_REQUEST = 1;
    private static final int REMOVE_FAVORITE_INDEX = 0;
    private static boolean commandLineRun = false;


    private SharedPreferences sharedPref;
    private String keyprefVideoCallEnabled;
    private String keyprefCamera2;
    private String keyprefResolution;
    private String keyprefFps;
    private String keyprefCaptureQualitySlider;
    private String keyprefVideoBitrateType;
    private String keyprefVideoBitrateValue;
    private String keyprefVideoCodec;
    private String keyprefAudioBitrateType;
    private String keyprefAudioBitrateValue;
    private String keyprefAudioCodec;
    private String keyprefHwCodecAcceleration;
    private String keyprefCaptureToTexture;
    private String keyprefNoAudioProcessingPipeline;
    private String keyprefAecDump;
    private String keyprefOpenSLES;
    private String keyprefDisableBuiltInAec;
    private String keyprefDisableBuiltInAgc;
    private String keyprefDisableBuiltInNs;
    private String keyprefEnableLevelControl;
    private String keyprefDisplayHud;
    private String keyprefTracing;
    private String keyprefRoomServerUrl;
    private String keyprefRoom;
    private String keyprefRoomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sock = RobotSocketManager.getRobotControllingSocket();


        sock.on("connect", onConnection);
        sock.on("message", onMessage);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.main_activity_layout);
        loadStringKey();

        text1 = (TextView) findViewById(R.id.textAppel);
        text2 = (TextView) findViewById(R.id.textRappel);
        text3 = (TextView) findViewById(R.id.textCom);

        Img1 = (ImageView) findViewById(R.id.imageAppel);
        Img2 = (ImageView) findViewById(R.id.imageRappel);
        Img3 = (ImageView) findViewById(R.id.imageCom);

        Img1.setOnClickListener(this);
        Img2.setOnClickListener(this);
        Img3.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerArrowIndicator = (ViewPagerArrowIndicator) findViewById(R.id.viewPagerArrowIndicator);
        viewPagerArrowIndicator.setArrowIndicatorRes(R.drawable.fg, R.drawable.fd);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                Log.e("page n :", position + "");
                if (position == 0) {
                    // piloter
                    // menu item  0 :
                    text1.setText("Appeler");
                    Img1.setBackground(getResources().getDrawable(R.drawable.appeler));
                    Img1.setContentDescription("Appeler");
                    // menu item  2 :
                    text2.setText("Rappeler");
                    Img2.setBackground(getResources().getDrawable(R.drawable.rappelermenu));
                    Img2.setContentDescription("Rappeler");
                    //menu item 3:
                    text3.setText("Commander");
                    Img3.setBackground(getResources().getDrawable(R.drawable.commandermenu));
                    Img3.setContentDescription("Commander");
                } else if (position == 1) {
                    // appel
                    // menu item  1 :
                    text1.setText("Rappeler");
                    Img1.setBackground(getResources().getDrawable(R.drawable.rappelermenu));
                    Img1.setContentDescription("Rappeler");
                    Img1.setContentDescription("Rappeler");
                    // menu item  2 :
                    text2.setText("Commander");
                    Img2.setBackground(getResources().getDrawable(R.drawable.commandermenu));
                    Img2.setContentDescription("Commander");
                    //menu item 3:
                    text3.setText("Piloter");
                    Img3.setBackground(getResources().getDrawable(R.drawable.pilotermenu));
                    Img3.setContentDescription("Piloter");


                } else if (position == 2) {
                    // rappel
                    text1.setText("Commander");
                    Img1.setBackground(getResources().getDrawable(R.drawable.commandermenu));
                    Img1.setContentDescription("Commander");
                    text2.setText("Piloter");
                    Img2.setBackground(getResources().getDrawable(R.drawable.pilotermenu));
                    Img2.setContentDescription("Piloter");
                    text3.setText("Appeler");
                    Img3.setBackground(getResources().getDrawable(R.drawable.appeler));
                    Img3.setContentDescription("Appeler");

                } else if (position == 3) {
                    text1.setText("Piloter");
                    Img1.setBackground(getResources().getDrawable(R.drawable.pilotermenu));
                    text1.setText("Piloter");

                    text2.setText("Appeler");
                    Img2.setBackground(getResources().getDrawable(R.drawable.appeler));
                    text2.setText("Appeler");

                    text3.setText("Rappeler");
                    Img3.setBackground(getResources().getDrawable(R.drawable.rappelermenu));
                    Img3.setContentDescription("Rappeler");

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerArrowIndicator.bind(viewPager);

    }

    @Override
    public void onClick(View view) {
        // ui menu current item
        // Piloter : 0 ; Appeler : 1; Rappeler :2 ; Commander: 3;
        if (view instanceof ImageView) {
            if (view.getContentDescription().toString().equalsIgnoreCase("Appeler")) {


                connectToRoom("rommyroom1", false, false, 0, true);
                viewPager.setCurrentItem(1);
            } else if (view.getContentDescription().toString().equalsIgnoreCase("Rappeler")) {
                viewPager.setCurrentItem(2);
            } else if (view.getContentDescription().toString().equalsIgnoreCase("Commander")) {
                viewPager.setCurrentItem(3);
            } else if (view.getContentDescription().toString().equalsIgnoreCase("Piloter")) {
                viewPager.setCurrentItem(0);
            }
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return SimpleFragment.newInstance("Piloter");
                case 1:
                    return SimpleFragment.newInstance("Appeler");

                case 2:
                    return SimpleFragment.newInstance("Rappeler");
                case 3:
                    return SimpleFragment.newInstance("Commander");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    private void connectToRoom(
            String roomId, boolean commandLineRun, boolean loopback, int runTimeMs, boolean call) {
        this.commandLineRun = commandLineRun;

        // roomId is random for loopback.

        if (call){
            roomId = Integer.toString((new Random()).nextInt(100000000));
            String stringToSend = "call:" + roomId;
            sock.emit(RobotSocketManager.PARENT_ORDER_EVENT, stringToSend);
        }




        String roomUrl = sharedPref.getString(
                keyprefRoomServerUrl,
                getString(R.string.pref_room_server_url_default));

        // Video call enabled flag.
        boolean videoCallEnabled = sharedPref.getBoolean(keyprefVideoCallEnabled,
                Boolean.valueOf(getString(R.string.pref_videocall_default)));

        // Use Camera2 option.
        boolean useCamera2 = sharedPref.getBoolean(keyprefCamera2,
                Boolean.valueOf(getString(R.string.pref_camera2_default)));

        // Get default codecs.
        String videoCodec = sharedPref.getString(keyprefVideoCodec,
                getString(R.string.pref_videocodec_default));
        String audioCodec = sharedPref.getString(keyprefAudioCodec,
                getString(R.string.pref_audiocodec_default));

        // Check HW codec flag.
        boolean hwCodec = sharedPref.getBoolean(keyprefHwCodecAcceleration,
                Boolean.valueOf(getString(R.string.pref_hwcodec_default)));

        // Check Capture to texture.
        boolean captureToTexture = sharedPref.getBoolean(keyprefCaptureToTexture,
                Boolean.valueOf(getString(R.string.pref_capturetotexture_default)));

        // Check Disable Audio Processing flag.
        boolean noAudioProcessing = sharedPref.getBoolean(
                keyprefNoAudioProcessingPipeline,
                Boolean.valueOf(getString(R.string.pref_noaudioprocessing_default)));

        // Check Disable Audio Processing flag.
        boolean aecDump = sharedPref.getBoolean(
                keyprefAecDump,
                Boolean.valueOf(getString(R.string.pref_aecdump_default)));

        // Check OpenSL ES enabled flag.
        boolean useOpenSLES = sharedPref.getBoolean(
                keyprefOpenSLES,
                Boolean.valueOf(getString(R.string.pref_opensles_default)));

        // Check Disable built-in AEC flag.
        boolean disableBuiltInAEC = sharedPref.getBoolean(
                keyprefDisableBuiltInAec,
                Boolean.valueOf(getString(R.string.pref_disable_built_in_aec_default)));

        // Check Disable built-in AGC flag.
        boolean disableBuiltInAGC = sharedPref.getBoolean(
                keyprefDisableBuiltInAgc,
                Boolean.valueOf(getString(R.string.pref_disable_built_in_agc_default)));

        // Check Disable built-in NS flag.
        boolean disableBuiltInNS = sharedPref.getBoolean(
                keyprefDisableBuiltInNs,
                Boolean.valueOf(getString(R.string.pref_disable_built_in_ns_default)));

        // Check Enable level control.
        boolean enableLevelControl = sharedPref.getBoolean(
                keyprefEnableLevelControl,
                Boolean.valueOf(getString(R.string.pref_enable_level_control_key)));

        // Get video resolution from settings.
        int videoWidth = 0;
        int videoHeight = 0;
        String resolution = sharedPref.getString(keyprefResolution,
                getString(R.string.pref_resolution_default));
        String[] dimensions = resolution.split("[ x]+");
        if (dimensions.length == 2) {
            try {
                videoWidth = Integer.parseInt(dimensions[0]);
                videoHeight = Integer.parseInt(dimensions[1]);
            } catch (NumberFormatException e) {
                videoWidth = 0;
                videoHeight = 0;
                Log.e(TAG, "Wrong video resolution setting: " + resolution);
            }
        }

        // Get camera fps from settings.
        int cameraFps = 0;
        String fps = sharedPref.getString(keyprefFps,
                getString(R.string.pref_fps_default));
        String[] fpsValues = fps.split("[ x]+");
        if (fpsValues.length == 2) {
            try {
                cameraFps = Integer.parseInt(fpsValues[0]);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Wrong camera fps setting: " + fps);
            }
        }

        // Check capture quality slider flag.
        boolean captureQualitySlider = sharedPref.getBoolean(keyprefCaptureQualitySlider,
                Boolean.valueOf(getString(R.string.pref_capturequalityslider_default)));

        // Get video and audio start bitrate.
        int videoStartBitrate = 0;
        String bitrateTypeDefault = getString(
                R.string.pref_startvideobitrate_default);
        String bitrateType = sharedPref.getString(
                keyprefVideoBitrateType, bitrateTypeDefault);
        if (!bitrateType.equals(bitrateTypeDefault)) {
            String bitrateValue = sharedPref.getString(keyprefVideoBitrateValue,
                    getString(R.string.pref_startvideobitratevalue_default));
            videoStartBitrate = Integer.parseInt(bitrateValue);
        }
        int audioStartBitrate = 0;
        bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
        bitrateType = sharedPref.getString(
                keyprefAudioBitrateType, bitrateTypeDefault);
        if (!bitrateType.equals(bitrateTypeDefault)) {
            String bitrateValue = sharedPref.getString(keyprefAudioBitrateValue,
                    getString(R.string.pref_startaudiobitratevalue_default));
            audioStartBitrate = Integer.parseInt(bitrateValue);
        }

        // Check statistics display option.
        boolean displayHud = sharedPref.getBoolean(keyprefDisplayHud,
                Boolean.valueOf(getString(R.string.pref_displayhud_default)));

        boolean tracing = sharedPref.getBoolean(
                keyprefTracing, Boolean.valueOf(getString(R.string.pref_tracing_default)));

        // Start AppRTCDemo activity.
        Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
        if (validateUrl(roomUrl)) {
            Uri uri = Uri.parse(roomUrl);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setData(uri);
            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
            intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
            intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                    captureQualitySlider);
            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED,
                    noAudioProcessing);
            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
            intent.putExtra(CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, enableLevelControl);
            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
            intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
            intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
            intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
            intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);

            startActivityForResult(intent, CONNECTION_REQUEST);
        }
    }


    void loadStringKey() {


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        keyprefVideoCallEnabled = getString(R.string.pref_videocall_key);
        keyprefCamera2 = getString(R.string.pref_camera2_key);
        keyprefResolution = getString(R.string.pref_resolution_key);
        keyprefFps = getString(R.string.pref_fps_key);
        keyprefCaptureQualitySlider = getString(R.string.pref_capturequalityslider_key);
        keyprefVideoBitrateType = getString(R.string.pref_startvideobitrate_key);
        keyprefVideoBitrateValue = getString(R.string.pref_startvideobitratevalue_key);
        keyprefVideoCodec = getString(R.string.pref_videocodec_key);
        keyprefHwCodecAcceleration = getString(R.string.pref_hwcodec_key);
        keyprefCaptureToTexture = getString(R.string.pref_capturetotexture_key);
        keyprefAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
        keyprefAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
        keyprefAudioCodec = getString(R.string.pref_audiocodec_key);
        keyprefNoAudioProcessingPipeline = getString(R.string.pref_noaudioprocessing_key);
        keyprefAecDump = getString(R.string.pref_aecdump_key);
        keyprefOpenSLES = getString(R.string.pref_opensles_key);
        keyprefDisableBuiltInAec = getString(R.string.pref_disable_built_in_aec_key);
        keyprefDisableBuiltInAgc = getString(R.string.pref_disable_built_in_agc_key);
        keyprefDisableBuiltInNs = getString(R.string.pref_disable_built_in_ns_key);
        keyprefEnableLevelControl = getString(R.string.pref_enable_level_control_key);
        keyprefDisplayHud = getString(R.string.pref_displayhud_key);
        keyprefTracing = getString(R.string.pref_tracing_key);
        keyprefRoomServerUrl = getString(R.string.pref_room_server_url_key);
        keyprefRoom = getString(R.string.pref_room_key);
        keyprefRoomList = getString(R.string.pref_room_list_key);


    }


    private boolean validateUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true;
        }

        new AlertDialog.Builder(this)
                .setTitle(getText(R.string.invalid_url_title))
                .setMessage(getString(R.string.invalid_url_text, url))
                .setCancelable(false)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
        return false;
    }


    private Emitter.Listener onConnection = new Emitter.Listener() {
        @Override
        public void call(Object... args) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(MainActivity.this, "Your connected to the socket server", Toast.LENGTH_SHORT).show();


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

                        connectToRoom(idToCall, false, false, 0, false);

                    }


                    Toast.makeText(MainActivity.this, "Message: " + recievedString, Toast.LENGTH_SHORT).show();


                }
            });
        }


    };


}