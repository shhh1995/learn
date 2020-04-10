package com.study.sort;

import java.util.Arrays;

/**
 * 常见的排序算法
 */
public class SortMain {

    public static void main(String[] args) {

//        String str = "abcdefg";
//        System.out.println(reverse(str));

        int[] nums = {1, 5, 2, 6, 8, 9, 2};
//        bubbleSort(nums);
//        insertSort(nums);
//        mergeSort(nums, 0 , nums.length -1);
        quickSort(nums, 0 , nums.length -1);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 逆转字符串
     * @param originStr
     * @return
     */
    public static String reverse(String originStr){
        if(originStr == null || originStr.length() <=1) return originStr;
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

    /**
     * 冒泡排序
     * @param nums
     */
    public static void bubbleSort(int[] nums){
        boolean hasChange = true;
        for(int i = 0; i < nums.length - 1 && hasChange; i++){
            hasChange = false;
            for(int j = 0; j < nums.length - 1 - i; j++){
                if(nums[j] > nums[j + 1]){
                    swap(nums, j, j+1);
                    hasChange = true;
                }
            }
        }
    }

    /**
    public static void topoSort(int[] nums, int low, int high){

    }

    /**
     * 快速排序
     * @param nums
     * @param low
     * @param high
     */
    public static void quickSort(int[] nums, int low, int high){
        if(low >= high) return;

        int p = partition(nums, low, high);
        quickSort(nums, low, p - 1);
        quickSort(nums, p + 1, high);
    }

    /**
     * 获取随机的基准点，左边都小于基准值，右边都大于基准值
     * @param nums
     * @param low
     * @param high
     * @return
     */
    private static int partition(int[] nums, int low, int high) {
        swap(nums, randRange(low, high), high);     //随机获取一个值放在最右边，规定最右侧为基准值

        int i,j;
        for(i = low, j = low; j < high; j++){       //循环遍历整个数组，将比基准值小的元素都放置在i的位置，i后移
            if(nums[j] <= nums[high]){
                swap(nums, i++, j);                 //将j位置的值放置在i位置，i后移
            }
        }
        swap(nums, i, j);
        return i;
    }

    /**
     * 返回low与high之间的一个随机数
     * @param low
     * @param high
     * @return
     */
    private static int randRange(int low, int high) {
       return (int) (Math.random() * (high - low) + low);
    }

    /**
     * 归并排序
     * @param nums
     */
    public static void mergeSort(int[] nums, int low, int high){
        if(low >= high) return;

        int mid = low + (high - low)/2;
        mergeSort(nums, low, mid);
        mergeSort(nums, mid + 1, high);

        merge(nums, low, mid, high);
    }

    /**
     * 合并数组
     * @desc L:1,2,7   R:5,8,9   T: 1,2,5,7,8,9
     * @param nums
     * @param low
     * @param mid
     * @param high
     */
    private static void merge(int[] nums, int low, int mid, int high) {
        int[] copy = nums.clone();  //复制一份原本的数组，因为会对数组进行修改，不复制会影响比较
        int k = low, i = low, j = mid + 1;  //k:从第k个元素开始修改，i:左侧起始位置，j:起始位置

        while (k <= high){
            if(i > mid){                    //左侧树形都处理完毕
                nums[k++] = copy[j++];
            }else if(j > high){             //右侧属性都处理完毕
                nums[k++] = copy[i++];
            }else if(copy[j] < copy[i]){    //右侧树形小于左侧
                nums[k++] = copy[j++];
            }else{                          //左侧树形小于右侧
                nums[k++] = copy[i++];
            }
        }
    }

    /**
     * 插入排序
     * @desc （将数组分为两部分，未排序好的数组往排序好的数组中间直接插入值）
     * @param nums
     */
    public static void insertSort(int[] nums){
        for(int i = 1, j, current; i < nums.length; i++){
            current = nums[i];
            for(j = i - 1; j >= 0 && nums[j] > current; j-- ){
                //第一次时j+1就是i，此后大于则一直右移，直至找到插入位置
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = current;
        }
    }

    /**
     * 交换位置
     * @param nums
     * @param i
     * @param j
     */
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
