
package net.codetojoy.vending

import net.codetojoy.vending.action.* 

class ParserTestCase extends GroovyTestCase {
	def parser = new Parser()
	
	void assertCoinAction(MoneyState expected, def action) {
		def machineState = new MachineState()
		action(machineState)
		assert expected == machineState.insertedMoney
	}

	void assertCoinReturnAction(def action) {
		def machineState = new MachineState()
		action(machineState)
		assert MoneyState.ZERO == machineState.insertedMoney
	}	
	
	void assertGetAction(def action) {
		def machineState = new MachineState()
		def inventoryStr = "[ N:'A', P:'65', C:'10'] ]"
		machineState.inventoryState = new InventoryState(inventoryStr)
		action(machineState)
		asssert inventoryStr == machineState.inventoryState 
	}
	
	void testParser_Basic() {
		def input = "n , d , q , \$ , coin_return"
		def actions = parser.parse(input)
		assert 5 == actions.size()
		assertCoinAction(MoneyState.NICKEL, actions[0])
		assertCoinAction(MoneyState.DIME, actions[1])
		assertCoinAction(MoneyState.QUARTER, actions[2])
		assertCoinAction(MoneyState.DOLLAR, actions[3])
		assertCoinReturnAction(actions[4])
	}

	void testParser_Basic2() {
		def input = "n , d , q , \$ , service [3,4,5,6] [] , coin_return"
		def actions = parser.parse(input)
		assert 6 == actions.size()
		assertCoinAction(MoneyState.NICKEL, actions[0])
		assertCoinAction(MoneyState.DIME, actions[1])
		assertCoinAction(MoneyState.QUARTER, actions[2])
		assertCoinAction(MoneyState.DOLLAR, actions[3])
		def expectedState = new MoneyState([3,4,5,6])
		assert expectedState == actions[4].availableChange
		assertCoinReturnAction(actions[5])
	}

	void testParser_Basic3() {
		def input = "n , n , n , n , get 2"
		def actions = parser.parse(input)
		assert 5 == actions.size()
		assertCoinAction(MoneyState.NICKEL, actions[0])
		assertCoinAction(MoneyState.NICKEL, actions[1])
		assertCoinAction(MoneyState.NICKEL, actions[2])
		assertCoinAction(MoneyState.NICKEL, actions[3])
		assert actions[4] instanceof GetAction
		assert "2" == actions[4].item
	}

	void testParser_Basic_Verify() {
		def input = "verify [1,2,3,4] [3,4,5,6]"
		def actions = parser.parse(input)
		assert 1 == actions.size()
		assert actions[0] instanceof VerifyAction
		println "@@@@@@@ " + actions[0].expected
		assert "[1,2,3,4] [3,4,5,6]" == actions[0].expected
	}

	void testParseOneInput_Nickel() {
		def action = parser.parseOneInput("N")
		assertCoinAction(MoneyState.NICKEL, action)
	}

	void testParseOneInput_Dime() {
		def action = parser.parseOneInput("D")
		assertCoinAction(MoneyState.DIME, action)
	}

	void testParseOneInput_Quarter() {
		def action = parser.parseOneInput("Q")
		assertCoinAction(MoneyState.QUARTER, action)
	}
	
	void testParseOneInput_Dollar() {
		def action = parser.parseOneInput("\$")
		assertCoinAction(MoneyState.DOLLAR, action)
	}

	void testParseOneInput_CoinReturn() {
		def action = parser.parseOneInput("coin_return")
		assertCoinReturnAction(action)
	}
	
	void testCompoundParse_Service() {
		def action = parser.compoundParse("SERVICE [1,2,3,4] [ [N:'A', P:'125', C:'99'], [N:'B', P:'33', C:'44'] ] ")
		def expectedState = new MoneyState([1,2,3,4])
		assert expectedState.equals(action.availableChange)
		def expectedInventory = "[ [N:'A', P:'125', C:'99'], [N:'B', P:'33', C:'44'] ]"
		println "------------ " + action.inventoryState.toString() + "----"
		assert expectedInventory == action.inventoryState.toString()
	}
	
	void testCompoundParse_Get() {
		def action = parser.compoundParse("GET 1")
		assert "1" == action.item
	}
	
}