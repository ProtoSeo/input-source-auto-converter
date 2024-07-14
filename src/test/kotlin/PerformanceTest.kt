import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.NANOSECONDS
import org.junit.Test
import com.protoseo.inputsourceautoconverter.converter.MacInputSourceConverter
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

class PerformanceTest {

    @Test
    fun `프로세스를 호출해서 입력 소스를 변경하는 방법의 시간 측정`() {
        val inputSources = InputSourceUtils.getInputSources()

        val executionTime = measureExecutionTime {
            for (i in 0..100) {
                for (inputSource in inputSources) {
                    InputSourceUtils.convertInputSource(inputSource)
                }
            }
        }

        println("${MILLISECONDS.convert(executionTime, NANOSECONDS)} ms")
    }

    @Test
    fun `JNI를 활용해서 입력 소스를 변경하는 방법의 시간 측정`() {
        val inputSources = MacInputSourceConverter.findSelectableInputSources()

        val executionTime = measureExecutionTime {
            for (i in 0..100) {
                for (inputSource in inputSources) {
                    MacInputSourceConverter.convert(inputSource)
                }
            }
        }
        println("${MILLISECONDS.convert(executionTime, NANOSECONDS)} ms")
    }

    private fun measureExecutionTime(method: () -> Unit): Long {
        val startTime = System.nanoTime()
        method()
        return System.nanoTime() - startTime
    }
}
