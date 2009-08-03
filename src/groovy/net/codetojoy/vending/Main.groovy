
package net.codetojoy.vending

// init state

def parser = new Parser()
def machineState = new MachineState()
def lineCount = 1

// closures

def applyActions = { actions ->
	for( action in actions ) {
		//action.apply(machineState)
		if (action instanceof Closure) {
			action(machineState)			
		} else {
			action.apply(machineState)			
		}
	}
}

def processLine = { line ->
	def input = line.trim()
	def commentRegEx = "^//.*"
	
	if (input.size() > 0) {
		def isComment = input =~ commentRegEx
		
		if (! isComment.matches()) {
			try {
				def actions = parser.parse(input)
				applyActions(actions)					
			} catch(Throwable t) {
				println ">>>>>>> ERROR! for line ${input[0..8]}"
				println ">>>>>>> ERROR! at line ${lineCount}. start = ${line[0..8]}. type ${t.class} msg ${t.message}"
			}
		}
	}
	
	lineCount++
}
	
/////////////////////////////////////////
// MAIN

def file = new File("${args[0]}")

file.eachLine( processLine )

println "done"