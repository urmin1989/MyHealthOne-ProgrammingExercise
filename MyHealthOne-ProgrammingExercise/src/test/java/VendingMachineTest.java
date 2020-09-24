import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.myhealthone.programming.exercise.SodaVendingMachineFactory;
import org.myhealthone.programming.exercise.exception.NotSufficientChangeException;
import org.myhealthone.programming.exercise.exception.SoldOutException;
import org.myhealthone.programming.exercise.models.Bucket;
import org.myhealthone.programming.exercise.models.Coin;
import org.myhealthone.programming.exercise.models.Item;
import org.myhealthone.programming.exercise.service.SodaVendingMachine;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VendingMachineTest {
    private static SodaVendingMachine vm;

    @BeforeClass
    public static void setUp() {
        vm = SodaVendingMachineFactory.createVendingMachine();
    }

    @AfterClass
    public static void tearDown() {
        vm = null;
    }
    @Test
    public void testBuyItemWithExactPrice() {
        //select item, price in cents
        long price = vm.selectItemAndGetPrice(Item.COKE);
        //price should be Coke's price
        assertEquals(Item.COKE.getPrice(), price);
        //25 cents paid
        vm.insertCoin(Coin.QUARTER);

        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();

        //should be Coke
        assertEquals(Item.COKE, item);
        //there should not be any change
        assertTrue(change.isEmpty());
    }

    @Test
    public void testBuyItemWithMorePrice(){
        long price = vm.selectItemAndGetPrice(Item.SODA);
        assertEquals(Item.SODA.getPrice(), price);

        vm.insertCoin(Coin.QUARTER);
        vm.insertCoin(Coin.QUARTER);

        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();

        //should be Coke
        assertEquals(Item.SODA, item);
        //there should not be any change
        assertTrue(!change.isEmpty());
        //comparing change
        assertEquals(50 - Item.SODA.getPrice(), getTotal(change));

    }


    @Test
    public void testRefund() {
        long price = vm.selectItemAndGetPrice(Item.PEPSI);
        assertEquals(Item.PEPSI.getPrice(), price);
        vm.insertCoin(Coin.DIME);
        vm.insertCoin(Coin.NICKLE);
        vm.insertCoin(Coin.PENNY);
        vm.insertCoin(Coin.QUARTER);
        assertEquals(41, getTotal(vm.refund()));
    }


    @Test(expected = SoldOutException.class)
    public void testSoldOut() {
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.COKE);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
    }


    @Test(expected = NotSufficientChangeException.class)
    public void testNotSufficientChangeException() {
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.SODA);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
            vm.selectItemAndGetPrice(Item.PEPSI);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
    }

    @Test(expected = SoldOutException.class)
    public void testReset() {
        SodaVendingMachine vmachine = SodaVendingMachineFactory.createVendingMachine();
        vmachine.reset();
        vmachine.selectItemAndGetPrice(Item.COKE);
    }

    private long getTotal(List<Coin> change) {
        long total = 0;
        for (Coin c : change) {
            total = total + c.getDenomination();
        }
        return total;
    }

}
