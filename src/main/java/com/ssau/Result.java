package com.ssau;

import java.math.BigInteger;

public class Result {

    private Boolean isPrime;
    private int prime;

    public Result(Boolean isPrime, int prime) {
        this.isPrime = isPrime;
        this.prime = prime;
    }

    public Boolean getIsPrime() {
        return this.isPrime;
    }

    public int getPrime() {return this.prime;}
}