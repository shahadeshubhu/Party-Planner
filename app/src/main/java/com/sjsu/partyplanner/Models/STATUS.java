package com.sjsu.partyplanner.Models;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/*
@IntDef({STATUS.NOT_STARTED, STATUS.PENDING, STATUS.COMPLETE, STATUS.UNPAID, STATUS.PAID})
@Retention(RetentionPolicy.SOURCE)
@interface STATUS {
    int NOT_STARTED = 0;
    int PENDING = 1;
    int  COMPLETE = 2;
    int UNPAID = 3;
    int PAID = 4;
}
*/


public enum STATUS {
    NOT_STARTED,
    PENDING,
    COMPLETE,
    UNPAID,
    PAID
}
