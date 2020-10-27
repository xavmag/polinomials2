package ru.build23.polinomials.artemis

import com.artemis.io.SaveFileFormat
import com.artemis.managers.WorldSerializationManager
import java.io.InputStream

inline fun<reified T: SaveFileFormat> WorldSerializationManager.load(input: InputStream): T? = load(input, T::class.java)