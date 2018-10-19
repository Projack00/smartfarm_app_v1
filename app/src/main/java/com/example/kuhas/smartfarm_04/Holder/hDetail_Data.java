package com.example.kuhas.smartfarm_04.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kuhas.smartfarm_04.R;

public class hDetail_Data {
    public TextView tMode;
    public Button bDelete, bView, bUpdate;

    public hDetail_Data(View view) {
        tMode = view.findViewById(R.id.tModel);
        bUpdate = view.findViewById(R.id.bUpdate);
        bDelete = view.findViewById(R.id.bDelete);
        bView = view.findViewById(R.id.bView);
    }

    public hDetail_Data() {

    }
}
