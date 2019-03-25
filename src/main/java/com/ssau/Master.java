package com.ssau;

import akka.actor.*;
import akka.routing.*;
import scala.collection.mutable.ArraySeq;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.*;
import scala.collection.mutable.ArraySeq;

import java.util.ArrayList;
import java.util.List;

public class Master extends UntypedActor {

    private ActorRef workerRouter;
    private final Time time = new Time();
    private List<Integer> list = new ArrayList<Integer>();
    private int countOfWorkers;
    private int countOfPrimes;

    public Master() {
        workerRouter = this.getContext().actorOf(Worker.createWorker().withRouter(new RoundRobinPool(Main.CountOfWorkers)), "workerRouter");
        list.add(2);
        list.add(3);
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Calculate) {
            countOfWorkers = ((Calculate) message).getCountOfWorkers();
            countOfPrimes = ((Calculate) message).getCountOfPrimes();
            time.start();
            processMessages();
        } else if (message instanceof Result) {
            if(((Result) message).getIsPrime() == true){list.add(((Result) message).getPrime());}
            if (list.size() == countOfPrimes)
                end();
        } else {
            unhandled(message);
        }
    }

    private void processMessages() {
        for (int i = 0; i < countOfPrimes; i++) {
            int[] currentArray = list.stream().mapToInt(j->j).toArray();
            workerRouter.tell(new Work(i,currentArray), getSelf());
            System.out.println("work send" );
        }
    }

    private void end() {
        time.end();
        System.out.println("Done: " + time.elapsedTimeMilliseconds());
        for(int i=0; i<list.size();i++){
            System.out.println("Result "+i +" = "+ list.get(i).toString() );
        }
        getContext().system().terminate();
    }

    public static Props createMaster() {
        return Props.create(Master.class, new ArraySeq<Object>(0));
    }
}