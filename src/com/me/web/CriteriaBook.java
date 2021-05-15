package com.me.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaBook {

    private float minPrice = 0;
    private float maxPrice = Integer.MAX_VALUE;
    private int pageNo;

}
