package pubg.igameguide.com.network;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Map;

/**
 * 之所以在原来的网络框架上再做一层封装，是考虑到以后可能换网络库的需求
 * 目的：和具体的网络库解耦，我们调用时，只需要传入CallBack就可以得到相应的数据即可
 */

public class Net {




    /**
     * GET方法
     * @param url
     * @param params
     * @param netCallBack
     * @param <T>
     */
    public static<T> void get(String url, Map params, final BaseNetCallBack<T> netCallBack) {
        if (url == null || params == null || netCallBack == null) {
            return;
        }

        AbsCallback absCallback = null;
        if (netCallBack instanceof StringNetCallBack) {
            absCallback = new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response != null) {
                        ((StringNetCallBack) netCallBack).onSuccess(response.body());
                    } else {
                        ((StringNetCallBack) netCallBack).onFail();
                    }

                }
            };
        } else if (netCallBack instanceof JsonNetCallBack){

            //TODO JSONCallBack以后做

        }

        if (absCallback == null) {
            return;
        }

        OkGo
                .get(url)
                .params(params)
                .execute(absCallback);

    }
}
