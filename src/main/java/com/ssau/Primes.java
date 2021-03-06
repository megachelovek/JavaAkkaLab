package com.ssau;

public class Primes {

    public Boolean checkIsPrime(int[] primes, int prime) {
        Boolean isPrime = true;
        int i=0;
        addPrimeToCheckedList(prime);
        while (primes[i] * primes[i]<= prime ){
            if (primes[i]!=0){
                if (prime % primes[i] == 0) {
                    isPrime = false;
                    break;
                }
                i++;
            }
            if (i == primes.length-1){break;}
        }
       if (isPrime){addPrimeToList(prime);}
        return isPrime;
    }

    public synchronized void addPrimeToList(int prime){
        Master.list.add(prime);
    }

    public synchronized void addPrimeToCheckedList(int digit){
        Master.checkedList.add(digit);
    }
}
