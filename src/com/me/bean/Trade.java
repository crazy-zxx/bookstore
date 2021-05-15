package com.me.bean;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class Trade {

    private Integer tradeId;
    private Integer userId;
    //保存时分秒需要用java.sql.Timestamp
    private Timestamp tradeTime;

    // 1 -> *
    private Set<TradeItem> items = new LinkedHashSet<TradeItem>();

}
