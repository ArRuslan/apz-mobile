package com.rdev.nure.apz.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rdev.nure.apz.ui.theme.ApzTheme
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

private val dateFmt: DateFormat = SimpleDateFormat.getDateInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastCarousel(vararg temps : Int?, onLongClick: (() -> Unit)? = null) {
    val carouselState = rememberCarouselState { temps.size }
    var size by remember { mutableStateOf(0.dp) }

    Column {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .onSizeChanged { size = it.width.dp }
                .pointerInput(Unit){
                    detectTapGestures(
                        onLongPress = {
                            if(onLongClick != null) onLongClick()
                        }
                    )
                }
        ) {
            HorizontalUncontainedCarousel(
                state = carouselState,
                itemWidth = size,
                flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(carouselState)
            ) { index ->
                val forecastTemp = temps[index]?.toString() ?: "?"

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DATE, index)

                    Text(
                        text = "$forecastTemp °C",
                        fontSize = 28.sp,
                    )
                    Text(
                        text = dateFmt.format(calendar.time),
                        fontSize = 20.sp,
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherForecastCarouselPreview() {
    ApzTheme {
        WeatherForecastCarousel(15, 20)
    }
}