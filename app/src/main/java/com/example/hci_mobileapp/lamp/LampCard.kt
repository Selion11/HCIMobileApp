package com.example.hci_mobileapp.lamp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.graphics.Shader
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.core.graphics.toRect
import com.example.hci_mobileapp.ui.theme.ComposeColorPickerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.toHexString

@Composable
fun LampCard(
    lampViewModel: LampViewModel = viewModel()
) {
    val lampUiState = lampViewModel.uiState.collectAsState()

    val currentColor = remember { lampViewModel.currentColor() }

    val intensityDialog = remember { mutableStateOf(false) }

    val colorPickerDialog = remember { mutableStateOf(false) }
/*
    val hsv = remember {
        val color = currentColor?.let { Color(it.toULong()) }
        val hsv = floatArrayOf(0f, 0f, 0f)
        if (color != null) {
            android.graphics.Color.RGBToHSV(
                color.red.toInt(),
                color.green.toInt(),
                color.blue.toInt(),
                hsv
            )
        }

        mutableStateOf(
            Triple(hsv[0], hsv[1], hsv[2])
        )
    }*/

/*    val backgroundColor = remember(hsv.value) {
        mutableStateOf(
            Color.hsv(
                hsv.value.first,
                hsv.value.second,
                hsv.value.third
            )
        )
    }*/

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(95.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 3.dp,
                    vertical = 4.dp
                ),
        ) {
            Row {
                lampUiState.value.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                    )
                }
            }
            Row {
                IconButton(onClick = { lampViewModel.turnOnOff() }) {
                    Icon(
                        painter = painterResource(lampViewModel.iconSelection()),
                        contentDescription = stringResource(R.string.lamp),
                        modifier = Modifier.size(45.dp),

                        )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 45.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .height(55.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                TextButton(
                    onClick = { colorPickerDialog.value = true },
                    enabled = lampUiState.value.state == R.string.On,
                    modifier = Modifier.padding(start = 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(lampUiState.value.actions.colChange))
                    Icon(
                        painter = painterResource(lampUiState.value.icons.colorPicker),
                        contentDescription = null
                    )
                }
                TextButton(
                    onClick = { intensityDialog.value = true },
                    enabled = lampUiState.value.state == R.string.On,
                    modifier = Modifier.padding(start = 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(lampUiState.value.actions.inten))
                    Icon(
                        painter = painterResource(lampUiState.value.icons.intense),
                        contentDescription = null,
                    )
                }
            }
        }
    }

    if (intensityDialog.value) {
        Dialog(onDismissRequest = { intensityDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(100.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Text(
                    text = lampUiState.value.intensity.toString(),
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 15.dp)
                ) {
                    IconButton(onClick = {
                        lampViewModel.setIntensity(lampUiState.value.intensity?.minus(1) ?: 0 )
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null
                        )
                    }
                    lampUiState.value.intensity?.let {
                        Slider(
                            value = it.toFloat(),
                            onValueChange = { lampViewModel.setIntensity(it.toInt()) },
                            valueRange = 0f..100f,
                            modifier = Modifier.width(240.dp)
                        )
                    }
                    IconButton(onClick = {
                        lampViewModel.setIntensity(lampUiState.value.intensity?.plus(1) ?: 0)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            intensityDialog.value = false
                        },
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
                }
            }
        }
    }

/*    if (colorPickerDialog.value) {
        Dialog(onDismissRequest = { intensityDialog.value = false }) {
            val newColor = remember {
                mutableStateOf(" ")
            } //lampViewModel.currentColor()
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        horizontal = 30.dp,
                        vertical = 30.dp
                    ),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                ComposeColorPickerTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        SatValPanel(hue = hsv.value.first) { sat, value ->
                            hsv.value = Triple(hsv.value.first, sat, value)
                            newColor.value = android.graphics.Color
                                .HSVToColor(
                                    floatArrayOf(
                                        hsv.value.first,
                                        hsv.value.second,
                                        hsv.value.third
                                    )
                                )
                                .toHexString()

                        }
                        Spacer(modifier = Modifier.height(32.dp))

                        HueBar { hue ->
                            hsv.value = Triple(hue, hsv.value.second, hsv.value.third)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(backgroundColor.value)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            colorPickerDialog.value = false
                            lampViewModel.colorSet(newColor.value)
                        },
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
                }
            }
        }
    }*/
}



