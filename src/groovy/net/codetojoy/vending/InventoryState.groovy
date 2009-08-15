
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
    
        def count = Integer.parseInt(item[COUNT])
        def newCount = count - 1     
        item[COUNT] = "${newCount}"
    }
    
    MoneyState getPrice(String name) {
        def priceStr = findItemByName(name).get(PRICE)
        def price = Integer.parseInt( priceStr )
        def moneyState = new MoneyState(price)
        return moneyState
    }

    boolean isItemAvailable(String name) {
        boolean result = false

        def item = findItemByName(name)
        def count = Integer.parseInt(item[COUNT])
        
        if (count > 0) result = true
        
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
        
        println "size FINAL " + s.length()
        
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
        assert result != null
        
        return result
    }
}