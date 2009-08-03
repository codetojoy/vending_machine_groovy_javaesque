
package net.codetojoy.vending.action

import net.codetojoy.vending.*

class Actions {

    // accessors

	def getCoinAction() { return coinAction }

	def getCoinReturnAction() { return coinReturnAction }
	
	def getGetAction() { return getAction }
	
    // definitions

	def getAction = { String item, MachineState machineState ->
		def result = machineState.getItem(item)	
	}

	def coinReturnAction = { MachineState machineState -> 
		machineState.insertedMoney = MoneyState.ZERO
	}
	
	def coinAction = { MoneyState moneyState, MachineState machineState -> 
		machineState.addInsertedMoney(moneyState)
	}
	
}