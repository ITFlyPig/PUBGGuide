package pubg.igameguide.com.network;

public interface BaseNetCallBack<T> {
    /**
     * 成功
     * @param resp
     */
    void onSuccess(T resp);

    /**
     * 失败
     */
     void onFail();
}
