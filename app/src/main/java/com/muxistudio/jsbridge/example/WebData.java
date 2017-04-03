package com.muxistudio.jsbridge.example;

import java.util.List;

/**
 * Created by ybao (ybaovv@gmail.com)
 * Date: 17/3/6
 */

public class WebData {

    public List<TransData> lists;

    public static class TransData{
        public String time;
        public String transMoney;
    }

}
