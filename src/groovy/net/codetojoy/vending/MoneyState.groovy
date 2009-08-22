
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

class MoneyState {
    
    static final MoneyState ZERO = new MoneyState( [0,0,0,0] )
    static final MoneyState NICKEL = new MoneyState( [0,0,0,1] )
    static final MoneyState DIME = new MoneyState( [0,0,1,0] )
    static final MoneyState QUARTER = new MoneyState( [0,1,0,0] )
    static final MoneyState DOLLAR = new MoneyState( [1,0,0,0] )
    
    final int numNickels
    final int numDimes
    final int numQuarters
    final int numDollars
    
    // assume incoming values are legal
    MoneyState(numDollars, numQuarters, numDimes, numNickels) {
        this.numDollars = numDollars
        this.numQuarters = numQuarters
        this.numDimes = numDimes
        this.numNickels = numNickels
    }
    
    MoneyState(def coinList) {
        assert 4 == coinList.size()
        this.numDollars = coinList[0]
        this.numQuarters = coinList[1]
        this.numDimes = coinList[2]
        this.numNickels = coinList[3]
    }    
    
    boolean equals(MoneyState rhs) {
        return (this.numNickels == rhs.numNickels) && (this.numDimes == rhs.numDimes)
               (this.numQuarters == rhs.numQuarters) && (this.numDollars == rhs.numDollars)
    }
    
    boolean isLessOrEqual(MoneyState rhs) {
        return this.getTotal() <= rhs.getTotal()
    }
    
    // returns this + rhs, not normalized
    MoneyState add(MoneyState rhs) {
        int numNickels = this.numNickels + rhs.numNickels
        int numDimes = this.numDimes + rhs.numDimes
        int numQuarters = this.numQuarters + rhs.numQuarters
        int numDollars = this.numDollars + rhs.numDollars
        def result = new MoneyState(numDollars, numQuarters, numDimes, numNickels)
        return result
    }

    // returns this - rhs, not normalized
    MoneyState subtract(MoneyState rhs) {
        int numNickels = this.numNickels - rhs.numNickels
        int numDimes = this.numDimes - rhs.numDimes
        int numQuarters = this.numQuarters - rhs.numQuarters
        int numDollars = this.numDollars - rhs.numDollars
        def result = new MoneyState(numDollars, numQuarters, numDimes, numNickels)
        return result
    }
            
    // returns this - price, accounting for coin resources offered by 'this'
    def getCost(MoneyState price) {
    
        def exp = factor(price.total, numDollars, 100)
        def dollars = exp.times
        exp = factor(exp.remainingPrice, numQuarters, 25)
        def quarters = exp.times
        exp = factor(exp.remainingPrice, numDimes, 10)
        def dimes = exp.times
        exp = factor(exp.remainingPrice, numNickels, 5)
        def nickels = exp.times
        
        def cost = new MoneyState(dollars, quarters, dimes, nickels)
        return cost
    }
    
    String toString() {
        return "[${numDollars}, ${numQuarters}, ${numDimes}, ${numNickels}]"
    }
    
    // ------------------------------------------------------------------

    protected def factor = { def price, numCoins, amount ->
        def expando  = new Expando()
        expando.times = 0
        expando.remainingPrice = price

        if (price > 0 && numCoins > 0) {
            def naturalDiv = ((price / amount) as int)
            expando.times = Math.min(naturalDiv, numCoins)
            expando.remainingPrice = (price - (expando.times * amount))
        } 

        return expando
    }

    // assume total is in cents
    protected MoneyState(int total) {
        def dollarResult = divAndRemainder(total, 100)
        this.numDollars = dollarResult.div
        int remainder = dollarResult.remainder
        
        def quarterResult = divAndRemainder(remainder, 25)
        this.numQuarters = quarterResult.div
        remainder = quarterResult.remainder
        
        def dimeResult = divAndRemainder(remainder, 10)
        this.numDimes = dimeResult.div
        remainder = dimeResult.remainder
        
        def nickelResult = divAndRemainder(remainder, 5)
        this.numNickels = nickelResult.div
        // drop pennies
    }    
    
    protected int getTotal() {
        int result = numNickels * 5
        result += numDimes * 10
        result += numQuarters * 25
        result += numDollars * 100
    }

    protected def divAndRemainder(int total, int divisor) {
        def result = new Expando()
        result.div = ((total / divisor) as int)
        result.remainder = total % divisor
        return result
    }    
}
