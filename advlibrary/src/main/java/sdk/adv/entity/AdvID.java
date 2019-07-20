package sdk.adv.entity;

import java.io.Serializable;

/**
 * @date {2019/6/25}
 */
public class AdvID implements Serializable{
    /**穿山甲ID*/
    private CsjAdvID csjAdvID; //穿山甲ID
    /**广点通ID*/
    private GdtAdvID gdtAdvID;//广点通ID
    /**精众*/
    private JzAdvID jzAdvID ;//精众AdvID
    /**链咖*/
    private LkAdvID lkAdvID;//链咖AdvID


    public CsjAdvID getCsjAdvID() {
        return csjAdvID;
    }

    public void setCsjAdvID(CsjAdvID csjAdvID) {
        this.csjAdvID = csjAdvID;
    }

    public GdtAdvID getGdtAdvID() {
        return gdtAdvID;
    }

    public void setGdtAdvID(GdtAdvID gdtAdvID) {
        this.gdtAdvID = gdtAdvID;
    }

    public JzAdvID getJzAdvID() {
        return jzAdvID;
    }

    public void setJzAdvID(JzAdvID jzAdvID) {
        this.jzAdvID = jzAdvID;
    }

    public LkAdvID getLkAdvID() {
        return lkAdvID;
    }

    public void setLkAdvID(LkAdvID lkAdvID) {
        this.lkAdvID = lkAdvID;
    }
}
