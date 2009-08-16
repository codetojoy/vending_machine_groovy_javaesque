
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

def parser = new Parser()
def machineState = new MachineState()

def file = new File("${args[0]}")
def lineCount = 1
def commentRegEx = "^//.*"

file.eachLine { line ->
    def input = line.trim()
    
    if (input.size() > 0) {
        def m = input =~ commentRegEx
        
        if (! m.matches()) {
            try {
                def actions = parser.parse(input)
                actions.each { action -> action.apply(machineState) }             
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