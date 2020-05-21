package com.study.sort;

import java.util.*;

public class NiuKeMain {

    public static void main(String[] args) {

        int[] arr = {1,2,3,4,5,6,7};
//        int i = minNumberInRotateArray(arr);
//        System.out.println(i);
//
//        for(int j = 3; j < 3; i++){
//            System.out.println("sssssssss");
//        }

//        reOrderArray(arr);

//        Node node = new Node(0);
//        Map<String, Node> map = new HashMap<>();
//        map.put("ss", node);
//        map.get("ss").val++;
//        System.out.println(map.get("ss").val);

        int[] nums = {2,3,4,2,6,2,5,1};
        ArrayList<Integer> integers = maxInWindows(nums, 3);
        System.out.println(Arrays.toString(integers.toArray()));
    }

    /**
     * 给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1），
     * 每段绳子的长度记为k[0],k[1],...,k[m]。请问k[0]xk[1]x...xk[m]可能的最大乘积是多少？
     * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
     * @param target  绳子长度
     * @return
     */
    public int cutRope(int target) {
        int max=1;
        if(target<=3 && target>0){
            return target-1;
        }
        while(target>4){
            target-=3;
            max*=3;
        }
        return max*target;
    }

    /**
     * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
     * 但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    int count = 0;
    public int movingCount(int threshold, int rows, int cols) {
        boolean[] pass = new boolean[rows*cols];    //标记是否走过
        movingHelper(threshold, 0, 0, rows, cols, pass);
        return count;
    }

    private void movingHelper(int threshold, int i, int j, int rows, int cols, boolean[] pass) {
        int index = i * cols + j;
        if(i < 0 || j < 0 || i >= rows || j >= cols || pass[index]){
            return;
        }

        if(sumHelper(i,j ) <= threshold){
            count++;
            pass[index]=true;
        }else{
            pass[index]=false;      //如果不满足，则回溯，取消本轮操作
            return;
        }

        movingHelper(threshold, i + 1, j, rows, cols, pass);
        movingHelper(threshold, i - 1, j, rows, cols, pass);
        movingHelper(threshold, i, j + 1, rows, cols, pass);
        movingHelper(threshold, i, j - 1, rows, cols, pass);
    }

    private int sumHelper(int i, int j){
        int sum = 0;
        do {
            sum += i%10;
        }while ((i = i/10) > 0);

        do {
            sum += j%10;
        }while ((j = j/10) > 0);
        return sum;
    }

    /**
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
     * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
     * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子
     * 例如
     * [a  b  c  e]
     * [s  f  c  s]
     * [a  d  e  e]
     * 矩阵中包含一条字符串"bcced"的路 径，但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了
     * 矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
     * @param matrix
     * @param rows
     * @param cols
     * @param str
     * @return
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str){
        if (matrix.length < str.length || rows > matrix.length || cols > matrix.length) {
            return false;
        }

        int k = 0;  //目标字符串的第k个
       //标志位，初始化为false
        boolean[] flag = new boolean[matrix.length];

        //间接的构建二维数组
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //找到一组直接返回true
                if (pathHelper(matrix, rows, cols, i, j, str, k, flag)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否包含路径
     * @param matrix 矩阵
     * @param rows  矩阵行数
     * @param cols  矩阵列数
     * @param i   当前行数
     * @param j   当前列数
     * @param str   目标字符串
     * @param k char坐标
     * @param flag  是否访问过
     * @return
     */
    private boolean pathHelper(char[] matrix, int rows, int cols, int i, int j, char[] str, int k, boolean[] flag) {
        //通过公式计算出二维数组在一维数组中的位置
        int index = i * cols + j;
        //1.递归结束条件
        if (i < 0 || j < 0 || i >= rows || j >= cols || matrix[index] != str[k] || flag[index]) {
            return false;
        }
        //如果刚好匹配完的话，直接返回true
        if (k == str.length - 1) {
            return true;
        }
        //开始回溯之前将本次回溯的字符标记位置为true，表示已经使用过了
        flag[index] = true;
        //开始回溯，上下左右分别回溯
        if (pathHelper(matrix, rows, cols, i - 1, j, str, k + 1, flag) ||
            pathHelper(matrix, rows, cols, i + 1, j, str, k + 1, flag) ||
            pathHelper(matrix, rows, cols, i, j - 1, str, k + 1, flag) ||
            pathHelper(matrix, rows, cols, i, j + 1, str, k + 1, flag)) {
            return true;
        }
        //四个方向都没找到的话，说明当前字符不行,我们往回走，恢复flag
        flag[index] = false;
        return false;
    }

