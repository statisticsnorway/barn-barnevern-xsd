package no.ssb.barn

object TestUtils {

    fun getResourceAsString(resourceName:String) = this.javaClass.getResource("/$resourceName")!!.readText()
}
