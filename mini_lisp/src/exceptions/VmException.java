package exceptions;

import enums.VmStatus;

public class VmException extends Exception {

    private VmStatus status;

    public VmException(String msg, VmStatus status) {
        super(msg);
        this.status = status;
    }

    public VmStatus getStatus() {
        return this.status;
    }
}
