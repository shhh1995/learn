package com.study.sort;

import java.util.*;

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
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String val = scanner.nextLine();
        char[] chas = str.toCharArray();
        int result = 0;
        for(char ch:chas){
            if(ch == val.toCharArray()[0]) result++;
        }
        System.out.print(result);
    }

    /**
     * 最小删除区间，leetcode：435
     * 方式：贪婪法（以结束时间排序）
     * @param intervals
     * @return
     */
    public static int eraseOverlapIntervals(int[][] intervals){
        if(intervals.length == 0) return 0;

        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int end = intervals[0][1], count = 1;

        for(int i = 1; i < intervals.length; i++){
            if(intervals[i][0] >= end){
                end = intervals[i][1];
                count++;
            }
        }

        return intervals.length - count;
    }

    /**
     * 最小删除区间，leetcode：435
     * 方式：贪婪法（以开始时间排序）
     * @param intervals
     * @return
     */
    public static int eraseOverlapIntervals1(int[][] intervals){
        if(intervals.length == 0) return 0;

        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int end = intervals[0][1], count = 0;

        for(int i = 1; i < intervals.length; i++){
            if(intervals[i][0] < end){
                end = Math.min(end, intervals[i][1]);
                count++;
            }else{
                end = intervals[i][1];
            }
        }

        return count;
    }

    /**
     * 最小删除区间，leetcode：435
     * 方式：暴力法
     * @param intervals
     * @return
     */
    public static int eraseOverlapIntervals2(int[][] intervals){
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        return eraseOverlapIntervals2(-1, 0, intervals);
    }

    private static int eraseOverlapIntervals2(int prev, int curr, int[][] intervals) {
        if(curr == intervals.length) return 0;

        int taken = Integer.MAX_VALUE, nottaken;

        if(prev == -1 || intervals[prev][1] <= intervals[curr][0]){
            taken = eraseOverlapIntervals2(curr, curr + 1, intervals);
        }

        nottaken = eraseOverlapIntervals2(prev, curr + 1, intervals) + 1;

        return Math.min(taken, nottaken);
    }

    /**
     * 合并区间，leetcode:56
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals){
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int[] previous = null;
        List<int[]> result = new ArrayList<>();

        for(int[] current : intervals){
            if(previous == null || current[0] > previous[1]){
                result.add(previous = current);
            }else{
                previous[1]  = Math.max(previous[1], current[1]);
            }
        }

        return result.toArray(new int[result.size()][]);
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
