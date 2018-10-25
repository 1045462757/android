package com.example.Tiga.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CallActivity extends BaseActivity {

    private Button mBtnCall;
    private Button mBtnSms;
    private EditText et_Phone;
    private EditText et_Sms;
    private Button mBtnGetCall;
    private Button mBtnGetSms;
    private Button mBtnGetMap;
    private Button mBtnPhoto;
    private ImageView iv_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        mBtnCall = findViewById(R.id.btn_call);
        mBtnSms = findViewById(R.id.btn_sms);
        et_Phone = findViewById(R.id.et_phone);
        et_Sms = findViewById(R.id.et_sms);
        mBtnGetCall = findViewById(R.id.btn_getPhone);
        mBtnGetSms = findViewById(R.id.btn_getSms);
        mBtnGetMap = findViewById(R.id.btn_getMap);
        mBtnPhoto = findViewById(R.id.btn_photo);
        iv_photo = findViewById(R.id.iv_photo);
        mBtnCall.setOnClickListener(l);
        mBtnSms.setOnClickListener(l);
        mBtnGetCall.setOnClickListener(l);
        mBtnGetSms.setOnClickListener(l);
        mBtnGetMap.setOnClickListener(l);
        mBtnPhoto.setOnClickListener(l);
    }

    final View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.btn_call:
                    intent.setAction(intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + et_Phone.getText().toString()));
                    startActivity(intent);
                    break;
                case R.id.btn_sms:
                    intent.setAction(intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + et_Phone.getText().toString()));
                    intent.putExtra("sms_body", et_Sms.getText().toString());
                    startActivity(intent);
                    break;
                case R.id.btn_getPhone:
                    intent.setClass(CallActivity.this, Call2Activity.class);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.btn_getSms:
                    intent.setClass(CallActivity.this, Call2Activity.class);
                    startActivityForResult(intent, 2);
                    break;
                case R.id.btn_getMap:
                    Uri uri = Uri.parse("geo:39.899533,116.036476");
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.btn_photo:
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 3);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        Bundle bundle = data.getExtras();
        String Phone = bundle.getString("Phone");
        String Sms = bundle.getString("Sms");
        if (requestCode == 1) {
            et_Phone.setText(Phone);
        } else if (requestCode == 2) {
            et_Sms.setText(Sms);
        } else if (requestCode == 3) {
            Bitmap b = (Bitmap) bundle.get("data");
            iv_photo.setImageBitmap(b);

        }
    }
}
