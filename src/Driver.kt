//src/Driver.kt
import kotlin.math.cos
import kotlin.math.sin

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





