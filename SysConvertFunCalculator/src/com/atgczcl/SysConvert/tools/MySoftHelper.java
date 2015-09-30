package com.atgczcl.SysConvert.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class MySoftHelper {
	
	public static void ShowSoftInfoDialog(final Context context, String title, String msg){
		AlertDialog alertDialog=new AlertDialog.Builder(context)
        .setIcon(android.R.attr.alertDialogIcon)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked OK so do some stuff */
            }
        })
        .setNeutralButton("Email", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	Uri uri=Uri.parse("mailto:atgczcl@163.com");
                Intent emailIntent=new Intent(Intent.ACTION_SENDTO,uri);
                context.startActivity(emailIntent);
                /* User clicked Something so do some stuff */
            }
        })
//        .setNegativeButton("", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//
//                /* User clicked Cancel so do some stuff */
//            }
//        })
        .create();
		alertDialog.show();
		
	}
	

}
