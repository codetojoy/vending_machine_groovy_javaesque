
package net.codetojoy.vending.action

import net.codetojoy.vending.*

class GetAction {
	def item = null
	
	GetAction(String item) { this.item = item }
		
	def apply(MachineState machineState) {
		println "HELLO from GetAction"
		def result = machineState.getItem(item)
	}
}