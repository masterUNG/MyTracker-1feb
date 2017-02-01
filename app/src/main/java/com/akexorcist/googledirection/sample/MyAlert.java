package com.akexorcist.googledirection.sample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by unchalee.ru on 31/1/2560.
 */

public class MyAlert {
    // Explict
    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    } // Constructor

    public  void  myDialog(int intIcon,String strTitle,String StrMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(intIcon);
        builder.setTitle(strTitle);
        builder.setMessage(StrMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}//Main Class
