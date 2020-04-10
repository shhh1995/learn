package com.study.sort;

import java.util.Arrays;

public class LeetCode {

    public static void main(String[] args) {

        int[] nums = {3,1};
        search(nums, 1);
    }

    public static int search(int[] nums, int target) {

        int value = binarySearch2(nums, target, 0, nums.length - 1);
        System.out.println(value);
        return value;
    }

    public static int binarySearch2(int[] nums, int target, int low, int high){
        while (low <= high){
            int middle = low + (high - low)/2;

            if(nums[middle] == target) return middle;

            if(nums[low] <= nums[middle]) {    //判断左侧是否是有序数列
                if(nums[low] <= target & nums[middle] > target) {   //如果目标值在左侧
                    high = middle - 1;
                }else{
                    low = middle + 1;
                }
            }else{  //右侧一定排好序
                if(nums[middle] < target && nums[high] >= target){   //判断值是否在右侧
                    low = middle + 1;
                }else{
                    high = middle -1;
                }
            }
        }

        return -1;
    }

    public static int findValue(int[] nums, int target, int low, int high){
        if(low > high) return -1;

        int middle = low + (high - low)/2;

        if(nums[middle] == target){
            return middle;
        }

        if(nums[low] <= nums[middle]){    //判断左侧是否是有序数列

            if(nums[low] <= target & nums[middle] > target){   //如果目标值在左侧
                return findValue(nums, target, low, middle -1);
            }
            return findValue(nums, target, middle + 1, high); //是右侧

        }else{  //右侧一定排好序
            if(nums[middle] < target && nums[high] >= target){   //判断值是否在右侧
                return findValue(nums, target, middle + 1, high);
            }
            return findValue(nums, target, low, middle -1); //是在左侧
        }
    }
}
