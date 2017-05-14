package com.test.oystercard.service;

import com.test.oystercard.bean.UserDetails;

/**
 * Created by Ali on 5/14/2017.
 */
public interface OystercardService {

    double calcualateBalance(UserDetails userDetails);
}
