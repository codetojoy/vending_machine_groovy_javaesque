
package net.codetojoy.vending.action

import net.codetojoy.vending.*

class GetAction {
	def item = null
	
	GetAction(String item) { this.item = item }
		
	def apply(MachineState machineState) {
		def result = machineState.getItem(item, machineState)
		assert true == result
	}
}