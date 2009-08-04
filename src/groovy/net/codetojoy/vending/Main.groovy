
package net.codetojoy.vending

def applyActions(def actions, def machineState) {
    for( action in actions ) {
        action.apply(machineState)
    }
}

/////////////////////////////////////////

def parser = new Parser()
def machineState = new MachineState()

def file = new File("${args[0]}")
def lineCount = 1

file.eachLine { line ->
    def input = line.trim()
    def commentRegEx = "^//.*"
    
    if (input.size() > 0) {
        def m = input =~ commentRegEx
        
        if (! m.matches()) {
            try {
                def actions = parser.parse(input)
                applyActions(actions, machineState)                 
            } catch(Throwable t) {
                println ">>>>>>> ERROR! for line ${input[0..8]}"
                println ">>>>>>> ERROR! at line ${lineCount}. start = ${line[0..8]}. type ${t.class} msg ${t.message}"
            }
        }
    }
    
    lineCount++
}

println "done"