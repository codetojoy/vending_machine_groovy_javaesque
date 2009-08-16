
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

class MachineState {
    InventoryState inventoryState = new InventoryState()
    MoneyState availableChange = MoneyState.ZERO
    MoneyState insertedMoney = MoneyState.ZERO
        
     void addInsertedMoney(MoneyState change) {
         insertedMoney = insertedMoney.add(change)
     }
    
     boolean getItem(String itemName) {
        boolean result = false
        
        def avail = inventoryState.isItemAvailable(itemName)
        
        if (avail == ItemRequestState.IN_STOCK) {
            def price = inventoryState.getPrice(itemName)
            def sufficientFunds = price.isLessOrEqual(insertedMoney)
            
            if (sufficientFunds) {
                execTransaction(price)
                inventoryState.getItem(itemName)
                result = true
            }
        }
        
        return result
    }

    protected void execTransaction(def price) {
        def cost = insertedMoney.getCost(price)
        this.insertedMoney = insertedMoney.subtract(cost)
        this.availableChange = availableChange.add(cost)        
    }
    
    String toString() {
        return "${availableChange.toString()} ${insertedMoney.toString()} ${inventoryState.toString()}"
    }
}

