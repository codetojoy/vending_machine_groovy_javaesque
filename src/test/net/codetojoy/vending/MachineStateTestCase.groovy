
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

class MachineStateTestCase extends GroovyTestCase {
	def machineState = new MachineState()
	
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