    public static ArrayList<Integer> maxInWindows(int[] num, int size){
        int low = 0;
        int high = size - 1;

        ArrayList<Integer> result = new ArrayList<>();
        while (high < num.length){
            int maxValue = getMaxValue(num, low, high);
            result.add(maxValue);
            low++;
            high++;
        }
        return result;
    }
    private static int getMaxValue(int[] num, int low, int high){
        int max = Integer.MIN_VALUE;
        for(int i = low; i <= high; i++){
            if(num[i] > max){
                max = num[i];
            }
        }
        return max;
    }

    public int[] multiply(int[] A) {
        int length = A.length;
        int[] B = new int[length];
        if(length != 0){
            B[0] = 1;
            for(int i = 1; i < length; i++){
                B[i] = B[i - 1] * A[i - 1];
            }
            int temp = 1;
            for(int j = length - 2; j >= 0; j--){
                temp *= A[j + 1];
                B[j] *= temp;
            }
        }
        return B;
    }

    public TreeNode KthNode(TreeNode pRoot, int k) {
        if(pRoot == null || k == 0) return pRoot;
        Stack<TreeNode> stack = new Stack<>();
        int count = 0;
        while (pRoot != null || !stack.isEmpty()){
            while (pRoot != null){
                stack.push(pRoot);
                pRoot = pRoot.left;
            }
            pRoot = stack.pop();
            count++;
            if(count == k) return pRoot;
            pRoot = pRoot.right;
        }
        return null;
    }

    boolean isSymmetrical(TreeNode pRoot) {
        if(pRoot == null) return true;

        Stack<TreeNode> stack = new Stack<>();
        stack.add(pRoot);
        while (!stack.empty()){
            TreeNode node = stack.pop();
            if(!isEquals(node)){
                return false;
            }
            stack.push(node.left);
            stack.push(node.right);
        }
        return true;
    }

    private boolean isEquals(TreeNode pRoot) {
        if(pRoot == null) return true;
        if(pRoot.left == null && pRoot.right == null) return true;
        if(pRoot.left == null && pRoot.right != null) return false;
        if(pRoot.left != null && pRoot.right == null) return false;
        if(pRoot.left.val != pRoot.right.val) return false;
        return false;
    }

    /**
     * 二叉树的层次遍历
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        depth(pRoot, 1, list);
        return list;
    }

    private void depth(TreeNode root, int depth, ArrayList<ArrayList<Integer>> list) {
        if(root == null) return;
        if(depth > list.size())
            list.add(new ArrayList<Integer>());
        list.get(depth -1).add(root.val);

        depth(root.left, depth + 1, list);
        depth(root.right, depth + 1, list);
    }

    /**
     * 删掉重复的节点
     * @param pHead
     * @return
     */
    public ListNode deleteDuplication(ListNode pHead) {
        ListNode head = new ListNode(0);//增加头节点，避免第一、二个就相同
        head.next = pHead;

        ListNode pre = head;    //当前节点的前一个节点
        ListNode last = head.next; //当前节点的后继节点

        while (last != null){
            if(last.next!=null && last.val == last.next.val){
                // 找到最后的一个相同节点
                while (last.next!=null && last.val == last.next.val){
                    last = last.next;
                }
                pre.next = last.next;
                last = last.next;
            }else{
                pre = pre.next;
                last = last.next;
            }
        }

        return head.next;
    }

    private void deleteDuplicationNode(ListNode node, int val) {
        if(node == null) return;
        node = node.next;   //将指针指向下一个节点
        if(node.val == val){    //如果下一个节点和当前节点值相同，就找到最近的一个不同值节点，将当前的next置为该节点
            ListNode next =  node.next;
            while (next != null && next.val == val){
                next =  node.next;
            }
            node.next = next;
        }

        deleteDuplicationNode(node.next, node.val);
    }

    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    private Map<Character, Integer> map = new HashMap<>();
    public void Insert(char ch) {
        if(map.containsKey(ch)){
            map.put(ch,map.get(ch));
        }else{
            map.put(ch, 1);
        }
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        for (Character ch:map.keySet()){
            if(map.get(ch) == 1)
                return ch;
        }
        return '#';
    }

    public int LastRemaining_Solution(int n, int m) {
        if(n < 1 || m < 1){
            return -1;
        }

        int[] array = new int[n];
        int count = n, i = -1, step = 0; //count=人数
        while (count > 0){
            i++;
            if(i > n){
                i = 0;  //当循环一圈后，重置为0
            }
            if(array[i] == -1) continue;
            step++;
            if(step == m){
                count--;
                array[i] = -1;
                step = 0;
            }
        }
        return i;
    }

    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> result = new ArrayList<>();
        if(array == null || array.length == 0){
            return result;
        }

