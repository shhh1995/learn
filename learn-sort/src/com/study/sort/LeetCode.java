package com.study.sort;

import java.util.*;

public class LeetCode {

    public static void main(String[] args) {
//        int calculate = calculate("1+((2+3)*4)-5");
//        System.out.println(calculate);

//        String babad = longestPalindrome("bb");
//        System.out.println(babad);

        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            String s=sc.nextLine();
            for(int i=s.length()-1;i>=0;i--){
                System.out.print(s.charAt(i));
            }
        }

        String s = longestCommonPrefix(new String[]{"flower", "flow", "flight"});
        System.out.println(s);
    }

    public static String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) return "";

        String str = strs[0];
        for(int i = 1; i < strs.length; i++){
            while (strs[i].indexOf(str) < 0){
                str = str.substring(0, str.length() - 1);
                if (str.isEmpty()) return "";
            }
        }
        return str;
    }

    public static String longestPalindrome(String s) {
        if(s == null || s.equals("") || s.length() == 1) return s;
        // a b c b a c d
        String ret = "";
        for (int i = 0; i < s.length(); i++)
            ret += "#" + s.charAt(i);
        ret+= "#";
        System.out.println(ret);
        StringBuilder result = getStringBuilder(ret);
        System.out.println(result);
        return result.toString().replace("#", "");
    }

    private static StringBuilder getStringBuilder(String s) {
        int pre = 0;
        int index = 0;  //记录中间点位置
        char[] chars = s.toCharArray();
        for(int center = 0; center < chars.length; center++){
            int length = getLengthByCenter(chars, center);
            if(length > pre){
                pre = length;
                index = center;
            }
        }
        System.out.println(pre);
        System.out.println(index);

        StringBuilder result = new StringBuilder();
        for(int i = index - (pre/2); i < index + (pre/2)+1; i++){
            result.append(chars[i]);
        }
        return result;
    }

    /**
     * 获取以center为中心的最长回文长度（仅限奇数）
    * @param chars
     * @param center
     * @return
     */
    private static int getLengthByCenter(char[] chars, int center) {
        //定义边界条件
        if(center == 0 || center == chars.length -1){   //如果是第0个或者最后一个，直接返回本身
            return 1;
        }

        int left = center -1;
        int right = center + 1;
        int count = 1;
        //如果没超过边界且中心点两边相等则继续
        while (left >= 0 && right <= chars.length -1 && chars[left] == chars[right]){
            left--;
            right++;
            count+=2;
        }
        return count;
    }

    /**
     * 利用栈实现一个计算器
     * @param s
     * @return
     */
    public static int calculate(String s){
        Queue<Character> queue = new LinkedList<>();
        for(char c:s.toCharArray()){
            if(c != ' ') queue.offer(c);
        }
        queue.offer('+');
        return calculate(queue);
    }

    private static int calculate(Queue<Character> queue) {
        char sign = '+';
        int num = 0;

        Stack<Integer> stack = new Stack<>();
        while(!queue.isEmpty()){
            char c = queue.poll();

            // 如果当前字符是数字，那么就更新 num 变量，如果遇到了加号，就把当前的 num 加入到 sum 里，num 清零
            if(Character.isDigit(c)){
                num = 10 * num + c - '0';   //这里-'0'是因为c自动运算取的是ASCII码值，如果不减需要来回转换
            }else if(c == '('){
                num = calculate(queue);
            }else{
                if(sign == '+'){
                    stack.push(num);
                }else if (sign == '-'){
                    stack.push(-num);
                }else if(sign == '*'){
                    stack.push(stack.pop() * num);
                }else if(sign == '/'){
                    stack.push(stack.pop() / num);
                }
                num = 0;
                sign = c;
                if(c == ')'){
                    break;
                }
            }
        }

        int sum = 0;
        while (!stack.isEmpty()){
            sum += stack.pop();
        }
        return sum;
    }

    /**
     * leetcode:269
     * @desc :1.构建有向图 2.对有向图进行拓扑排序
     * @param words
     * @return
     */
    public static String alienOrder(String[] words){
        if(words == null || words.length == 0) return null;

        if(words.length == 1) return words[0];

        Map<Character, List<Character>> adjList = new HashMap<>();
        for(int i = 0; i < words.length - 1; i++){
            String w1 = words[i], w2 = words[i + 1];
            int n1 = w1.length(), n2 = w2.length();

            boolean found = false;

            for (int j = 0; j < Math.max(w1.length(), w2.length()); j++){
                Character c1 = j < n1 ? w1.charAt(j):null;
                Character c2 = j < n2 ? w2.charAt(j):null;

                if(c1 != null && !adjList.containsKey(c1)){
                    adjList.put(c1, new ArrayList<>());
                }

                if(c2 != null && !adjList.containsKey(c2)){
                    adjList.put(c2, new ArrayList<>());
                }

                if(c1 != null && c2 != null && c1 != c2 && !found){
                    adjList.get(c1).add(c2);
                    found = true;
                }
            }
        }

        Set<Character> visited = new HashSet<>();
        Set<Character> loop = new HashSet<>();
        Stack<Character> stack = new Stack<>();

        for(Character key:adjList.keySet()){
            if(!visited.contains(key)){
                if(!topulogicSort(adjList, key, visited, loop, stack)){
                    return "";
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * 使用深度优先的方式进行拓扑排序
     * @param adjList
     * @param u
     * @param visited
     * @param loop
     * @param stack
     * @return
     */
    private static boolean topulogicSort(Map<Character, List<Character>> adjList, Character u,
                                         Set<Character> visited, Set<Character> loop,
                                         Stack<Character> stack) {
        visited.add(u);
        loop.add(u);
        if(adjList.containsKey(u)){
            for(int i = 0; i < adjList.get(u).size(); i++){
                char v = adjList.get(u).get(i);

                if(loop.contains(v)) return false;

                if(!visited.contains(v)){
                    if(!topulogicSort(adjList, v, visited, loop, stack)){
                     return false;
                    }
                }
            }
        }

        loop.remove(u);
        stack.push(u);
        return true;
    }

    public static int findMedianSortedArrays(int nums1[], int nums2[]){
        int m = nums1.length;
        int n = nums2.length;

        int k = (m + n) / 2;
        if((m + n) % 2 == 1){       //如果是奇数
            return findKth(nums1, 0, m - 1, nums2, 0, n - 1, k + 1);
        }else{
            return (findKth(nums1, 0, m - 1, nums2, 0, n - 1, k) +
                    findKth(nums1, 0, m - 1, nums2, 0, n - 1, k)) /2;
        }
    }

    private static int findKthLargest(int[] nums, int k){
        return  quickSelect(nums, 0, nums.length - 1, k);
    }

    private static int quickSelect(int[] nums, int low, int high, int k) {
        int pivot = low;

        for(int j = low; j < high; j++){
            if(nums[j] <= nums[high]){
                swap(nums, pivot++, j);
            }
        }

        swap(nums, pivot, high);

        int count = high - pivot + 1;
        if(count == k) return nums[pivot];

        if(count > k) return  quickSelect(nums, pivot + 1, high, k);

        return quickSelect(nums, low, pivot - 1, k - count);
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

    private static int findKth(int[] nums1, int l1, int h1, int[] nums2, int l2, int h2, int k) {

        int m = h1 - l1 + 1;
        int n = h2 - l2 + 1;

        if(m > n){
            return findKth(nums2, l2, h2, nums1, l1, h1, k);
        }

        if(m == 0){
            return nums2[l2 + k -1];
        }

        if(k == 1){
            return Math.min(nums1[l1], nums2[l2]);
        }

        int na = Math.min(k/2, m);
        int nb = k - na;
        int va = nums1[l1 + na - 1];
        int vb = nums2[l2 + nb - 1];

        if(va == vb){
            return va;
        }else if(va < vb){
            return findKth(nums1, l1 + na, h1, nums2, l2, l2 + nb - 1, k - na);
        }else{
            return findKth(nums1, l1, l1 + na - 1, nums2, l2, l2 + nb, k - nb);
        }
    }
}
