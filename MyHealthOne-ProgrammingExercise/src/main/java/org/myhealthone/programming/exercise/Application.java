package org.myhealthone.programming.exercise;

import org.myhealthone.programming.exercise.models.Bucket;
import org.myhealthone.programming.exercise.models.Coin;
import org.myhealthone.programming.exercise.models.Item;
import org.myhealthone.programming.exercise.service.SodaVendingMachine;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        SodaVendingMachine vm =SodaVendingMachineFactory.createVendingMachine();
        //select item, price in cents
        long price = vm.selectItemAndGetPrice(Item.COKE);

        //25 cents paid
        vm.insertCoin(Coin.QUARTER);

        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();
        System.out.println("Item is "+item+"  change is "+ change);
    }
}
