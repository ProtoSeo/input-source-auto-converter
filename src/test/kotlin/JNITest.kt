import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy
import java.util.concurrent.TimeUnit
import org.junit.Test
import com.protoseo.inputsourceautoconverter.converter.MacNative

class JNITest {

    @Test
    fun `JNI 잘 동작하는지 테스트`() {
        println(MacNative.findSelectableInputSources())

        MacNative.convert("com.apple.inputmethod.Korean.2SetKorean")
        Thread.sleep(500)
        MacNative.convert("com.apple.keylayout.ABC")
        Thread.sleep(500)
        MacNative.convert("com.apple.inputmethod.Korean.2SetKorean")
    }

    @Test
    fun `다른 Thread 에서 실행해도 동작되는지 테스트`() {
        val executor = ThreadPoolExecutor(
            1, 1, Long.MAX_VALUE, TimeUnit.DAYS, ArrayBlockingQueue(10),
            { r ->
                val thread = Thread(r, "ideavim_extension")
                thread.isDaemon = true
                thread.priority = Thread.MAX_PRIORITY
                thread
            },
            DiscardPolicy()
        )

        executor.execute { MacNative.convert("com.apple.inputmethod.Korean.2SetKorean") }
    }
}
