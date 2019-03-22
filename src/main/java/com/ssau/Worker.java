package com.ssau;

import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.collection.mutable.ArraySeq;

import java.math.BigInteger;

public class Worker extends UntypedActor {
    final static int LIMIT = 1000;
    int[] primes = new int[LIMIT];

    int numPrimes = 1,i, candidate, isprime;

    @Override
    public void onReceive(Object message) {
        primes[0] = 2;
        primes[1] = 3;
        if (message instanceof Work) {
            int prime;
            Boolean isPrime = new Primes().checkIsPrime(primes, prime);
            getSender().tell( isPrime , getSelf());
        } else
            unhandled(message);
    }

    public static Props createWorker() {
        return Props.create(Worker.class, new ArraySeq<Object>(0));
    }
}