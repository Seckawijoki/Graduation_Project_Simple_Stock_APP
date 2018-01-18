package com.seckawijoki.graduation_project.functions.TEST;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.SZ;
import com.seckawijoki.graduation_project.constants.server.OldServerPath;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.Arrays;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/25.
 */

class TESTModelImpl extends WebSocketClient implements TESTContract.Model {
  private static final String TAG = "TESTModelImpl";
  private static final Handler handler = new Handler(  );
  private DataCallback callback;
  private int repeatRequestTimes = 0;
  private static final int MAX_REQUEST_TIME = 5;

  TESTModelImpl() throws URISyntaxException {
    super( new URI( OldServerPath.WITH_WIFI_CONNECTION ) );
  }

  @Override
  public void initiate() {
    connect();
  }

  @Override
  public void destroy() {
    callback = null;
  }

  @Override
  public void setDataCallback( DataCallback callback ) {
    this.callback = callback;
  }

  @Override
  public void requestConnectToServer() {
    connect();
  }

  @Override
  public void requestNormalDownload( CharSequence s ) {
    ++repeatRequestTimes;
    try {
      Log.i( TAG, "requestNormalDownload(): s = " + s );
      if ( TextUtils.isEmpty( s ) )
        send( TAG + ".requestNormalDownload() " );
      else
        send( s.toString() );
      repeatRequestTimes = 0;
    } catch ( WebsocketNotConnectedException e ) {
      Log.e( TAG, "requestNormalDownload(): \n", e );
      if ( repeatRequestTimes > MAX_REQUEST_TIME ) {
        repeatRequestTimes = 0;
        callback.onDisplayReceptionEditText(
                e.getMessage()  + "\n"
                + getResourceDescriptor() + "\n" );
        return;
      }
      requestNormalDownload( s );
    }
  }

  @Override
  public void requestGetSzIdAndName() {
    Log.i( TAG, "requestGetSzIdAndName(): " );
    try {
      send( OldServerPath.GET_SZ_ID_AND_NAME );
    } catch ( NotYetConnectedException e ) {

    }
  }

  @Override
  public void onOpen( ServerHandshake handShakeData ) {
    Log.e( TAG, "onOpen(): " + handShakeData );
  }

  @Override
  public void onMessage( String message ) {
    Log.i( TAG, "onMessage(): message = " + message );
    try {
      JSONArray jsonArray = new JSONArray( message );
      int count = jsonArray.length();
      String[] ids = new String[count];
      String[] names = new String[count];
      for ( int i = 0 ; i < count ; i++ ) {
        JSONObject jsonObject = jsonArray.getJSONObject( i );
        ids[i] = jsonObject.getString( SZ.ID );
        names[i] = jsonObject.optString( SZ.NAME );
      }
      final String id = Arrays.toString(ids);
      final String name = Arrays.toString(names);
      Log.d( TAG, "onMessage(): ids = " + id );
      Log.d( TAG, "onMessage(): names = " + name );
      handler.post( new Runnable() {
        @Override
        public void run() {
          callback.onDisplaySendEditText( id + "\n" + name );
        }
      } );
//      callback.onDisplayReceptionEditText(ids, names);
    } catch ( JSONException e ) {
      Log.e( TAG, "onMessage(): not matched: " + e );
    }
  }

  @Override
  public void onClose( int code, String reason, boolean remote ) {
    Log.e( TAG, "onClose(): " + reason );
  }

  @Override
  public void onError( Exception ex ) {
    Log.e( TAG, "onError(): ", ex );
  }
}