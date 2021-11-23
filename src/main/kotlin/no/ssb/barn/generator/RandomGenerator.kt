package no.ssb.barn.generator

class RandomGenerator {
    companion object {
        @JvmStatic
        fun generateRandomString(stringLength: Int) : String{
            val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return (1..stringLength)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
        }

        @JvmStatic
        fun generateRandomInt(): Int {
            return (1..100000).random()
        }

    }
}