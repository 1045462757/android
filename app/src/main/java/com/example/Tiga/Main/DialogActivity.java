package com.example.Tiga.Main;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DialogActivity extends BaseActivity {

    private Button mBtnDialog1;
    private Button mBtnDialog2;
    private Button mBtnDialog3;
    private Button mBtnDialog4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mBtnDialog1 = findViewById(R.id.btn_dialog1);
        mBtnDialog2 = findViewById(R.id.btn_dialog2);
        mBtnDialog3 = findViewById(R.id.btn_dialog3);
        mBtnDialog4 = findViewById(R.id.btn_dialog4);

        //按钮对话框
        mBtnDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(DialogActivity.this).create();
                alertDialog.setTitle("乔布斯:");
                alertDialog.setMessage("活着不就是为了改变世界吗？");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "你单击了否按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "你单击了是按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

        //带列表的对话框
        mBtnDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"当你有使命，它会让你更专注","要么出众，要么出局","活着就是为了改变世界","求知若渴，虚心若愚"};
                AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                builder.setTitle("请选择你喜欢的名言:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "你选择了["+items[which]+"]", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });

        //单选列表对话框
        mBtnDialog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"男","女","保密"};
                AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                builder.setTitle("你的性别?");
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "你选择了["+items[which]+"]", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定",null);
                builder.create().show();
            }
        });

        //多选列表对话框
        mBtnDialog4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] checkedItems = new boolean[]{false,false,false};
                final String[] items = new String[]{"体育","美术","学习"};
                AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                builder.setTitle("请选择你的爱好:");
                builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = "";
                        for(int i=0;i<checkedItems.length;i++){
                            if(checkedItems[i]){
                                result += items[i]+",";
                            }
                        }
                        if(!"".equals(result)){
                            Toast.makeText(DialogActivity.this, "你选择了["+result+"]", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }
}
