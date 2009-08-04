
package net.codetojoy.vending.action

import net.codetojoy.vending.*

class ServiceAction {
	def availableChange = null
	def inventoryState = null

	ServiceAction(MoneyState moneyState, InventoryState inventory) { 
		this.availableChange = moneyState 
		this.inventoryState = inventory
	}
		
	def apply(def machineState) {
		println "Cp 1"
		machineState.availableChange = availableChange
		println "Cp 2"
		machineState.inventoryState = inventoryState
		println "Cp 3"
	}
}