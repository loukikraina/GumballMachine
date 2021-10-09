package concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import concurrency.Summation;

public class ConcurrencyDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello Concurrency");

        int[] randomArray = createRandomArray(100000);
        int batchSize = (int) randomArray.length / 100;
        ExecutorService executorService = Executors.newFixedThreadPool(batchSize);
        SmartSummation[] summationTasks = new SmartSummation[batchSize];

        List<Future<Long>> sumsList;

        for (int j = 0; j < batchSize; j++) {
            int[] randomKaChotaBhai = new int[100];
            for (int i = 0; i < 100; i++)
                randomKaChotaBhai[i] = randomArray[(100 * j) + i];
            SmartSummation summation = new SmartSummation(randomKaChotaBhai);
            summationTasks[j] = summation;
        }
        sumsList = executorService.invokeAll(Arrays.asList(summationTasks));

        long sum = 0;
        for (int i = 0; i < sumsList.size(); i++) {
            System.out.println(sumsList.get(i).isDone());
            long batchSum = sumsList.get(i).get();
            sum += batchSum;
            System.out.println(batchSum);
        }
        System.out.println("Overall sum = " + sum);

        executorService.shutdown();
    }

    static int[] createRandomArray(int size) {
        int[] randomArray = new int[size];
        for (int i = 0; i < size; i++) {
            randomArray[i] = ThreadLocalRandom.current().nextInt(0, 100);
        }
        return randomArray;
    }

    static long computeSum(int[] input) {
        long sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return sum;
    }
}
