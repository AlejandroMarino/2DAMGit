package org.example.ui.common

import org.example.ui.controllers.PrincipalController

open class BaseScreenController {
    private var principalController: PrincipalController? = null

    fun getPrincipalController(): PrincipalController? {
        return principalController
    }

    fun setPrincipalController(principalController: PrincipalController?) {
        this.principalController = principalController
    }

    open fun principalCargado() {

    }
}
