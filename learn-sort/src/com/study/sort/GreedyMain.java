package com.study.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 贪婪算法
 *
 * 贪心法的设计思路：
 * 1.创建数学模型来描述问题。
 * 2.把求解的问题分成若干个子问题。
 * 3.对每一子问题求解，得到子问题的局部最优解。
 * 4.把子问题的解局部最优解合成原来解问题的一个解。
 * 使用前提：局部最优策略能导致产生全局最优
 */
public class GreedyMain {

    public static void main(String[] args) {

    }

    public static int minMeetingRooms(Interval[] intervals){
        if(intervals == null || intervals.length == 0) return 0;

        Arrays.sort(intervals, new Comparator<Interval>(){

            @Override
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });

        PriorityQueue<Interval> heap = new PriorityQueue<>(intervals.length, new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                return a.end - b.end;
            }
        });

        heap.offer(intervals[0]);

        for(int i = 1; i < intervals.length; i++){
            Interval interval = heap.poll();
            if(intervals[i].start >= interval.end){
                interval.end = intervals[i].end;
            }else{
                heap.offer(interval);
            }
        }

        return heap.size();
    }

    class Interval{

        public int start;
        public int end;
    }
}
