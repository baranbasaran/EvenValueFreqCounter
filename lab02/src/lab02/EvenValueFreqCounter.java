package lab02;

public class EvenValueFreqCounter {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Generate random array of integers
        int[] arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 100000);
        }

        // Count the frequency of even values in the array
        int evenFreq = 0;

        // Get the number of available processors
        int numProcessors = Runtime.getRuntime().availableProcessors();

        // Compute the size of each block for the threads to handle
        int blockSize = arr.length / numProcessors;
        int remainder = arr.length % numProcessors;

        // Create an array of threads and an array to hold their results
        Thread[] threads = new Thread[numProcessors];
        int[] results = new int[numProcessors];

        // Define a function to count the even values in a block of the array
        Runnable countEvenValues = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                int threadIndex = Thread.currentThread().getName().charAt(Thread.currentThread().getName().length()-1) - '0';
                int start = blockSize * threadIndex;
                
                int end = start + blockSize;
              
               
                
                if (threadIndex == numProcessors - 1) {
                    end += remainder;
                }
                System.out.println("start : "+start);
                
                System.out.println("end : "+end);
                for (int i = start; i < end; i++) {
                    if (arr[i] % 2 == 0) {
                        count++;
                    }
                }
                results[threadIndex] = count;
            }
        };

        // Start each thread to count the even values in its assigned block of the array
        for (int i = 0; i < numProcessors; i++) {
            threads[i] = new Thread(countEvenValues);
            threads[i].setName("Thread " + i);
            threads[i].start();
        }

        // Wait for all threads to complete and aggregate their results
        for (int i = 0; i < numProcessors; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            evenFreq += results[i];
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Print the total frequency of even values in the array
        System.out.println("Frequency of even values: " + evenFreq);
        System.out.println("Execution time: " + duration + " milliseconds");
    }
}
