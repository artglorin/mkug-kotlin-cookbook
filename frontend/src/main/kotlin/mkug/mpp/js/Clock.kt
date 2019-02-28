package mkug.mpp.js

import kotlin.browser.document

fun getCanvasContext(canvasId: String): dynamic = document.getElementById(canvasId).asDynamic().getContext("2d")

external fun clock_conti(size: Int, canvasContext: dynamic, options: ClockOptions )
external fun clock_digital(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_norm(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_follow(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_circles(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_grow(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_dots(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_num(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_random(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_digitalran(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_bars(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_planets(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_roulette(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_reverse(size: Int, canvasContext: dynamic, options: ClockOptions)
external fun clock_binary(size: Int, canvasContext: dynamic, options: ClockOptions)

class ClockOptions(
    val indicate: Boolean = true,
    val indicate_color: String = "#FF5722",
    var dial1_color: String = "#198acc",
    var dial2_color: String = "#558631",
    var dial3_color: String = "#673ab7",
    var time_add: Int = 0,
    var time_add_color: String = "#558631",
    var time_24h: Boolean = true,
    var timeoffset: Int = 0,
    var date_add: Int = 0,
    var date_add_color: String? = "#0608278c",
    var bg_color: String? = "#c6eeff8c",
    var bg_opacity: Double = 0.1
)