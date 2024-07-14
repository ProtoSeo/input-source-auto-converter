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
}
