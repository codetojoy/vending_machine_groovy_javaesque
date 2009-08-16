
// Project: Vending Machine in Groovy
// Author: Michael Easter
//
// http://codetojoy.blogspot.com

package net.codetojoy.vending

class MoneyStateTestCase extends GroovyTestCase {
    def numNickels = 50     // $2.50
    def numDimes = 75       // $7.50
    def numQuarters = 80    // $20
    def numDollars = 7      // $7
    
    def moneyState = new MoneyState(numDollars, numQuarters, numDimes, numNickels)
    
    def normalizedState = new MoneyState(2,2,1,1)
    
    def zeroState = MoneyState.ZERO
    
    void testGetCost_Easy() {
        // pre
        def inserted = new MoneyState(1,1,1,1)
        def price = new MoneyState(0,0,1,1)
        // test
        def cost = inserted.getCost(price)
        // post
        def expected = new MoneyState(0,0,1,1)
        assert expected == cost
    }

    void testGetCost_Complex() {
        // pre
        def inserted = new MoneyState(0,0,0,50)
        def price = new MoneyState(1,1,1,1)
        // test
        def cost = inserted.getCost(price)
        // post
        def expected = new MoneyState(0,0,0,28)
        assert expected == cost
    }
        
    void testNumTimes_zeroN() {
        assert 0 == moneyState.numTimes(100, 0, 10)
    }

    void testNumTimes_normal() {
        assert 3 == moneyState.numTimes(121, 3, 25)
    }

    void testNumTimes_limitN() {
        assert 3 == moneyState.numTimes(121, 3, 5)
    }

    void testNumTimes_NLarge() {
        assert 12 == moneyState.numTimes(121, 30, 10)
    }
    
    void testFactor_NoCoins() {
        def factor = moneyState.factor(100,0,10) 
        assert 0 == factor.times
        assert 100 == factor.remainingPrice
    }

    void testFactor_Normal() {
        def factor = moneyState.factor(160,7,25) 
        assert 6 == factor.times
        assert 10 == factor.remainingPrice
    }

    void testFactor_CoinShortage() {
        def factor = moneyState.factor(100,4,10)
        assert 4 == factor.times
        assert 60 == factor.remainingPrice
    }

    void testFactor_CoinOverage() {
        def factor = moneyState.factor(10,40,5) 
        assert 2 == factor.times
        assert 0 == factor.remainingPrice
    }
    
    void testIsLessOrEqual_Less() {
        assert true == zeroState.isLessOrEqual(normalizedState)
    }

    void testIsLessOrEqual_Equal() {
        assert true == normalizedState.isLessOrEqual(normalizedState)
    }
    
    void testIsLessOrEqual_Greater() {
        assert false == normalizedState.isLessOrEqual(zeroState)
    }
    
    void testDivAndRemainder_Basic() {
        def result = moneyState.divAndRemainder(1250, 100)
        assert 12 == result.div
        assert 50 == result.remainder
    }

    void testDivAndRemainder_NoDiv() {
        def result = moneyState.divAndRemainder(50, 100)
        assert 0 == result.div
        assert 50 == result.remainder
    }

    void testDivAndRemainder_NoRemainder() {
        def result = moneyState.divAndRemainder(500, 100)
        assert 5 == result.div
        assert 0 == result.remainder
    }
    
    void testIntCtor_Nickels() {
        int total = 5
        def result = new MoneyState(total)
        assert 1 == result.numNickels
        assert 0 == result.numDimes 
        assert 0 == result.numQuarters
        assert 0 == result.numDollars
    }

    void testIntCtor_Nickels_Dimes() {
        int total = 15
        def result = new MoneyState(total)
        assert 1 == result.numNickels
        assert 1 == result.numDimes 
        assert 0 == result.numQuarters 
        assert 0 == result.numDollars
    }

    void testIntCtor_Nickels_Dimes_Quarters() {
        int total = 90
        def result = new MoneyState(total)
        assert 1 == result.numNickels
        assert 1 == result.numDimes 
        assert 3 == result.numQuarters 
        assert 0 == result.numDollars
    }
    
    void testIntCtor_Nickels_Dimes_Quarters_Dollars() {
        int total = 420
        def result = new MoneyState(total)
        assert 0 == result.numNickels
        assert 2 == result.numDimes 
        assert 0 == result.numQuarters 
        assert 4 == result.numDollars
    }    

    void testIntCtor_Nickels_Dimes_Quarters_Dollars_Case2() {
        int total = 445
        def result = new MoneyState(total)
        assert 0 == result.numNickels
        assert 2 == result.numDimes 
        assert 1 == result.numQuarters 
        assert 4 == result.numDollars
    }    
    
    void testEquals_Zero() {
        def moneyState2 = new MoneyState(numDollars, numQuarters, numDimes, numNickels)
        assert moneyState2.equals(moneyState)
    }

    void testAdd_Zero() {
        def result = normalizedState.add(zeroState)
        assert result.equals(normalizedState)
    }
    
    void testTotal_Zero() {
        assert 0 == zeroState.total
    }
    
    void testTotal_NonZero() {
        assert 3700 == moneyState.total
    }
}
