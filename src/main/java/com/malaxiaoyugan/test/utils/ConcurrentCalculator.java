package com.malaxiaoyugan.test.utils;

/**
 * description: ConcurrentCalculator
 * date: 2020/9/27 15:53
 * author: juquansheng
 * version: 1.0 <br>
 */

import com.malaxiaoyugan.test.callable.SumCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并发计算数组的和，“同步”求和
 * 引用 https://blog.csdn.net/fansunion/article/details/50433904
 */
public class ConcurrentCalculator {
    private ExecutorService exec;
    //这个地方，纯粹是“一厢情愿”，“并行执行”不受咱们控制，取决于操作系统的“态度”
    private int cpuCoreNumber;
    private List<Future<Long>> tasks = new ArrayList<Future<Long>>();
    private List<Future<Integer>> tasksNew = new ArrayList<Future<Integer>>();

    public ConcurrentCalculator() {
        cpuCoreNumber = Runtime.getRuntime().availableProcessors();
        exec = Executors.newFixedThreadPool(cpuCoreNumber);
    }

    class SumCalculator implements Callable<Long> {
        private int[] numbers;
        private int start;
        private int end;


        public SumCalculator(final int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }


        public Long call() throws Exception {
            Long sum = 0L;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }
    }



    public Long sum(final int[] numbers) {
        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor
        for (int i = 0; i < cpuCoreNumber; i++) {
            int increment = numbers.length / cpuCoreNumber + 1;
            int start = increment * i;
            int end = increment * i + increment;
            if (end > numbers.length)
                end = numbers.length;
            SumCalculator subCalc = new SumCalculator(numbers, start, end);
            FutureTask<Long> task = new FutureTask<Long>(subCalc);
            tasks.add(task);
            if (!exec.isShutdown()) {
                exec.submit(task);
            }
        }
        return getResult();
    }

    public Integer sumNew(final int[] numbers) {

        SumCallable subCalc = new SumCallable(numbers);
        FutureTask<Integer> task = new FutureTask<Integer>(subCalc);
        tasksNew.add(task);
        if (!exec.isShutdown()) {
            exec.submit(task);
        }
        return getResultNew();
    }


    /**
     * 迭代每个只任务，获得部分和，相加返回
     */
    public Long getResult() {
        Long result = 0l;
        for (Future<Long> task : tasks) {
            try {
                // 如果计算未完成则阻塞
                Long subSum = task.get();
                result += subSum;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 迭代每个只任务，获得部分和，相加返回
     */
    public Integer getResultNew() {
        Integer result = 0;
        for (Future<Integer> task : tasksNew) {
            try {
                // 如果计算未完成则阻塞
                Integer subSum = task.get();
                result += subSum;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public void close() {
        exec.shutdown();
    }

}
