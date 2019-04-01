package com.ssau;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import scala.collection.mutable.ArraySeq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Master extends UntypedActor{

    private ActorRef workerRouter;
    private final Time time = new Time();
    public static ArrayList<Integer> list = new ArrayList<Integer>();
    public static List<Integer> checkedList = new ArrayList<Integer>();
    public static int currentIndex=4;
    private static boolean endFlag = true;
    private RoundRobinPool roundRobinPool;

    public Master() {
        roundRobinPool = new RoundRobinPool(Main.CountOfWorkers);
        workerRouter = this.getContext().actorOf(Worker.createWorker().withRouter(roundRobinPool), "workerRouter");
        list.add(2);
        list.add(3);
    }

    @Override
    public void onReceive(Object message) throws InterruptedException {
        if (message instanceof Calculate) {
            time.start();
            processMessages();
        } else if (message instanceof Result) {
            if(((Result) message).getIsPrime() == true){
                list.add(((Result) message).getPrime());
            }
            if (currentIndex-1 == Main.CountOfPrimes && endFlag){
                endFlag = false;
                removeSortDuplicates(list);
                end();
            }
        } else {
            unhandled(message);
        }
    }

    private void processMessages() throws InterruptedException {
        while( !(currentIndex == Main.CountOfPrimes+1)) {
            int[] currentArray = getListPrimes();
            if (currentIndex == Main.CountOfPrimes){
                currentArray[2]=-1;
            }
            if (checkNumber(currentIndex)) {
                workerRouter.tell(new Work(currentIndex, currentArray), getSelf());
                Thread.sleep(50);
                currentIndex++;
                System.out.println("work send");
            }
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

    public synchronized int[] getListPrimes() {
        return list.stream().mapToInt(j->j).toArray();
    }

    private boolean checkNumber(int number){
        boolean result = true;
        for (int i=getInteger(number); i>3;i-- ){
            if (!checkedList.contains(i)){
                result = false;
                break;
            }
        }
        return result;
    }

    private int getInteger(int number){
        double result = (number / 2);
        return ((int) result);
    }

    public static List<Integer> removeSortDuplicates(ArrayList<Integer> list)
    {
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (Integer element : list) {
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        Collections.sort(newList);
        return newList;
    }
}