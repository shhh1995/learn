package com.study.sort;

import java.util.Arrays;

/**
 * 二分搜索算法（包含递归与非递归）
 */
public class BinarySearch {

    public static void main(String[] args) {

//        int[] nums = {1, 3, 5, 6, 8, 9, 11};
//        int i = binarySearch2(nums, 11, 0, nums.length - 1);

//        int[] nums = {5, 7, 7, 8, 8, 10};
//        int i = searchLowerBound(nums, 8, 0, nums.length - 1);
//        int j = searchUpperBound(nums, 8, 0, nums.length - 1);

//        int[] nums = {-2, 0, 1, 4, 7, 9, 10};
//        int i = firstGreaterThan(nums, 6, 0, nums.length - 1);

        int[] nums = {4, 5, 6, 7, 0, 1, 2}; //经过旋转了的数组
        int i = binarySearchForRevolve(nums, 7, 0, nums.length - 1);
        System.out.println(i);
    }

    /**
     * 二分搜索递归写法
     * @param nums
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int binarySearch(int[] nums, int target, int low, int high){
        if(low > high) return  -1;

        int middle = low + (high - low)/2;

        if(nums[middle] == target) return middle;

        if(target < nums[middle]){
            return binarySearch(nums, target, low, middle - 1);
        }else{
            return binarySearch(nums, target, middle + 1, high);
        }
    }

    /**
     * 二分搜索非递归写法
     * @param nums
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int binarySearch2(int[] nums, int target, int low, int high){
        while (low <= high){
            int middle = low + (high - low)/2;

            if(nums[middle] == target) return middle;

            if(target < nums[middle]){
                high = middle - 1;
            }else{
                low = middle + 1;
            }
        }

        return -1;
    }

    /**
     * 二分搜索递归写法——变形（寻找下边界）
     * @param nums
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int searchLowerBound(int[] nums, int target, int low, int high){
        if(low > high) return  -1;

        int middle = low + (high - low)/2;

        if(nums[middle] == target && (middle == 0 || nums[middle - 1] < target)) return middle;

        if(target <= nums[middle]){
            return searchLowerBound(nums, target, low, middle - 1);
        }else{
            return searchLowerBound(nums, target, middle + 1, high);
        }
    }

    /**
     * 二分搜索递归写法——变形（寻找上边界）
     * @param nums
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int searchUpperBound(int[] nums, int target, int low, int high){
        if(low > high) return  -1;

        int middle = low + (high - low)/2;

        if(nums[middle] == target && (middle == nums.length - 1 || nums[middle + 1] > target)) return middle;

        if(target < nums[middle]){
            return searchUpperBound(nums, target, low, middle - 1);
        }else{
            return searchUpperBound(nums, target, middle + 1, high);
        }
    }

    /**
     * 二分搜索递归写法——变形（寻找模糊边界）
     * @param nums
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int firstGreaterThan(int[] nums, int target, int low, int high){
        if(low > high) return  -1;

        int middle = low + (high - low)/2;

        if(nums[middle] > target && (middle == 0 || nums[middle - 1] <= target)) return middle;

        if(target < nums[middle]){
            return firstGreaterThan(nums, target, low, middle - 1);
        }else{
            return firstGreaterThan(nums, target, middle + 1, high);
        }
    }

    /**
     * 二分搜索递归写法——变形（经过旋转了的数组）
     * @param nums
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int binarySearchForRevolve(int[] nums, int target, int low, int high){
        if(low > high) return  -1;

        int middle = low + (high - low)/2;

        if(nums[middle] == target) return middle;

        if(nums[low] <= nums[middle]){      //判断左半边是否排好序
            if(nums[low] <= target && target < nums[middle]){   //判断目标值是否在左半边
                return binarySearchForRevolve(nums, target, low, middle - 1);
            }
            return binarySearchForRevolve(nums, target, middle + 1, high);
        }else{
            if(nums[middle] < target && target <= nums[high]){  //判断目标值是否在右半边
                return binarySearchForRevolve(nums, target, middle + 1, high);
            }
            return binarySearchForRevolve(nums, target, low, middle - 1);
        }
    }

    /**
     * 不定长边界（先利用二分搜索思想找边界，在使用普通二分搜索）
     * @param logs
     * @param high
     * @return
     */
    public static int getUpperBound(String[] logs, int high){
        if(logs[high] == null){
            return high;
        }
        return getUpperBound(logs, high*2);
    }

    /**
     * 利用二分搜索查找日志
     * @param logs
     * @param low
     * @param high
     * @return
     */
    public static int binarySearch(String[] logs, int low, int high){
        if(low > high) return  -1;

        int middle = low + (high - low)/2;

        if(logs[middle] == null && logs[middle - 1] != null) return middle;

        if(logs[middle] == null){
            return binarySearch(logs, low, middle - 1);
        }else{
            return binarySearch(logs ,middle + 1, high);
        }
    }
}
