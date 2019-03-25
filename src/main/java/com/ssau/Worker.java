package com.ssau;

import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.collection.mutable.ArraySeq;

import java.math.BigInteger;

public class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        Work mes = (Work)message;
        int[] primes = mes.getPrimes();
        System.out.println("Worker on "+ "| number=" +mes.getPrime());
        if (message instanceof Work) {
            Work work = (Work) message;
            int prime = work.getPrime();
            Boolean isPrime = new Primes().checkIsPrime(primes, prime);
            getSender().tell( new Result(isPrime,prime) , getSelf());
        } else
            unhandled(message);
    }

    public static Props createWorker() {
        return Props.create(Worker.class, new ArraySeq<Object>(0));
    }
}