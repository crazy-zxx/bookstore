package com.me.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Integer bookId;
    private String author;
    private String title;
    private float price;
    private Date publishingDate;
    private int salesAmount;
    private int storeNumber;
    private String remark;

}
