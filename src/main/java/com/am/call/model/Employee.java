package com.am.call.model;

/**
 * Employee that can take a call
 */
public interface Employee {

    void takeCall(Call call);

    long getId();
}
