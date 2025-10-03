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





