package com.ssau;

public class Calculate {
    private int countOfWorkers;
    private int countOfPrimes;

    Calculate(int workers, int primes){
        this.countOfPrimes = primes;
        this.countOfWorkers = workers;
    }

    public int getCountOfPrimes() {
        return countOfPrimes;
    }

    public int getCountOfWorkers() {
        return countOfWorkers;
    }
}
