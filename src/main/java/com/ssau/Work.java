package com.ssau;

import scala.concurrent.duration.Duration;

public class Work {
    private final int prime;
    private final int[] primes;

    public Work(int prime, int[] primes) {
        this.prime = prime;
        this.primes = primes;
    }

    public int getPrime() {
        return prime;
    }

    public int[] getPrimes() {
        return primes;
    }
}