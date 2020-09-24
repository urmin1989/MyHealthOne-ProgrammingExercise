package org.myhealthone.programming.exercise.service;

import org.myhealthone.programming.exercise.models.Bucket;
import org.myhealthone.programming.exercise.models.Coin;
import org.myhealthone.programming.exercise.models.Item;

import java.util.List;

public interface SodaVendingMachine {
    public long selectItemAndGetPrice(Item item);
    public void insertCoin(Coin coin);
    public List<Coin> refund();
    public Bucket<Item, List<Coin>> collectItemAndChange();
    public void reset();

}
