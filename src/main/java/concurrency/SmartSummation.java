package concurrency;

import java.util.concurrent.Callable;

public class SmartSummation implements Callable<Long> {
    private int[] batchArray;

    public SmartSummation(int[] input) {
        batchArray = input;
    }

    @Override
    public Long call() throws Exception {
        Long sum = 0l;
        for (int i : batchArray) {
            sum += i;
        }
        return sum;
    }
}
