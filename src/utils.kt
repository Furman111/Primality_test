import java.math.BigInteger
import java.util.*

const val TESTS_COUNT = 5

val RANDOM = Random()

private val primitives = listOf<Long>(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
        73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
        179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
        283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
        419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541,
        547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
        661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809,
        811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941,
        947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069,
        1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223,
        1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373,
        1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511,
        1523, 1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637, 1657,
        1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811,
        1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987, 1993, 1997, 1999)

/**
 * Функция проверки делимости на простые числа до 2000
 *
 * @return true - не делится, false - делится
 */
val BigInteger.dividedByPrimitives: Boolean
    get() {
        var res = false
        println("Проверка на делимость на простые числа:")
        primitives.forEach {
            val primitiveBigInt = BigInteger.valueOf(it)
            if (compareTo(primitiveBigInt) != 0 && mod(primitiveBigInt) == BigInteger.ZERO) {
                println("* $this делится на простое число $it")
                res = true
            }
        }
        println("Проверка ${if (!res) "пройдена" else "не пройдена"}\n")
        return res
    }

/**
 * Функция генерирования p-битового числа
 **/
fun generate(p: Int): BigInteger = BigInteger(p, RANDOM).setBit(p - 1).setBit(0).apply {
    println("Сгенерированное число $this\n")
}

fun testMillerRabin(x: BigInteger, testCount: Int = TESTS_COUNT): Boolean {

    println("Тест Рабина-Миллера числа $x")

    if (x.mod(BigInteger.valueOf(2)) == BigInteger.ZERO) throw IllegalArgumentException("Arg x mustn't divide by 2")

    val two = BigInteger.valueOf(2)

    var s = BigInteger.ONE
    var t = x.minus(BigInteger.ONE).div(two)

    while (t.mod(two) == BigInteger.ZERO) {
        s = s.inc()
        t = t.div(two)
    }

    println("s = $s, t = $t : ${x.minus(BigInteger.ONE)}= 2^$s * $t\n")

    var result = true

    for (i in 0 until testCount) {

        println("Тест ${i+1}:\n")


        val a = x.minus(BigInteger(x.bitLength() - 1, RANDOM))
        println("a = $a")


        val nod = a.gcd(x)
        if (nod != BigInteger.ONE) {
            println("НОД(a = $a, x = $x) = $nod - числа не взаимно простые, тест не пройден!\n")
            result = false
            continue
        }

        var success = false

        val tt = a.modPow(t, x)
        if (tt.compareTo(BigInteger.ONE) == 0 || tt.compareTo(x.minus(BigInteger.ONE)) == 0) {
            println("abs($a^$t mod $x) = 1 : Тест пройден!")
            success = true
        }


        var step = t.multiply(two)
        while (step != two.pow(s.toInt()).multiply(t)) {
            if (a.modPow(step, x) == x.minus(BigInteger.ONE)) {
                println("$a^$step mod $x = -1 : Тест пройден!")
                success = true
                break
            }
            step = step.multiply(two)
        }

        if (!success) {
            println("Тест не пройден!\n")
            result = false
        }

    }

    return result

}

