
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending.action

import net.codetojoy.vending.*

class VerifyAction {	
	def expected = null
	
	VerifyAction(String expected) { 
		this.expected = expected.trim() 
	}
	
	def apply(MachineState machineState) {
		def actual = machineState.toString()
		
		if (actual != expected) {
			println "EXPECTED = .${expected}."
			println "ACTUAL .${actual}."			
		}
		
		assert expected == actual
		println "verification OK"
	}
}