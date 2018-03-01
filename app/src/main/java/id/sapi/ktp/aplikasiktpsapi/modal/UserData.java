package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by hestimr on 29/08/2017.
 */

public class UserData {

    private String status;
    private String msg;

    public UserData(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}

