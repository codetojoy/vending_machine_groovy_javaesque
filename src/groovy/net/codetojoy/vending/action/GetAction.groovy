
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending.action

import net.codetojoy.vending.*

class GetAction {
	def item = null
	
	GetAction(String item) { this.item = item }
		
	def apply(MachineState machineState) {
		println "GET ACTION CP"
		def result = machineState.getItem(item)
	}
}