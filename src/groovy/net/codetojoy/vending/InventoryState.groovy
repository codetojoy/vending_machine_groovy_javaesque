
package net.codetojoy.vending

public class InventoryState {
	static final String NAME = 'N'
	static final String PRICE = 'P'
	static final String COUNT = 'C'
	
	def inventory = []

	InventoryState() {}

    InventoryState(String inventoryStr) {
		def shell = new GroovyShell(new Binding())
		inventory = shell.evaluate(inventoryStr)
	}
	
    void getItem(String name) {
		def item = findItemByName(name)
		assert item != null
	
		def count = Integer.parseInt(item[COUNT])
		def newCount = count - 1
		println "WTH " + newCount
		item[COUNT] = "${newCount}"
	}
	
    MoneyState getPrice(String name) {
		def priceStr = findItemByName(name).get(PRICE)
		def price = Integer.parseInt( priceStr )
		def moneyState = new MoneyState(price)
		return moneyState
    }

 	// returns false if non-existent or if quantity = 0
	boolean isItemAvailable(String name) {
		boolean result = false

		def item = findItemByName(name)

		println "checkpoint. item = " + item
		
		if (item != null) {
			def count = Integer.parseInt(item[COUNT])
			if (count > 0) result = true			
		}
		
		println "result IS " + result
		
		return result
	}
	
	// this is not efficient!
	String toString() {
		String s = "[ "
		
		// build nameList to sort and nameMap with output strings
		
		def nameList = []
		def nameMap = [:]
		
		for (item in inventory) {
			def name = item[NAME]
			nameList << name
			def itemStr = "[${NAME}:'${item[NAME]}', ${PRICE}:'${item[PRICE]}', ${COUNT}:'${item[COUNT]}']"
			nameMap[name] = itemStr
		}
		
		nameList.sort()

		def noCommaIndex = nameList.size() - 1 
		def count = 0
		
		for (name in nameList) {
			def itemStr = nameMap[name]
			s += itemStr
			if (count != noCommaIndex) s += ", "
			count++
		}

		s += " ]"
		
		return s
	}
	
	// returns null if not found
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