        int i = 0, j = array.length - 1;
        while (i < j){
            if(array[i] + array[j] == sum){
                result.add(array[i]);
                result.add(array[j]);
                return result;
            }else if(array[i] + array[j] > sum){
                j--;
            }else{
                i++;
            }
        }
        return result;
    }

    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {

        //存放结果
        ArrayList<ArrayList<Integer> > result = new ArrayList<>();
        //两个起点，相当于动态窗口的两边，根据其窗口内的值的和来确定窗口的位置和大小
        int plow = 1,phigh = 2;
        while (phigh > plow){
            int cur = (plow + phigh) * (phigh - plow + 1)/2;
            if(cur == sum){ //如果相等，就将窗口的值存入结果中
                ArrayList<Integer> list = new ArrayList<>();
                for(int i = plow; i <= phigh; i++){
                    list.add(i);
                }
                result.add(list);
                plow++;
            }else if(cur < sum){    //如果小，则窗口右边界右移
                phigh++;
            }else{  //cur大于sum，则右边界右移
                plow++;
            }
        }
        return result;
    }

    public int GetUglyNumber_Solution(int index) {

        if(index<=0)
            return 0;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        int i2 = 0, i3 = 0, i5= 0;
        while (list.size() < index){

            int n2 = list.get(i2)*2;
            int n3 = list.get(i3)*3;
            int n5 = list.get(i5)*5;

            int min = Math.min(n2,Math.min(n3,n5));
            list.add(min);
            if(min==n2)
                i2++;
            if(min==n3)
                i3++;
            if(min==n5)
                i5++;
        }
        return list.get(list.size()-1);
    }

    public String PrintMinNumber(int [] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        List<Integer> list = new ArrayList<>();
        for(int i:numbers){
            list.add(i);
        }
        list.sort((o1, o2) -> {
            String s1 = String.valueOf(o1);
            String s2 = String.valueOf(o2);
            String str1 = s1+s2;
            String str2 = s2+s1;
            return str1.compareTo(str2);
        });

        StringBuilder sb = new StringBuilder();
        for(Integer i:list){
            sb.append(i);
        }
        return sb.toString();
    }

    public int MoreThanHalfNum_Solution(int [] array) {
        int n = 0;
        int result = array[0];
        for(int i = 0; i < array.length; i++){
            if(n == 0){ //
                result = array[i];
                n++;
            }else{
                if(result == array[i]){
                    n ++;
                }else{
                    n--;
                }
            }
        }
        n = 0;
        for(int i:array){
            if(i == result){
                n++;
            }
        }
        return n>array.length/2? result:0;
    }

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str != null && str.length() > 0) {
            PermutationHelper(str.toCharArray(), 0, res);
        }
        return res;
    }

    public void PermutationHelper(char[] cs, int i, List<String> list) {
        if (i == cs.length - 1) {
            String val = String.valueOf(cs);
            if (!list.contains(val))
                list.add(val);
        } else {
            for (int j = i; j < cs.length; j++) {
                swap(cs, i, j);
                PermutationHelper(cs, i+1, list);
                swap(cs, i, j);
            }
        }
    }

    public void swap(char[] chs, int i, int j){
        char temp = chs[i];
        chs[i] = chs[j];
        chs[j] = temp;
    }

    public TreeNode Convert(TreeNode root) {

        Stack<TreeNode> stack = new Stack<>();//创建一个栈，用于非递归遍历
        TreeNode p = root;
        TreeNode pre = null;
        boolean first = true;
        while(p != null || !stack.isEmpty()){
            while (p != null){  //先找到最左下节点
                stack.push(p);
                p = p.left;
            }

            p = stack.pop();    //最左下节点

            if(first){
                root = p;
                pre = root;
                first = false;
            }else{
                pre.right = p;
                p.left = pre;
                pre = p;
            }
            p = p.right;
        }
        return root;
    }

    TreeNode pre;
    TreeNode head;
    public void middle(TreeNode node){
        if(node == null) return;

        middle(node.left);  //有左孩子

//        visit();            //访问
        if(pre == null){
            pre = node;
            head = node;
        }else{
            pre.right = node;   //当前节点作为前一个的后继节点
            node.left = pre;    //前一个节点作为当前节点的前驱
            pre = node;         //移向下一个节点
        }

        middle(node.right); //查找右孩子

    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {

        ArrayList<Integer> result = new ArrayList<>();
        if(k > input.length || k == 0 || input.length == 0){
            return result;
        }


        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        for(int i:input){
            if(heap.size() != k){
                heap.add(i);
            }else if(heap.peek() > i){
                heap.poll();
                heap.add(i);
            }
        }

        for (Integer integer : heap) {
            result.add(integer);
        }

        return result;
    }

    private ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {

        if(root == null) {
            return result;
        }

        getPath(root, target, 0, new ArrayList<Integer>());
        return result;
    }

    private void getPath(TreeNode node, int target, int count, ArrayList<Integer> list) {

        if(node == null) return;

        list.add(node.val);

        if((node.val + count) == target && node.left == null && node.right == null){
            result.add(new ArrayList<>(list));
        }else{
            count += node.val;
            getPath(node.left, target, count, list);
            getPath(node.right, target, count, list);
        }
    }

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        while (root != null){
            list.add(root.val);
            if(root.left != null) queue.add(root.left);
            if(root.right != null) queue.add(root.right);

            root = queue.poll();
        }

        return list;
    }

    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence == null || sequence.length == 0) return false;

        if(sequence.length == 1){
            return true;
        }

        return judge(sequence, 0, sequence.length - 1);
    }

    public boolean judge(int[] arr, int start, int end){

        if(start > end) return true;    //数组遍历完毕

        int i = start;

        while (arr[i] < arr[end]){  //寻找第一个比中间值大的数
            i++;
        }

        for(int index = i; i < end; i++){
            if(arr[index] < arr[end]){
                return false;            }
        }

        return judge(arr, start,i - 1) && judge(arr, i, end - 1);
    }

    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if(pushA == null || pushA.length == 0) return false;
        Stack<Integer> stack = new Stack<>();
        //用于标识弹出序列的位置
        int popIndex = 0;
        for(int i = 0; i < pushA.length; i++){
            stack.push(pushA[i]);
            while (!stack.isEmpty() && stack.peek() == popA[popIndex]){
                stack.pop();
                popIndex++;
            }
        }
        return stack.isEmpty();
    }

    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
        boolean result = false;
        //当Tree1和Tree2都不为零的时候，才进行比较。否则直接返回false
        if (root2 != null && root1 != null) {
            //如果找到了对应Tree2的根节点的点
            if(root1.val == root2.val){
                //以这个根节点为为起点判断是否包含Tree2
                result = doesTree1HaveTree2(root1,root2);
            }
            //如果找不到，那么就再去root的左儿子当作起点，去判断时候包含Tree2
            if (!result) {
                result = HasSubtree(root1.left,root2);
            }

            //如果还找不到，那么就再去root的右儿子当作起点，去判断时候包含Tree2
            if (!result) {
                result = HasSubtree(root1.right,root2);
            }
        }
        //返回结果
        return result;
    }

    public static boolean doesTree1HaveTree2(TreeNode node1, TreeNode node2) {
        //如果Tree2已经遍历完了都能对应的上，返回true
        if (node2 == null) {
            return true;
        }
        //如果Tree2还没有遍历完，Tree1却遍历完了。返回false
        if (node1 == null) {
            return false;
        }
        //如果其中有一个点没有对应上，返回false
        if (node1.val != node2.val) {
            return false;
        }

        //如果根节点对应的上，那么就分别去子节点里面匹配
        return doesTree1HaveTree2(node1.left,node2.left) && doesTree1HaveTree2(node1.right,node2.right);
    }

    public static void reOrderArray(int [] array) {

        int[] arrayTemp = new int[array.length * 2];

        int i = 0;//奇数开始坐标
        int j = array.length; //偶数开始坐标

        for(int count:array){
            if(count % 2 == 0){//是偶数
                arrayTemp[j] = count;
                j++;
            }else{//是奇数
                arrayTemp[i] = count;
                i++;
            }
        }
        System.out.println(Arrays.toString(arrayTemp));
        for(int count = 0; count <= i; count++){
            array[count] = arrayTemp[count];
        }
        System.out.println(Arrays.toString(array));
        for(int count = array.length; count < j; count++){
            array[i++] = arrayTemp[count];
        }
        System.out.println(Arrays.toString(array));
    }

    public int JumpFloorII(int target) {
        return (int) Math.pow(2, target-1);
    }

    public static int minNumberInRotateArray(int [] array) {
        int low = 0 ; int high = array.length - 1;
        while(low < high){
            int mid = low + (high - low) / 2;
            if(array[mid] > array[high]){
                low = mid + 1;
            }else if(array[mid] == array[high]){
                high = high - 1;
            }else{
                high = mid;
            }
        }
        return array[low];
    }

    public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
        TreeNode root=reConstructBinaryTree(pre,0,pre.length-1,in,0,in.length-1);
        return root;
    }
    //前序遍历{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
    private TreeNode reConstructBinaryTree(int [] pre,int startPre,int endPre,int [] in,int startIn,int endIn) {

        if(startPre>endPre||startIn>endIn)
            return null;
        TreeNode root=new TreeNode(pre[startPre]);

        for(int i=startIn;i<=endIn;i++)
            if(in[i]==pre[startPre]){
                root.left=reConstructBinaryTree(pre,startPre+1,startPre+i-startIn,in,startIn,i-1);
                root.right=reConstructBinaryTree(pre,i-startIn+startPre+1,endPre,in,i+1,endIn);
                break;
            }

        return root;
    }

    public String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ","%20");
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
