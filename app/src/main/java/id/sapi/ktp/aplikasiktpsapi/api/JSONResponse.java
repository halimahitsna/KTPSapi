package id.sapi.ktp.aplikasiktpsapi.api;

import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;

/**
 * Created by hali on 25/08/2017.
 */

public class JSONResponse {
    private Sapi[] sapi;
    private Jenis[] jenis;

    public Sapi[] getSapi(){
        return sapi;
    }
    public Jenis[] getJenis(){
        return jenis;
    }

}
