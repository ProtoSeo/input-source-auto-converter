package com.protoseo.inputsourceautoconverter.utils

import java.util.Locale
import com.intellij.openapi.diagnostic.Logger
import com.protoseo.inputsourceautoconverter.common.InputSource
import com.protoseo.inputsourceautoconverter.utils.NativeProgramUtils.loadFileFromResources

object InputSourceUtils {

    private val inputSourceFinderPath: String by lazy { loadFileFromResources("/native/InputSourceFinder") }
    private val inputSourceSelectorPath: String by lazy { loadFileFromResources("/native/InputSourceSelector") }
    private val logger: Logger = Logger.getInstance(InputSourceUtils::class.java)
    private lateinit var cachedInputSources: Map<String, InputSource>

    init {
        initCachedInputSources()
    }

    fun getInputSources(): Array<InputSource> {
        if (!this::cachedInputSources.isInitialized) {
            initCachedInputSources()
        }
        return this.cachedInputSources.values.toTypedArray()
    }

    fun getDefaultInputSource(): InputSource {
        for (inputSource in cachedInputSources.values) {
            if (inputSource.language == Locale.ENGLISH.language) {
                return inputSource
            }
        }
        return this.getInputSources()[0]
    }

    fun convertInputSource(inputSource: InputSource) {
        convertInputSourceById(inputSource.id)
    }

    private fun convertInputSourceById(id: String) {
        runProcess(inputSourceSelectorPath, id)
    }

    private fun initCachedInputSources() {
        val (stdout, _, _) = runProcess(inputSourceFinderPath)
        cachedInputSources = stdout.split('\n')
            .filter { it.isNotBlank() }
            .associate {
                val t = it.split(":").toTypedArray()
                t[0] to InputSource(t[0], t[1], t[2])
            }
    }

    private fun runProcess(command: String, vararg arguments: String): CommandResult {
        val process = ProcessBuilder(command, *arguments).start()
        val stdout = process.inputStream.bufferedReader().readText()
        val stderr = process.errorStream.bufferedReader().readText()
        val exitCode = process.waitFor()

        if (stderr.isNotBlank()) {
            logger.error("$stderr, exitCode = $exitCode")
        }

        return CommandResult(stdout, stderr, exitCode)
    }
}

data class CommandResult(
    val stdout: String,
    val stderr: String,
    val exitCode: Int
)
