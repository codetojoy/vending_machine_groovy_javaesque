
package net.codetojoy.vending

import net.codetojoy.vending.action.* 

class ParserTestCase extends GroovyTestCase {
	def parser = new Parser()
	
	void testParser_Basic() {
		def input = "n , d , q , \$ , coin_return"
		def actions = parser.parse(input)
		assert 5 == actions.size()
		assert MoneyState.NICKEL == actions[0].moneyState
		assert MoneyState.DIME == actions[1].moneyState
		assert MoneyState.QUARTER == actions[2].moneyState
		assert MoneyState.DOLLAR == actions[3].moneyState
		assert actions[4] instanceof CoinReturnAction
	}

	void testParser_Basic2() {
		def input = "n , d , q , \$ , service [3,4,5,6] [] , coin_return"
		def actions = parser.parse(input)
		assert 6 == actions.size()
		assert MoneyState.NICKEL == actions[0].moneyState
		assert MoneyState.DIME == actions[1].moneyState
		assert MoneyState.QUARTER == actions[2].moneyState
		assert MoneyState.DOLLAR == actions[3].moneyState
		def expectedState = new MoneyState(3,4,5,6)
		assert expectedState == actions[4].availableChange
		assert actions[5] instanceof CoinReturnAction
	}

	void testParser_Basic3() {
		def input = "n , n , n , n , get 2"
		def actions = parser.parse(input)
		assert 5 == actions.size()
		assert MoneyState.NICKEL == actions[0].moneyState
		assert MoneyState.NICKEL == actions[1].moneyState
		assert MoneyState.NICKEL == actions[2].moneyState
		assert MoneyState.NICKEL == actions[3].moneyState
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
		assert MoneyState.NICKEL == action.moneyState
	}

	void testParseOneInput_Dime() {
		def action = parser.parseOneInput("D")
		assert MoneyState.DIME == action.moneyState
	}

	void testParseOneInput_Quarter() {
		def action = parser.parseOneInput("Q")
		assert MoneyState.QUARTER == action.moneyState
	}
	
	void testParseOneInput_Dollar() {
		def action = parser.parseOneInput("\$")
		assert MoneyState.DOLLAR == action.moneyState
	}

	void testParseOneInput_CoinReturn() {
		def action = parser.parseOneInput("coin_return")
		assert action instanceof CoinReturnAction
	}
	
	void testCompoundParse_Service() {
		def action = parser.compoundParse("SERVICE [4,3,2,1] [ [N:'A', P:'125', C:'99'], [N:'B', P:'33', C:'44'] ] ")
		def expectedState = new MoneyState(4,3,2,1)
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