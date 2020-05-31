package com.nessbit.vojonbari.service.network;

import java.util.ArrayList;

public interface NetworkCallback {
    void onTaskFinish(boolean success);
    void onTaskFinish(ArrayList<?> data);
}
