
package net.codetojoy.vending

class MachineStateTestCase extends GroovyTestCase {
	def machineState = new MachineState()
	
	def INVENTORY = "[ [N:'ABC', P:'100', C:'1'], [N:'IJK', P:'135', C:'0'], [N:'XYZ', P:'65', C:'5'] ]"
	
	void setUp() {
		machineState.availableChange = new MoneyState([10, 10, 10, 10])
		machineState.inventoryState = new InventoryState(INVENTORY)
		assert INVENTORY == machineState.inventoryState.toString()
	}
	
	void testGetItem_NonExistant() {
		try {
			machineState.getItem('XXX')		
			fail("expected failed assertion")	
		} catch(Throwable t) {
			// happy path
		}
	}

	void testGetItem_NotAvailable() {
		assertFalse machineState.getItem('IJK')		
	}

	void testGetItem_Exact() {
		machineState.insertedMoney = new MoneyState([0,0,0,1])
		assertTrue machineState.getItem('ABC')		
		assert MoneyState.ZERO == machineState.insertedMoney
		assert "[ [N:'ABC', P:'100', C:'0'], [N:'IJK', P:'135', C:'0'], [N:'XYZ', P:'65', C:'5'] ]" == machineState.inventoryState.toString()
	}

	void testGetItem_WithChange() {
		machineState.insertedMoney = new MoneyState([1,1,0,1])
		assertTrue machineState.getItem('ABC')		
		assert new MoneyState([1,1,0,0]) == machineState.insertedMoney
		assert "[ [N:'ABC', P:'100', C:'0'], [N:'IJK', P:'135', C:'0'], [N:'XYZ', P:'65', C:'5'] ]" == machineState.inventoryState.toString()
	}

	void testGetItem_InsufficientFunds() {
		machineState.insertedMoney = new MoneyState([1,1,0,0])
		assertFalse machineState.getItem('ABC')		
		assert new MoneyState([1,1,0,0]) == machineState.insertedMoney
		assert INVENTORY == machineState.inventoryState.toString()
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
	
}
