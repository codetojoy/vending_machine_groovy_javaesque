
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending


public class InventoryState {
    // an item is a map with these keys: 
    // Note: arguably a Groovier way would be to use an Expando
    static final String NAME = 'N'
    static final String PRICE = 'P'
    static final String COUNT = 'C'
    
    def inventory = []
    
    InventoryState(def inventory) {
        this.inventory = inventory
        // normalize output string via sort
        if (this.inventory) { this.inventory.sort { it[NAME] } }
    }

    void getItem(String name) {
        def item = findItemByName(name)
        
        int newCount = getCount(name) - 1     
        item[COUNT] = "$newCount"
    }
    
    MoneyState getPrice(String name) {
        def priceStr = findItemByName(name).get(PRICE)
        def price = Integer.parseInt( priceStr )
        def moneyState = new MoneyState(price)
        return moneyState
    }

    int getCount(String name) {
        def item = findItemByName(name)
        assert item != null
    
        int count = Integer.parseInt(item[COUNT])
        return count
    }
    
    ItemRequestState isItemAvailable(String name) {
        ItemRequestState result = ItemRequestState.UNKNOWN

        def item = findItemByName(name)
        
        if (item != null) {
            result = ItemRequestState.IN_STOCK
            
            def count = Integer.parseInt(item[COUNT])

            if (count == 0) result = ItemRequestState.OUT_OF_STOCK
        }
        
        return result
    }
        
    String toString() {
        // we sorted in the ctor
        
        String s = inventory.inject ("[ ", { value, item ->
            value += "[${NAME}:'${item[NAME]}', ${PRICE}:'${item[PRICE]}', ${COUNT}:'${item[COUNT]}'], "
        })
        
        // snip trailing comma
        def t = s[0 .. s.length() - 3]
        t += " ]"
                
        return t
    }
    
    protected findItemByName(def name) {
        return inventory.find { item -> item[NAME] == name }
    }
}