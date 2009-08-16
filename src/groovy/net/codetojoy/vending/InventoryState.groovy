
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

public class InventoryState {
    static final String NAME = 'N'
    static final String PRICE = 'P'
    static final String COUNT = 'C'
    
    def inventory = []
    
    InventoryState(def inventory) {
        this.inventory = inventory
    }

    void getItem(String name) {
        def item = findItemByName(name)
        assert item != null
        
        int newCount = getCount(name) - 1     
        item[COUNT] = "$newCount"
    }
    
    // TODO: may not need moneyState as return type
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
        
    // this is incredibly inefficient!
    String toString() {
        String s = "[ "
        
        // build name list to sort
        
        def list = []
        
        for (item in inventory) {
            list << item[NAME]
        }
                
        list.sort()
        def noCommaIndex = list.size() - 1 
        def count = 0
        
        for (name in list) {
            println "name = " + name
            def item = findItemByName(name)
            println "item = " + item
            s += "[${NAME}:'${item[NAME]}', ${PRICE}:'${item[PRICE]}', ${COUNT}:'${item[COUNT]}']"
            if (count != noCommaIndex) s += ", "
            count++
        }
        
        s += " ]"
                
        return s
    }
    
    protected findItemByName(def name) {
        def result = null
        
        for (item in inventory) {
            assert item.keySet().contains(NAME)
            
            if (item[NAME] == name) {
                result = item
                break
            }
        }       
        
        return result
    }
}