@Composable
fun HueBar(
    setColor: (Float) -> Unit
) {
    val scope = rememberCoroutineScope()
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val pressOffset = remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(
        modifier = Modifier
            .height(40.dp)
            .width(300.dp)
            .clip(RoundedCornerShape(50))
            .emitDragGesture(interactionSource)
    ) {
        val drawScopeSize = size
        val bitmap = Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
        val hueCanvas = Canvas(bitmap)

        val huePanel = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())

        val hueColors = IntArray((huePanel.width()).toInt())
        var hue = 0f
        for (i in hueColors.indices) {
            hueColors[i] = android.graphics.Color.HSVToColor(floatArrayOf(hue, 1f, 1f))
            hue += 360f / hueColors.size
        }

        val linePaint = Paint()
        linePaint.strokeWidth = 0F
        for (i in hueColors.indices) {
            linePaint.color = hueColors[i]
            hueCanvas.drawLine(i.toFloat(), 0F, i.toFloat(), huePanel.bottom, linePaint)
        }

        drawBitmap(
            bitmap = bitmap,
            panel = huePanel
        )

        fun pointToHue(pointX: Float): Float {
            val width = huePanel.width()
            val x = when {
                pointX < huePanel.left -> 0F
                pointX > huePanel.right -> width
                else -> pointX - huePanel.left
            }
            return x * 360f / width
        }


        scope.collectForPress(interactionSource) { pressPosition ->
            val pressPos = pressPosition.x.coerceIn(0f..drawScopeSize.width)
            pressOffset.value = Offset(pressPos, 0f)
            val selectedHue = pointToHue(pressPos)
            setColor(selectedHue)
        }


        drawCircle(
            Color.White,
            radius = size.height/2,
            center = Offset(pressOffset.value.x, size.height/2),
            style = Stroke(
                width = 2.dp.toPx()
            )
        )

    }
}

fun CoroutineScope.collectForPress(
    interactionSource: InteractionSource,
    setOffset: (Offset) -> Unit
) {
    launch {
        interactionSource.interactions.collect { interaction ->
            (interaction as? PressInteraction.Press)
                ?.pressPosition
                ?.let(setOffset)
        }
    }
}



private fun Modifier.emitDragGesture(
    interactionSource: MutableInteractionSource
): Modifier = composed {
    val scope = rememberCoroutineScope()

    pointerInput(Unit) {
        detectDragGestures { input, _ ->
            scope.launch {
                interactionSource.emit(PressInteraction.Press(input.position))
            }
        }
    }.clickable(interactionSource, null) {

    }
}

private fun DrawScope.drawBitmap(
    bitmap: Bitmap,
    panel: RectF
) {
    drawIntoCanvas {
        it.nativeCanvas.drawBitmap(
            bitmap,
            null,
            panel.toRect(),
            null
        )
    }
}

@Composable
fun SatValPanel(
    hue: Float,
    setSatVal: (Float, Float) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val scope = rememberCoroutineScope()
    var sat: Float
    var value: Float

    val pressOffset = remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(
        modifier = Modifier
            .size(300.dp)
            .emitDragGesture(interactionSource)
            .clip(RoundedCornerShape(12.dp))
    ) {
        val cornerRadius = 12.dp.toPx()
        val satValSize = size

        val bitmap = Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val satValPanel = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())

        val rgb = android.graphics.Color.HSVToColor(floatArrayOf(hue, 1f, 1f))

        val satShader =  LinearGradient(
            satValPanel.left, satValPanel.top, satValPanel.right, satValPanel.top,
            -0x1, rgb, Shader.TileMode.CLAMP
        )
        val valShader = LinearGradient(
            satValPanel.left, satValPanel.top, satValPanel.left, satValPanel.bottom,
            -0x1, -0x1000000, Shader.TileMode.CLAMP
        )

        canvas.drawRoundRect(
            satValPanel,
            cornerRadius,
            cornerRadius,
            Paint().apply {
                shader = ComposeShader(
                    valShader,
                    satShader,
                    PorterDuff.Mode.MULTIPLY
                )
            }
        )

        drawBitmap(
            bitmap = bitmap,
            panel = satValPanel
        )


        fun pointToSatVal(pointX: Float, pointY: Float): Pair<Float, Float> {
            val width = satValPanel.width()
            val height = satValPanel.height()

            val x = when {
                pointX < satValPanel.left -> 0f
                pointX > satValPanel.right -> width
                else -> pointX - satValPanel.left
            }

            val y = when {
                pointY < satValPanel.top -> 0f
                pointY > satValPanel.bottom -> height
                else -> pointY - satValPanel.top
            }

            val satPoint = 1f / width * x
            val valuePoint = 1f - 1f / height * y

            return satPoint to valuePoint
        }

        scope.collectForPress(interactionSource) { pressPosition ->
            val pressPositionOffset = Offset(
                pressPosition.x.coerceIn(0f..satValSize.width),
                pressPosition.y.coerceIn(0f..satValSize.height)
            )


            pressOffset.value = pressPositionOffset
            val (satPoint, valuePoint) = pointToSatVal(pressPositionOffset.x, pressPositionOffset.y)
            sat = satPoint
            value = valuePoint

            setSatVal(sat, value)
        }

        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = pressOffset.value,
            style = Stroke(
                width = 2.dp.toPx()
            )
        )

        drawCircle(
            color = Color.White,
            radius = 2.dp.toPx(),
            center = pressOffset.value,
        )
    }
}

// Color Picker Source Code: https://github.com/V-Abhilash-1999/ComposeColorPicker/blob/main/app/src/main/java/com/abhilash/apps/composecolorpicker/ui/theme/Theme.kt

/*
@Preview
@Composable
fun prev(){
    LampCard(name = "juenaso")
}
*/
