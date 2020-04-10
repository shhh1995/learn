package com.learn.thread.base;

import java.util.Arrays;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {

        int money = 200;
        int people = 30;

        LuckyMoneyTool tool = new LuckyMoneyTool();
        List<Integer> moneys = tool.splitRedPacket(money, people);

    }

    /**
     * 用户群
     */
    private List<UserImpl> users = Arrays.asList(new UserImpl("1", "王一"),
            new UserImpl("2", "牛二"),
            new UserImpl("3", "张三"),
            new UserImpl("4", "李四"),
            new UserImpl("5", "吴五"),
            new UserImpl("6", "赵六"),
            new UserImpl("7", "枸七"));
}
