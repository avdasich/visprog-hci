import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI
import kotlin.random.Random

open class Human(
    var fullName: String,
    var age: Int,
    var speed: Double
) : Runnable {

    var x: Double = 0.0
    var y: Double = 0.0

    @Volatile
    var running = true

    open fun move() {
        val angle = Random.nextDouble(0.0, 2 * PI)
        x += speed * cos(angle)
        y += speed * sin(angle)
    }

    override fun run() {
        var step = 1
        while (running) {
            move()
            println("$fullName (шаг $step) → координаты: (%.2f, %.2f)".format(x, y))
            step++
            Thread.sleep(500)
        }
    }

    override fun toString(): String {
        return "$fullName (возраст: $age) → координаты: (%.2f, %.2f)".format(x, y)
    }
}

class Driver(
    fullName: String,
    age: Int,
    speed: Double
) : Human(fullName, age, speed) {

    private val angle = 0.0

    override fun move() {
        x += speed * cos(angle)
        y += speed * sin(angle)
    }
}

fun main() {
    val humans = arrayOf(
        Human("Иванов Иван", 20, 1.0),
        Human("Петров Петр", 21, 1.2),
        Human("Сидоров Сидор", 22, 0.8)
    )
    val driver = Driver("Смирнов Сергей (водитель)", 30, 2.0)

    val all = humans + driver

    val threads = all.map { Thread(it) }

    println("Начало симуляции...\n")
    threads.forEach { it.start() }

    Thread.sleep(5000)

    all.forEach { it.running = false }

    threads.forEach { it.join() }

    println("\nСимуляция завершена.")
}







