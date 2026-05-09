package com.atguigu.cocomall.search.thread;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/7 8:46
 */

public class ThreadTest {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("main...start");
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果:" + i);
//        }, executor);

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果:" + i);
//            return i;
//
//        }, executor).whenComplete((res, ex) -> {
//            System.out.println(res +";" + ex);
//        }).exceptionally(throwable -> {
//            return 9;
//        });

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果:" + i);
//            return i;
//        }, executor).handle((res, ex) -> {
//            if (res != null) {
//                return 3;
//            }
//            if (ex != null) {
//                return 6;
//            }
//            return 0;
//        });

//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 3;
//            System.out.println("运行结果:" + i);
//            return i;
//        }, executor).thenApplyAsync(res -> {
//            System.out.println("任务2启动了。。。" + res);
//            return "hello " + res;
//        }, executor);

//        CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务1线程" + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("任务1结束:");
//            return i;
//        }, executor);
//
//        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务2线程" + Thread.currentThread().getId());
//            try {
//                Thread.sleep(5000);
//                System.out.println("任务2结束:");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "Hello";
//        }, executor);

//        future01.runAfterBothAsync(future02, () -> {
//            System.out.println("任务3开始...");
//        }, executor);

//        future01.thenAcceptBothAsync(future02, (f1, f2) -> {
//            System.out.println("任务3开始...之前的结果" + f1 + "->" + f2);
//        }, executor);

//        CompletableFuture<String> future = future01.thenCombineAsync(future02, (f1, f2) -> {
//            return f1 + "->" + f2 + "->f3haha";
//        }, executor);

        /**
         *  两个任务有一个结束，就执行任务3
         */
//        future01.runAfterEitherAsync(future02, () -> {
//            System.out.println("任务3开始...之前的结果");
//        }, executor);

//        future01.acceptEitherAsync(future02, (res) -> {
//            System.out.println("任务3开始...之前的结果" + res);
//        }, executor);

//        CompletableFuture<String> future = future01.applyToEitherAsync(future01, (res) -> {
//            System.out.println("任务3开始...之前的结果");
//            return res + "任务3结果";
//        }, executor);

        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品 图片");
            return "Img";
        }, executor);

        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("查询商品 属性");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "黑色256G";
        }, executor);

        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品 描述");
            return "Apple";
        }, executor);

//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
//        allOf.get();
        anyOf.get();

//        Integer integer = future.get();
        System.out.println("main...end...");
        System.out.println("main...end..." + anyOf.get());
    }

    public void thread(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("main...start");
//        Thread01 thread01 = new Thread01();
//        thread01.start();

//        Runnable01 runnable01 = new Runnable01();
//        new Thread(runnable01).start();

//        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//        new Thread(futureTask).start();
//        Integer i = futureTask.get();

//        service.execute(new Runnable01());

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
//                20,
//                30,
//                null,
//                null,
//                null,
//                null);

        System.out.println("main...end");

    }


    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println("当前线程" + Thread.currentThread().getId());
            int i = 10/2;
            System.out.println("运行结果:" + i);
        }
    }

    public static class Runnable01 implements Runnable{
        @Override
        public void run() {
            System.out.println("当前线程" + Thread.currentThread().getId());
            int i = 10/2;
            System.out.println("运行结果:" + i);
        }
    }

    public static class Callable01 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception{
            System.out.println("当前线程" + Thread.currentThread().getId());
            int i = 20/2;
            System.out.println("运行结果:" + i);
            return i;
        }
    }
}
