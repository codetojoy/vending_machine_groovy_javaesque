
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending.action

import net.codetojoy.vending.*

class CoinReturnAction {	
	def apply(MachineState machineState) {
		machineState.insertedMoney = MoneyState.ZERO
	}
}