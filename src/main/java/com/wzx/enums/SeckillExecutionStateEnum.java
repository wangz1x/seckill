package com.wzx.enums;

/**
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/11 下午10:10
 */
public enum SeckillExecutionStateEnum {
    SUCCESS(1, "秒杀成功"),
    CLOSED(0, "秒杀关闭"),
    REPEATED(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");

    private int state;

    private String stateInfo;

    SeckillExecutionStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SeckillExecutionStateEnum stateOf(int state) {
        for (SeckillExecutionStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
