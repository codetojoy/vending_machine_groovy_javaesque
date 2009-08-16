
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending 

import net.codetojoy.vending.action.* 

class Parser {
    def shell = new GroovyShell(new Binding())
    def actionMap = [:]
    
    public Parser() {
        actionMap['N'] = new CoinAction(MoneyState.NICKEL)
        actionMap['D'] = new CoinAction(MoneyState.DIME)
        actionMap['Q'] = new CoinAction(MoneyState.QUARTER)
        actionMap['\$'] = new CoinAction(MoneyState.DOLLAR)
        actionMap['COIN_RETURN'] = new CoinReturnAction()
    }
    
    // returns a list of actions
    def parse(String line) {
        def actions = []
        
        def inputs = line.split(" , ")
        
        inputs.each { input -> 
            actions << parseOneInput(input)
        }
            
        return actions
    }
    
    def parseOneInput(String s) {
        def action = null
        
        def input = s.toUpperCase().trim()
        
        def actionRegEx = /^(.*)\s?.*/
        def m = input =~ actionRegEx
        
        if (m.matches()) {
            def actionVerb = m[0][1]
            
            if (actionMap.keySet().contains(actionVerb)) {
                action = actionMap.get(actionVerb)
            } else {
                action = compoundParse(input)
            }
        }
        
        return action
    }

    def parseVerify(String s) {
        def action = null
        
        def verifyRegEx = /^VERIFY\s*(.*)\s*$/
        def m = s =~ verifyRegEx

        if (m.matches()) {
            def expected = m[0][1]
            action = new VerifyAction(expected)
        }
        
        return action
    }
    
    def parseService(String s) {
        def action = null
        
        def serviceRegEx = /^SERVICE\s*\[(.*)\]\s*\[(.*)\]\s*$/
        def m = s =~ serviceRegEx

        if (m.matches()) {
            def coinListStr = "[${m[0][1]}]"
            def coinList = shell.evaluate(coinListStr)
            def moneyState = new MoneyState(coinList)
            
            def inventoryStr = "[${m[0][2]}]"
            def inventoryMap = shell.evaluate(inventoryStr)
            def inventory = new InventoryState(inventoryMap)
            action = new ServiceAction(moneyState, inventory)
        }
        
        return action
    }
    
    def parseGet(String s) {
        def action = null
        
        def getRegEx = /^GET\s*(.*)$/
        def m = s =~ getRegEx

        if (m.matches()) {
            def item = m[0][1]
            action = new GetAction(item)
        }
        
        return action                        
    }
    
    def compoundParse(String s) {
        def action = parseService(s)
    
        if (action == null) action = parseGet(s)
        if (action == null) action = parseVerify(s)
                    
        return action        
    }
}
