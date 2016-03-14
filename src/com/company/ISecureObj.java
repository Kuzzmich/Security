package com.company;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by kuzzm on 05.03.2016.
 */
public interface ISecureObj extends Serializable{
    void create();
    void delete();
    String fullName();
    void saveState() throws IOException;
}