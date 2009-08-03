
package net.codetojoy.vending.action

import net.codetojoy.vending.*

class CoinAction {
	MoneyState moneyState 
	
	CoinAction(MoneyState moneyState) { 
		this.moneyState = moneyState 
	}
	
	def apply(MachineState machineState) {
		machineState.addInsertedMoney(moneyState)
/*		println "coinAction state = " + machineState.toString()
*/	}
}