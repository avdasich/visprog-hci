import kotlin.random.Random
import kotlin.math.cos
import kotlin.math.sin

// Класс Human
class Human(
    var fullName: String,
    var age: Int,
    var currentSpeed: Double
) {
    // Координаты в 2D пространстве
    var x: Double = 0.0
    var y: Double = 0.0

    // Метод перемещения с использованием Random Walk
    fun move() {
        // Случайный угол направления в радианах
        val angle = Random.nextDouble(0.0, 2 * Math.PI)
        // Обновляем координаты
        x += currentSpeed * cos(angle)
        y += currentSpeed * sin(angle)
    }

    // Геттер координат
    fun getPosition(): Pair<Double, Double> = Pair(x, y)
}

// Основная функция
fun main() {
    val numberOfHumans = 5 // Например, номер в списке = 5
    val simulationTimeSeconds = 10 // Время симуляции в секундах

    // Создаем массив Human
    val humans = Array(numberOfHumans) { i ->
        Human("Human${i + 1}", 20 + i, 1.0 + i * 0.5)
    }

    // Основной цикл "времени"
    for (t in 1..simulationTimeSeconds) {
        println("Time: $t sec")
        for ((index, human) in humans.withIndex()) {
            human.move()
            val (x, y) = human.getPosition()
            println("${human.fullName} moved to position (x=${"%.2f".format(x)}, y=${"%.2f".format(y)})")
        }
        println()
        Thread.sleep(1000) // Пауза 1 секунда для имитации времени
    }

    println("Simulation finished!")
}
