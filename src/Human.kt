//src/Human.kt
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI
import kotlin.random.Random

open class Human(
    var fullName: String,
    var age: Int,
    override var speed: Double
) : Movable, Runnable {

    override var x: Double = 0.0
    override var y: Double = 0.0

    @Volatile
    var running = true

   override fun move() {
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
