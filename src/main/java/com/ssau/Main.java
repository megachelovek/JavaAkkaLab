package com.ssau;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int CountOfPrimes;
    public static int CountOfWorkers;

    public static void main(String[] args) throws IOException {
        System.out.println("Write primes & workers:");
        BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
        String primes = reader.readLine();
        String workers = reader.readLine();
        System.out.println("OK");
        CountOfPrimes = Integer.parseInt(primes);
        CountOfWorkers = Integer.parseInt(workers);
        new Main().run();

    }

    private void run() {
        ActorSystem system = ActorSystem.create("CalcSystem");
        ActorRef master = system.actorOf(Master.createMaster(), "master");
        master.tell(new Calculate(CountOfWorkers,CountOfPrimes), ActorRef.noSender());
    }
}
