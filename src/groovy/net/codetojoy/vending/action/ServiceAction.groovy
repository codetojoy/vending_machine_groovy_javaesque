
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

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
		machineState.availableChange = availableChange
		machineState.inventoryState = inventoryState
	}
}