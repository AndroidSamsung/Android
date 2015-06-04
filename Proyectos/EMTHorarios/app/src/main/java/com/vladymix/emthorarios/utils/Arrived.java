package com.vladymix.emthorarios.utils;

/**
 * Created by Fabricio on 05/06/2015.
 */
public class Arrived {
    String IdStop;
    String TimeLeftBus;

    public Arrived(String idStop, String timeLeftBus) {
        IdStop = idStop;
        TimeLeftBus = timeLeftBus;
    }

    public String getIdStop() {
        return IdStop;
    }

    public void setIdStop(String idStop) {
        IdStop = idStop;
    }

    public String getTimeLeftBus() {
        return TimeLeftBus;
    }

    public void setTimeLeftBus(String timeLeftBus) {
        TimeLeftBus = timeLeftBus;
    }
}
