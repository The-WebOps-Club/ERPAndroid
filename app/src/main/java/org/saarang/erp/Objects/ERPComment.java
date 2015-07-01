package org.saarang.erp.Objects;

/**
 * Created by Ahammad on 28/06/15.
 */
public class ERPComment {
    String info;
    String _id;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public ERPUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ERPUser createdBy) {
        this.createdBy = createdBy;
    }

    String userId;
    String createdOn;
    ERPUser createdBy;
}
