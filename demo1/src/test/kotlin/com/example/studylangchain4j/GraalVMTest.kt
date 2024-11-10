package com.example.studylangchain4j

import dev.langchain4j.code.CodeExecutionEngine
import dev.langchain4j.code.graalvm.GraalVmJavaScriptExecutionEngine
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test


@SpringBootTest
class GraalVMTest {
    @Test
    fun test() {
        val engine: CodeExecutionEngine = GraalVmJavaScriptExecutionEngine()

        val code = """
        function fibonacci(n) {
            if (n <= 1) return n;
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
                        
        fibonacci(10)
        """.trimIndent()
        val result = engine.execute(code)
        println(result)
    }
}
