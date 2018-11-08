package com.nacity.college.base;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.nacity.college.R;

/**
 * Created by xzz on 2017/6/17.
 **/

public  class CommonAlertDialog {

    private static Button cancel;
    private static NiftyDialogBuilder dialogBuilder;

    public static Button show(Context context, String tipContent, String confirmContent, String cancelContent){

        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.setContentView(R.layout.dialog_common);
        Button confirm= (Button) dialogBuilder.findViewById(R.id.confirm);
        cancel = (Button) dialogBuilder.findViewById(R.id.cancel);
        TextView tipText=(TextView) dialogBuilder.findViewById(R.id.tip_content);
        confirm.setText(confirmContent);
        cancel.setText(cancelContent);
        tipText.setText(tipContent);
        dialogBuilder.show();
        cancel.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.findViewById(R.id.parent).setOnClickListener(v -> dialogBuilder.dismiss());

        return confirm;
    }
public static Button getCancelClick(){
    return cancel;
}

public static void dismiss(){
    dialogBuilder.dismiss();
}
}
