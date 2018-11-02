package com.example.Tiga.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class CallActivity extends BaseActivity {

    private EditText et_Phone;
    private EditText et_Sms;
    private ImageView iv_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        et_Phone = findViewById(R.id.et_phone);
        et_Sms = findViewById(R.id.et_sms);
        iv_photo = findViewById(R.id.iv_photo);

        findViewById(R.id.btn_call).setOnClickListener(l);
        findViewById(R.id.btn_sms).setOnClickListener(l);
        findViewById(R.id.btn_getPhone).setOnClickListener(l);
        findViewById(R.id.btn_getSms).setOnClickListener(l);
        findViewById(R.id.btn_getMap).setOnClickListener(l);
        findViewById(R.id.btn_photo).setOnClickListener(l);
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
