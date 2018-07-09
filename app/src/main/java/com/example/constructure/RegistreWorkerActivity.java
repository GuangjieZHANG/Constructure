package com.example.constructure;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

public class RegistreWorkerActivity extends Activity {

    private EditText name_in;
    private EditText id_in;
    private EditText type_in;
    private EditText psw_in;
    private EditText home_in;
    private Button registre;
    private ImageView image;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Bitmap mBitmap;

    private String name,id,type,psw,home;
    private Bitmap pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre_worker);

        name_in = (EditText)findViewById(R.id.registre_worker_name);
        id_in = (EditText)findViewById(R.id.registre_worker_id);
        type_in = (EditText)findViewById(R.id.registre_worker_type);
        psw_in = (EditText)findViewById(R.id.registre_worker_psw);
        image = (ImageView)findViewById(R.id.registre_worker_image);
        home_in = (EditText)findViewById(R.id.registre_worker_hometown);

        registre = (Button)findViewById(R.id.registre_worker);

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_in.getText().toString();
                id = id_in.getText().toString();
                type = type_in.getText().toString();
                psw = psw_in.getText().toString();
                pic = image.getDrawingCache();
                home = home_in.getText().toString();
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
                String baseUrl = "http://34.226.141.56:8000/user/worker/";
                FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();

                RequestBody requestBody = RequestBody.create(JSON,toJson(name,id,type,psw,home,"testtesttest"));
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(baseUrl).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        //输出出错信息
                        Log.i("onFailure:",e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String res=response.body().string();
                        Log.i("zhuce", res);
                        try{
                            Gson gson = new Gson();
                            Type type = new TypeToken<Map<String, Object>>(){}.getType();
                            Map<String, Object> sList = gson.fromJson(res, type);
                            final String result= sList.get("msg").toString();
                            Log.i("res",result);
                            if(result.equals("success")){
                                final String worker_id = sList.get("worker_id").toString();
                                Intent intent = new Intent(RegistreWorkerActivity.this,LoginWorkerActivity.class);
                                startActivity(intent);
                                RegistreWorkerActivity.this.finish();
                            }else{
                                toast("注册发生错误");
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            toast("服务器返回错误");
                        }
                    }
                });
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });

    }

    /**
     * 显示修改图片的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistreWorkerActivity.this);
        builder.setTitle("添加图片");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }
    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            image.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码
        }
    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegistreWorkerActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String hearImage(Bitmap bitmap){
        String toreturn;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        toreturn = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
        return toreturn;
    }

    public String toJson(String name,String idcard,String speciality,String password,String hometown,String picture){
        String jsonResult = "";
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name",name);
            jsonObj.put("idcard",idcard);
            jsonObj.put("specialty",speciality);
            jsonObj.put("password",password);
            jsonObj.put("hometown",hometown);
            jsonObj.put("picture",picture);
            jsonResult = jsonObj.toString();
        }catch (JSONException e){
            e.printStackTrace();
        }
        Log.i("Json:",jsonResult);
        return jsonResult;
    }
}
