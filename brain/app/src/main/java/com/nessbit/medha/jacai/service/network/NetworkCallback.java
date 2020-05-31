package com.nessbit.medha.jacai.service.network;

import java.util.ArrayList;

public interface NetworkCallback {
    void onTaskFinish(Object data,String TAG);
    void onTaskFinish(ArrayList<?> data);
}
