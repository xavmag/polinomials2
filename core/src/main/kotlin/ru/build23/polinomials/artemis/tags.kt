package ru.build23.polinomials.artemis

import com.artemis.Entity
import com.artemis.managers.TagManager

operator fun TagManager.get(tag: String): Int = getEntityId(tag)