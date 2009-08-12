
package net.codetojoy.vending

def applyActions(def actions, def machineState) {
    for( action in actions ) {
        action.apply(machineState)
    }
}

def isVerbose(def args) {
	def result = false
	
	if (args.length >= 2 && args[1] == "verbose") { result = true }
	
	return result
}

/////////////////////////////////////////

def parser = new Parser()
def machineState = new MachineState()

def file = new File("${args[0]}")
def verboseMode = isVerbose(args)
def lineCount = 1
def commentRegEx = "^//.*"

file.eachLine { line ->
    def input = line.trim()
    
    if (input.size() > 0) {
        def m = input =~ commentRegEx
        
        if (! m.matches()) {
	        if (verboseMode) { println "$lineCount: $line" }
	
            try {
                def actions = parser.parse(input)
                applyActions(actions, machineState)                 
            } catch(Throwable t) {
                println ">>>>>>> ERROR! at line ${lineCount}. type ${t.class} msg ${t.message}"
                println ">>>>>>> ERROR! for line ${input}"
            }
        }
    }
    
    lineCount++
}

println "done"