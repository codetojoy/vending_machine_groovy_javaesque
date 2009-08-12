
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
                def msg1 = ">>>>>>> ERROR! at line ${lineCount}. type ${t.class} msg ${t.message}"
                def msg2 = ">>>>>>> ERROR! for line ${input}"
				println msg1
				println msg2
				def runLog = new File("error.log").newWriter()
				runLog << msg1
				runLog << msg2
				runLog.close()
				System.exit(-1)
            }
        }
    }
    
    lineCount++
}

println "done"