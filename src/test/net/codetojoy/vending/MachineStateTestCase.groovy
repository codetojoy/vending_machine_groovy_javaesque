
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

class MachineStateTestCase extends GroovyTestCase {
    def machineState = new MachineState()
        
    public void setUp() {
        def items = []
        items << [N:'A', P:'150', C:'10'] 
        items << [N:'B', P:'65', C:'1'] 
        items << [N:'C', P:'100', C:'1']
        def inventoryState = new InventoryState(items) 
        machineState.inventoryState = inventoryState
    }
    
    void testGetItem_NotThere() {
        def result = machineState.getItem('Z')
        assert false == result
    }

    void testGetItem_InsufficientFunds() {
        def result = machineState.getItem('A')
        assert false == result
    }

    void testGetItem_NormalTransaction() {
        // test
        machineState.insertedMoney = new MoneyState(1,0,5,3)
        def result = machineState.getItem('A')
        // post
        assert true == result
        assert new MoneyState(0,0,0,3) == machineState.insertedMoney
        assert new MoneyState(1,0,5,0) == machineState.availableChange
        assert 9 == machineState.inventoryState.getCount('A')
    }
    
    void testAddInsertedMoney_Nickel() {
        machineState.addInsertedMoney(MoneyState.NICKEL)
        assertTrue machineState.insertedMoney.equals(MoneyState.NICKEL)
    }

    void testAddInsertedMoney_Dime() {
        machineState.addInsertedMoney(MoneyState.DIME)
        assertTrue machineState.insertedMoney.equals(MoneyState.DIME)
    }

    void testAddInsertedMoney_Quarter() {
        machineState.addInsertedMoney(MoneyState.QUARTER)
        assertTrue machineState.insertedMoney.equals(MoneyState.QUARTER)
    }

    void testAddInsertedMoney_Dollar() {
        machineState.addInsertedMoney(MoneyState.DOLLAR)
        assertTrue machineState.insertedMoney.equals(MoneyState.DOLLAR)
    }
    
    void testExecTransaction() {
        // pre
        machineState.insertedMoney = new MoneyState(2,2,2,2)
        machineState.availableChange = new MoneyState(10,10,10,10)
        def price = new MoneyState(150)
        
        // test
        machineState.execTransaction(price)
        
        // post
        def expectedInserted = new MoneyState(1,0,2,2)
        def expectedAvailable = new MoneyState(11,12,10,10)
        assert expectedInserted == machineState.insertedMoney
        assert expectedAvailable == machineState.availableChange
    }
    
    void testExecTransaction_case2() {
        // pre
        machineState.insertedMoney = new MoneyState(5,0,100,0)
        machineState.availableChange = new MoneyState(10,10,10,10)
        def price = new MoneyState(90)
        
        // test
        machineState.execTransaction(price)
        
        // post
        def expectedInserted = new MoneyState(5,0,91,0)
        def expectedAvailable = new MoneyState(10,10,19,10)
        assert expectedInserted == machineState.insertedMoney
        assert expectedAvailable == machineState.availableChange
    }

    void testExecTransaction_case3() {
        // pre
        machineState.insertedMoney = new MoneyState(1,1,1,1)
        machineState.availableChange = new MoneyState(0,0,0,0)
        def price = new MoneyState(140)
        
        // test
        machineState.execTransaction(price)
        
        // post
        def expectedInserted = new MoneyState(0,0,0,0)
        def expectedAvailable = new MoneyState(1,1,1,1)
        assert expectedInserted == machineState.insertedMoney
        assert expectedAvailable == machineState.availableChange
    }    
}
