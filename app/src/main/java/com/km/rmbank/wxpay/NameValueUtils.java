package com.km.rmbank.wxpay;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamangkeji on 17/4/9.
 */

public class NameValueUtils {
    private Map<String, String> param;

    public NameValueUtils() {
        param = new HashMap<>();
    }

    public void put(String key, String value) {
        param.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (param == null) {
            return builder.toString();
        }
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        return builder.toString();
    }
}
