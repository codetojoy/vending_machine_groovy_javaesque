
package net.codetojoy.vending

enum Input { NICKEL, DIME, QUARTER, DOLLAR, COIN_RETURN, SERVICE, GET }

enum Output { NICKEL, DIME, QUARTER, VEND }

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
		
		if (avail) {
			def price = inventoryState.getPrice(itemName)
			def sufficientFunds = price.isLessOrEqual(insertedMoney)
			
			if (sufficientFunds) {
				inventoryState.getItem(itemName)
				this.insertedMoney = insertedMoney.subtract(price)
				this.availableChange = availableChange.add(price)
				// TODO: return change, normalized
				result = true
			}
		}
		
		return result
	}
		
	String toString() {
		def s = availableChange.toString() + " " + insertedMoney.toString() + " " + inventoryState.toString()

		return s
	}
}

