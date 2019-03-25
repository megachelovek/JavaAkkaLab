package com.ssau;

import java.math.BigInteger;

public class Primes {

    public Boolean checkIsPrime(int[] primes, int prime) {
        Boolean isPrime = true;
        int i=0;
        while (primes[i] * primes[i]<= prime || primes.length > i ){
            if (primes[i]!=0){
                if (prime % primes[i] == 0) {
                    isPrime = false;
                    break;
                }
                i++;
            }
        }
        return isPrime;
    }
}
