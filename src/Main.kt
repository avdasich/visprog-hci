import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI
import kotlin.random.Random

class Human(
    var fullName: String,
    var age: Int,
    var speed: Double
) {
    var x: Double = 0.0
    var y: Double = 0.0

    fun move() {
        // случайное направление от 0 до 2π
        val angle = Random.nextDouble(0.0, 2 * PI)
        x += speed * cos(angle)
        y += speed * sin(angle)
    }

    override fun toString(): String {
        return "$fullName (возраст: $age) → координаты: (%.2f, %.2f)".format(x, y)
    }
}

fun main() {
    // число людей = последняя цифра номера группы → 431 → 4 человека
    val humans = arrayOf(
        Human("Иванов Иван", 20, 1.0),
        Human("Петров Петр", 21, 1.2),
        Human("Сидоров Сидор", 22, 0.8),
        Human("Кузнецов Кузьма", 23, 1.5)
    )

    val simulationTime = 10 // секунд (итераций)
    println("Начало симуляции Random Walk на $simulationTime шагов:\n")

    for (t in 1..simulationTime) {
        println("Шаг $t:")
        humans.forEach { human ->
            human.move()
            println(human)
        }
        println()
    }
}

