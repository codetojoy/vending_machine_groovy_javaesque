
package net.codetojoy.vending

// TODO: goes away?
public class Item {
    int count
    int price
    String name
    
    String toString() {
        return " ${name} with # ${count} "
    }
}
