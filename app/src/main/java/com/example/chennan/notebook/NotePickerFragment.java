package com.example.chennan.notebook;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by chennan on 2018/6/10.
 */

public class NotePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle("确认框").setMessage("请确认是否删除该笔记？")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
//                        System.out.println("123");
                    }
                })
                .setNegativeButton("取消", null).show();

    }
}
