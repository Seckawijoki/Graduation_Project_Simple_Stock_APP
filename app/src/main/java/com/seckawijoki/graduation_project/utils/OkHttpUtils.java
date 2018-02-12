package com.seckawijoki.graduation_project.utils;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/14 at 15:32.
 */

public class OkHttpUtils {
  private static final String TAG = "OkHttpUtils";
  private static ExecutorService threadPool;
  private static final int TIMEOUT_SECONDS = 10;

  private static OkHttpClient newOkHttpClient() {
    return new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build();
  }

  private OkHttpUtils() {
  }

  public static void init() {
    threadPool = Executors.newFixedThreadPool(4);
  }

  public static GetBuilder get() {
    return new GetBuilder();
  }

  public static PostBuilder post() {
    return new PostBuilder();
  }

  public static PostFileBuilder postFile() {
    return new PostFileBuilder();
  }

  public static GetFileBuilder getFile() {
    return new GetFileBuilder();
  }

  public static class PostBuilder {
    private Request.Builder requestBuilder = new Request.Builder();
    private FormBody.Builder formBodyBuilder = new FormBody.Builder();

    private PostBuilder() {
    }

    public PostBuilder url(String url) {
      requestBuilder.url(url);
      return this;
    }

    public PostBuilder addParam(String name, String value) {
      formBodyBuilder.add(name, value);
      return this;
    }

    public ResponseResult execute() {
      requestBuilder.post(formBodyBuilder.build());
      Callable<String> callable = () -> {
        OkHttpClient okHttpClient = OkHttpUtils.newOkHttpClient();
        Response response =
                okHttpClient.newCall(requestBuilder.build()).execute();
        return (String) response.body().string();
      };
      Future<String> future = threadPool.submit(callable);
      try {
        return new ResponseResult(future.get());
      } catch ( InterruptedException | ExecutionException e ) {
        Log.e(TAG, "execute()\n: ", e);
        return null;
      }
    }
    public Response executeForResponse() {
      requestBuilder.post(formBodyBuilder.build());
      OkHttpClient okHttpClient = OkHttpUtils.newOkHttpClient();
      Callable<Response> callable = () -> {
        Response response =
                okHttpClient.newCall(requestBuilder.build()).execute();
        return response;
      };
      Future<Response> future = threadPool.submit(callable);
      try {
        return future.get();
      } catch ( InterruptedException | ExecutionException e ) {
        Log.e(TAG, "execute()\n: ", e);
        return null;
      }
    }
  }

  public static class GetBuilder {
    private Request.Builder requestBuilder = new Request.Builder();

    GetBuilder() {
      requestBuilder.get();
    }

    public GetBuilder url(String url) {
      requestBuilder.url(url);
      return this;
    }

    public ResponseResult execute() {
      Callable<String> callable = () -> {
        OkHttpClient okHttpClient = OkHttpUtils.newOkHttpClient();
        Response response =
                okHttpClient.newCall(requestBuilder.build()).execute();
        return response.body().string();
      };
      Future<String> future = threadPool.submit(callable);
      try {
        return new ResponseResult(future.get());
      } catch ( InterruptedException | ExecutionException e ) {
        e.printStackTrace();
        return null;
      }
    }
  }

  public static class PostFileBuilder {
    private Request.Builder requestBuilder = new Request.Builder();
    private MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private RequestBody fileRequestBody;

    public PostFileBuilder url(String url) {
      requestBuilder.url(url);
      return this;
    }

    public PostFileBuilder create(String mediaType, File file) {
      fileRequestBody = RequestBody.create(MediaType.parse(mediaType), file);
      return this;
    }

    public PostFileBuilder addParam(String name, String value) {
      multipartBodyBuilder.addFormDataPart(name, value);
      return this;
    }

    public PostFileBuilder addFormDataPart(String key, String fileName) {
      multipartBodyBuilder.addFormDataPart(key, fileName, fileRequestBody);
      return this;
    }

    public ResponseResult execute() {
      Callable<String> callable = () -> {
        OkHttpClient okHttpClient = OkHttpUtils.newOkHttpClient();
        Request request = requestBuilder.post(multipartBodyBuilder.build()).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
      };
      Future<String> future = threadPool.submit(callable);
      try {
        String result = future.get();
        return new ResponseResult(result);
      } catch ( InterruptedException | ExecutionException e ) {
        e.printStackTrace();
      }
      return null;
    }
  }

  public static class GetFileBuilder{
    private Request.Builder requestBuilder  = new Request.Builder();
    private FormBody.Builder requestBodyBuilder = new FormBody.Builder();
    private String savePath = "//download//";
    private String fileName;
    public GetFileBuilder savePath(String savePath){
      this.savePath  = savePath;
      return this;
    }
    public GetFileBuilder fileName(String fileName){
      this.fileName = fileName;
      return this;
    }
    public GetFileBuilder url(String url){
      requestBuilder.url(url);
      return this;
    }
    public GetFileBuilder addParam(String name, String value){
      requestBodyBuilder.add(name, value);
      return this;
    }
    public File executeForFile(){
      Callable<File> callable = () -> {
        OkHttpClient okHttpClient = OkHttpUtils.newOkHttpClient();
        Request request = requestBuilder.post(requestBodyBuilder.build()).build();
        Response response = okHttpClient.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        long total = response.body().contentLength();
        savePath = OkHttpUtils.isDirectoryExistent(savePath);
        File file = new File(savePath, fileName);
        if (file.exists()){
          file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[2048];
        long sum = 0;
        int len;
        while ( ( len = inputStream.read(buf) ) != -1 ) {
          fos.write(buf, 0, len);
          sum += len;
        }
        return file;
      };
      try {
        return threadPool.submit(callable).get();
      } catch ( InterruptedException | ExecutionException  e ) {
        e.printStackTrace();
      }
      return null;
    }
  }

  public static class ResponseResult {
    private String result;
    ResponseResult(String result) {
      this.result = result;
    }
    public JSONObject jsonObject() {
      try {
        return new JSONObject(result);
      } catch ( JSONException e ) {
        Log.e(TAG, "jsonObject()\n: ", e);
        return null;
      }
    }

    public JSONArray jsonArray() {
      try {
        return new JSONArray(result);
      } catch ( JSONException e ) {
        Log.e(TAG, "jsonArray()\n: ", e);
        return null;
      }
    }

    public Boolean bool() {
      return Boolean.valueOf(result);
    }

    public String string() {
      return result;
    }
  }
  private static String isDirectoryExistent(String saveDir) throws IOException {
    // 下载位置
    File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
//    File downloadFile = new File(saveDir);
    if ( !downloadFile.mkdirs() ) {
      downloadFile.createNewFile();
    }
    String savePath = downloadFile.getAbsolutePath();
    return savePath;
  }
}
