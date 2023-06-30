package com.tutorial.kotlin_playground.lazyinit

class HelloBot {
    val greeting: String by lazy { getHello() }
    fun sayHello() = println(greeting)
}

fun getHello() = "안녕하세요";

fun main() {
    val a = LateInitSample()

    println(a.getLateInitText()) // 출력 결과 : 기본값
    a.text = "새로 할당한 값"
    println(a.getLateInitText())
}

class LateInitSample {

    lateinit var text: String

    fun getLateInitText() : String {
        if(::text.isInitialized) {
            return text
        }
        return "test"
    }

}
