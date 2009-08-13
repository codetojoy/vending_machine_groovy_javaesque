
package net.codetojoy.vending

class MoneyStateTestCase extends GroovyTestCase {
	def numNickels = 50     // $2.50
	def numDimes = 75       // $7.50
	def numQuarters = 80    // $20
	def numDollars = 7      // $7
	
	def moneyState = new MoneyState(numDollars, numQuarters, numDimes, numNickels)
	
	def normalizedState = new MoneyState(2,2,1,1)
	
	def zeroState = MoneyState.ZERO
	
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
		assert 12 == result['div']
		assert 50 == result['remainder']
	}

	void testDivAndRemainder_NoDiv() {
		def result = moneyState.divAndRemainder(50, 100)
		assert 0 == result['div']
		assert 50 == result['remainder']
	}

	void testDivAndRemainder_NoRemainder() {
		def result = moneyState.divAndRemainder(500, 100)
		assert 5 == result['div']
		assert 0 == result['remainder']
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
	
	void testSubtract_Illegal() {
		try {
			zeroState.subtract(moneyState)
			fail("expected assertion error")
		} catch(Throwable ex) {
			// happy path
		}
	}
	
	void testSubtract_Zero() {
		def result = zeroState.subtract(zeroState)
		assert result.equals(zeroState)
	}
	
	void testSubtract_Basic() {
		def result = normalizedState.subtract(zeroState)
		assert result.equals(normalizedState)
	}
	
	void testSubtract_Basic2() {
		def x = new MoneyState(0,0,0,1)
		def expected = new MoneyState(2,2,1,0)
		def result = normalizedState.subtract(x)
		assert result.equals(expected)
	}
	
	void testSubtract_Basic3() {
		def x = new MoneyState(0,0,1,1)
		def expected = new MoneyState(2,2,0,0)
		def result = normalizedState.subtract(x)
		assert result.equals(expected)
	}

	void testSubtract_Basic4() {
		def x = new MoneyState(0,1,1,1)
		def expected = new MoneyState(2,1,0,0)
		def result = normalizedState.subtract(x)
		assert result.equals(expected)
	}

	void testSubtract_Basic5() {
		def x = new MoneyState(1,1,0,0)
		def expected = new MoneyState(1,1,1,1)
		def result = normalizedState.subtract(x)
		assert result.equals(expected)
	}

	void testSubtract_Basic6() {
		def x = new MoneyState(2,2,0,0)
		def expected = new MoneyState(0,0,1,1)
		def result = normalizedState.subtract(x)
		assert result.equals(expected)
	}
}
