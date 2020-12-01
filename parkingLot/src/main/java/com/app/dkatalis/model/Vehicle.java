package com.app.dkatalis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor
public class Vehicle implements Serializable, Externalizable {
    private static final long serialVersionUID = -577480775883012289L;

    private String	registrationNo;
    // private String	color;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getRegistrationNo());
        //out.writeObject(getColor());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setRegistrationNo((String) in.readObject());
        //setColor((String) in.readObject());
    }
}
