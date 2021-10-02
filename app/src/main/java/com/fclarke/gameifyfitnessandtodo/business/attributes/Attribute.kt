package com.fclarke.gameifyfitnessandtodo.business.attributes

import com.fclarke.gameifyfitnessandtodo.local.Shared

open class Attribute(shared: Shared, prefKey: String) {
    private val _shared = shared
    private val _prefKey: String = prefKey
    var quality: Int;

    init {
        quality = read()
    }

    fun earnings(earned: Int) {

        var stuff: Int? = _shared?.getInt(_prefKey)
        if (stuff != null) {
            stuff += earned
            _shared?.put(_prefKey, stuff)
        }
    }

    fun read(): Int {
        return _shared.getInt(_prefKey) ?: 0
    }
}