import java.math.BigInteger

const val BITS_NUMBER = 512

fun main(args: Array<String>) {
    var x = generate(BITS_NUMBER)

    var isPrimitive = testMillerRabin(x)

    while (!isPrimitive) {
        x = x.add(BigInteger.valueOf(2))

        isPrimitive = testMillerRabin(x)
    }

}

