fun main(args: Array<String>) {
    var x = generate(63)
    while(!checkDivisibilityWithPrimitives(x)){
        x = generate(63)
    }
}