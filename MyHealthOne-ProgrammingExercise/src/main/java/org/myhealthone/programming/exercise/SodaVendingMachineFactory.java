package org.myhealthone.programming.exercise;

import org.myhealthone.programming.exercise.service.Impl.SodaVendingMachineImpl;
import org.myhealthone.programming.exercise.service.SodaVendingMachine;

public class SodaVendingMachineFactory {
    public static SodaVendingMachine createVendingMachine() {
        return new SodaVendingMachineImpl();
    }
}
