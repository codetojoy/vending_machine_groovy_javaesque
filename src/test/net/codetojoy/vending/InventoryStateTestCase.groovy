
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

class InventoryStateTestCase extends GroovyTestCase {
	
	def inventoryState = null
	
	public void setUp() {
		def items = []
		items << [N:'A', P:'100', C:'10'] 
		items << [N:'B', P:'400', C:'1'] 
		items << [N:'C', P:'100', C:'0']
		inventoryState = new InventoryState(items) 
	}

	public void testGetItem() {
		// pre
		assert ItemState.IN_STOCK == inventoryState.isItemAvailable('B')
		// test
		inventoryState.getItem('B')
		// post
		assert ItemState.OUT_OF_STOCK == inventoryState.isItemAvailable('B')
	}
	
	public void testGetPrice() {
		def price = inventoryState.getPrice('A')
		def expectedState = new MoneyState(100)
		assert expectedState == price 
	}
	
	public void testFindItemByName_Yes() {
		def result = inventoryState.findItemByName('A')
		assert null != result
	}
	
	public void testFindItemByName_No() {
		def result = inventoryState.findItemByName('M')
		assert null == result
	}
	
	public void testIsItemAvailable_InStock() {
		assert ItemState.IN_STOCK == inventoryState.isItemAvailable('A')
	}
	
	public void testIsItemAvailable_OutOfStock() {
		assert ItemState.OUT_OF_STOCK == inventoryState.isItemAvailable('C')
	}
	
	public void testIsItemAvailable_Unknown() {
		assert ItemState.UNKNOWN == inventoryState.isItemAvailable('X')
	}	
}