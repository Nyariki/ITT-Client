package com.itt.client.view.main.clock;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

public class MyVectorClock extends VectorAnalogClock {

    private void init() {
        //use this for the default Analog Clock (recommended)
        initializeSimple();

        //or use this if you want to use your own vector assets (not recommended)
        //initializeCustom(faceResourceId, hourResourceId, minuteResourceId, secondResourceId);
    }

    //mandatory constructor
    public MyVectorClock(Context ctx) {
        super(ctx);
        init();
    }

    // the other constructors are in case you want to add the view in XML

    public MyVectorClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyVectorClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyVectorClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
}
