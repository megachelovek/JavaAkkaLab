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

public class Master extends UntypedActor {

    private long messages = 10;
    private ActorRef workerRouter;
    private final Time time = new Time();
    private ArrayList list = new ArrayList();

    public Master() {
        workerRouter = this.getContext().actorOf(Worker.createWorker().withRouter(new RoundRobinPool(8)), "workerRouter");
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Calculate) {
            time.start();
            processMessages();
        } else if (message instanceof Result) {
            list.add(((Result) message).getIsPrime());
            if (list.size() == messages)
                end();
        } else {
            unhandled(message);
        }
    }

    private void processMessages() {
        for (int i = 3; i < messages+3; i++) {
            workerRouter.tell(new Work(i,new int[]{2,3}), getSelf());
        }
    }

    private void end() {
        time.end();
        System.out.println("Done: " + time.elapsedTimeMilliseconds());
        for(int i=3; i<list.size()+3;i++){
            System.out.println("Result "+i +" = "+ list.get(i).toString() );
        }
        getContext().system().terminate();
    }

    public static Props createMaster() {
        return Props.create(Master.class, new ArraySeq<Object>(0));
    }
}