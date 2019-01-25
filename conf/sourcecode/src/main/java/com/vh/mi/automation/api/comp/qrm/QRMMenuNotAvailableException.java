package com.vh.mi.automation.api.comp.qrm;

/**
 * Created by i81131 on 4/13/2015.
 */
public class QRMMenuNotAvailableException extends QRMException {
    public QRMMenuNotAvailableException(String menu) {
        super("The menu " + menu + "is not available");

    }
}
