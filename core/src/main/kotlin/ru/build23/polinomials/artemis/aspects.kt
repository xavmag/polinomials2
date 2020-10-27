package ru.build23.polinomials.artemis

import com.artemis.Aspect
import com.artemis.Component
import kotlin.reflect.KClass

fun Aspect.Builder.all(vararg types: KClass<out Component>) = all(types.map { it.java })

fun Aspect.Builder.exclude(vararg types: KClass<out Component>) = exclude(types.map { it.java })

fun Aspect.Builder.one(vararg types: KClass<out Component>) = one(types.map { it.java })

fun all(vararg types: KClass<out Component>) = Aspect.all(types.map { it.java })

fun exclude(vararg types: KClass<out Component>) = Aspect.exclude(types.map { it.java })

fun one(vararg types: KClass<out Component>) = Aspect.one(types.map { it.java })