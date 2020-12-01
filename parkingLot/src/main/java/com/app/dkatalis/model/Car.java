package com.app.dkatalis.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * @author Teguh Santoso
 */
public class Car extends Vehicle implements Serializable {
    private static final long serialVersionUID = 6805088514868784528L;

    public Car(String registrationNo)
    {
        super(registrationNo);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
}
