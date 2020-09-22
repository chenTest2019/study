package com.chen.interview.boss.one.algorithm;


import java.util.PriorityQueue;

/**
 * 从一千万个数中取出前10最小值
 */
public class Test {
    public static void main(String[] args) {
        int nums[] ={1,2,3,-10,-20,34,12,90,-90,-34,100,20,57,34,56,23,65,89,23};
        Test test = new Test();
        test.sortMin(nums,3);
        System.out.println("----------");
        test.sortMin(nums,8);
        System.out.println("----------");
        test.sortMax(nums,3);
    }

    /**
     * 前k个最小值
     * @param nums
     * @param k
     */
    void sortMin(int[] nums,int k){
        int len=nums.length;
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();
        PriorityQueue<Integer> bigPriorityQueue=new PriorityQueue<>((o1,o2)->o2-o1);
        for (int i = 0; i < k; i++) {
            priorityQueue.add(nums[i]);
            bigPriorityQueue.add(nums[i]);
        }
        for (int j = k; j <len; j++) {
            if(nums[j]<priorityQueue.peek()){
                priorityQueue.add(nums[j]);
                priorityQueue.remove(bigPriorityQueue.poll());
                bigPriorityQueue.add(nums[j]);
            }
        }
        while(!priorityQueue.isEmpty()){
            System.out.println(priorityQueue.poll());
        }
    }

    /**
     * 前k个最大值
     * @param nums
     * @param k
     */
    void sortMax(int[] nums,int k){
        int len=nums.length;
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();
        PriorityQueue<Integer> bigPriorityQueue=new PriorityQueue<>((o1,o2)->o2-o1);
        for (int i = 0; i < k; i++) {
            priorityQueue.add(nums[i]);
            bigPriorityQueue.add(nums[i]);
        }
        for (int j = k; j <len; j++) {
            if(nums[j]>priorityQueue.peek()){
                bigPriorityQueue.add(nums[j]);
                bigPriorityQueue.remove(priorityQueue.poll());
                priorityQueue.add(nums[j]);

            }
        }
        while(!bigPriorityQueue.isEmpty()){
            System.out.println(bigPriorityQueue.poll());
        }
    }
}
