package org.saarang.erp.Objects;

/**
 * Created by Ahammad on 30/06/15.
 */
public class ERPWall {

    String _id, name, parentId;

    public String getId() {
        return _id;
    }

    public ERPWall(String id, String name) {
        this._id = id;
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
