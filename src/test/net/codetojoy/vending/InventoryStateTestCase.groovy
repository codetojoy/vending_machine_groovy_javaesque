
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
		assertTrue inventoryState.isItemAvailable('B')
		// test
		inventoryState.getItem('B')
		// post
		assertFalse inventoryState.isItemAvailable('B')
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
		try {
			def result = inventoryState.findItemByName('M')
			fail("expected assertion error")
		} catch(Throwable t) {
			// happy path
		}
	}
	
	public void testIsItemAvailable_Yes() {
		boolean result = inventoryState.isItemAvailable('A')
		assertTrue result
	}
	
	public void testIsItemAvailable_No() {
		boolean result = inventoryState.isItemAvailable('C')
		assertFalse result
	}

	
}