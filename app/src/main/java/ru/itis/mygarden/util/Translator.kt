package ru.itis.mygarden.util

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Translator(private val scope: CoroutineScope) {
    private lateinit var englishRussianTranslator: Translator

    init {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()

        englishRussianTranslator = Translation.getClient(options)
    }

    fun translate(text: String, callback: (String) -> Unit) {
        scope.launch {
            try {
                val translatedText = translateText(text)
                callback(translatedText)
            } catch (e: Exception) {
                e.printStackTrace()
                callback("Translation failed")
            }
        }
    }

    private suspend fun downloadModelIfNeeded() {
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Unit> { continuation ->
                englishRussianTranslator.downloadModelIfNeeded()
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { e ->
                        continuation.resumeWithException(e)
                    }
            }
        }
    }

    private suspend fun translateText(inputText: String): String {
        downloadModelIfNeeded()
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<String> { continuation ->
                englishRussianTranslator.translate(inputText)
                    .addOnSuccessListener { translatedText ->
                        continuation.resume(translatedText)
                    }
                    .addOnFailureListener { e ->
                        continuation.resumeWithException(e)
                    }
            }
        }
    }
}
