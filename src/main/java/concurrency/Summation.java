package concurrency;

public class Summation extends Thread {
    private int[] randomArray;

    public Summation(int[] inputArray){
        this.randomArray = inputArray;
    }

    @Override
    public void run() {
        long sum = 0;
        for (int i = 0; i < randomArray.length; i++)
            sum += randomArray[i];
        System.out.println("The sum is " + sum);
    }
}
