//Declaracion de Paquete
package com.dany.calculadora1
//Importacion de librerias (clases, metodos y funciones) necesarias
import android.os.Bundle
//importacion de elementos para la UI
import android.view.View
//permite el uso de botones
import android.widget.Button
//permite mostrar texto a los usuarios
import android.widget.TextView
//permite el uso de componentes modernos
import androidx.appcompat.app.AppCompatActivity
//permite formatear numeros decimales
import java.text.DecimalFormat

//Bloque de Construccion
class MainActivity : AppCompatActivity() {
    //Declaracion de variables globales Constantes
    private val suma = "+"
    private val resta = "-"
    private val multiplicacion = "*"
    private val division = "/"
    private val porcentaje = "%"

    //Declaracion de Variables globales Variables
    //Inicializa la variable con una cadena Vacia
    private var operacionActual = ""
    //Indica que la variable es de tipo Double y se inicializa con un valor NaN
    private var primerNumero: Double = Double.NaN
    //Indica que la variable es de tipo Double y se inicializa con un valor NaN
    private var segundoNumero: Double = Double.NaN

    //Variables que se iniciaran mas tarde
    private lateinit var tvTemp: TextView
    private lateinit var tvResult: TextView
    private lateinit var formatoDecimal: DecimalFormat

    //Sobreescribe el metodo onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        //Llama al metodo onCreate de la clase padre
        super.onCreate(savedInstanceState)
        //Establece el diseño de la UI
        setContentView(R.layout.activity_main)
        //Inicializa las variables mas tarde, con formato decimal a usar en la calculadora
        formatoDecimal = DecimalFormat("#.##########")
        //Inicializa las variables mas tarde, con los elementos de la UI a usar en la calculadora
        tvTemp = findViewById(R.id.tvTemp)
        //Inicializa las variables mas tarde, con los elementos de la UI a usar en la calculadora
        tvResult = findViewById(R.id.tvResult)
        //Inicializa la variable con el valor de la cadena cero
        tvTemp.text = ""
        //Enlace del boton igual
        val btnIgual = findViewById<Button>(R.id.button16)
        btnIgual.setOnClickListener {
            igual(it)
        }
        //Enlace del boton .
        val btnPunto = findViewById<Button>(R.id.button15)
        btnPunto.setOnClickListener {
            numeroPresionado(it)
        }
    }

    //Declaración de funcion para los botones de operaciones
    fun cambiarOperador(b: View) {
        //Comprueba si el tvTemp tiene texto || si el primerNumero no es NaN
        if (tvTemp.text.isNotEmpty() || primerNumero.toString() != "NaN") {
            //Llamado Funcion calcular
            calcular()
            //Convertir b en un boton
            val boton: Button = b as Button
            //Comprobacion del boton de opereacion presionado
            if (boton.text.toString().trim() == "÷") {
                //Si el texto es ÷ se asigna la cadena / a la variable operacionActual
                operacionActual = division
                //Si el texto es X se asigna la cadena * a la variable operacionActual
            } else if (boton.text.toString().trim() == "X") {
                operacionActual = multiplicacion
                //Si el texto es % se asigna la cadena % a la variable operacionActual
            } else if (boton.text.toString().trim() == "%") {
                operacionActual = porcentaje
                //Si el texto es - se asigna la cadena - a la variable operacionActual
            } else if (boton.text.toString().trim() == "-") {
                operacionActual = resta
                //Si el texto es + se asigna la cadena + a la variable operacionActual
            } else if (boton.text.toString().trim() == "+") {
                operacionActual = suma
                //Asigna texto del boton presionado a la variable operacionActual
            } else {
                operacionActual = boton.text.toString().trim()
            }
            //Comprueba si el tvTemp esta vacio
            if (tvTemp.text.toString().isEmpty()) {
                tvTemp.text = tvTemp.text.toString()
            }
            //Muestra el resultado de la operacion
            tvResult.text = formatoDecimal.format(primerNumero) + operacionActual
            tvTemp.text = ""
        }
    }
    //Funcion para calcular
    private fun calcular() {
        //Manejo de excepciones
        try {
            //Comprueba si el primerNumero no es NaN convirtiendolo en cadena
            if (primerNumero.toString() != "NaN") {
                //if anidado que comprueba si el tvTemp esta vacio
                if (tvTemp.text.toString().isEmpty()) {
                    tvTemp.text = tvResult.text.toString()
                }
                //Convierte el texto del tvTemp en cadena y lo convierte en un numero decimal
                segundoNumero = tvTemp.text.toString().toDouble()
                //Limpia el tvTemp
                tvTemp.text = ""
                //Ejecuta la operacion correspondiente
                when (operacionActual) {
                    "+" -> primerNumero = (primerNumero + segundoNumero)
                    "-" -> primerNumero = (primerNumero - segundoNumero)
                    "*" -> primerNumero = (primerNumero * segundoNumero)
                    "/" -> primerNumero = (primerNumero / segundoNumero)
                    "%" -> primerNumero = (primerNumero * (segundoNumero / 100))
                }
            } else {
                //Guarda el valor del tvTemp en la variable primerNumero
                primerNumero = tvTemp.text.toString().toDouble()
            }
            //Manejo de excepciones
        } catch (e: Exception) {
            //Muestra un mensaje de error en el tvTemp
            tvTemp.text = "Error"
        }
    }
    //Captura de numero presionado
        fun numeroPresionado(b: View) {
            //Convierte la vista 'b' en un botón
            val boton: Button = b as Button
            //Concatenar el texto del botón presionado al texto actual del TextView
            tvTemp.text = tvTemp.text.toString() + boton.text.toString()
        }

        private fun igual(b: View) {
            //Llamado Funcion calcular
            calcular()
            //Muestra el resultado de la operacion
            tvResult.text = formatoDecimal.format(primerNumero)
            //Reinicia la variable operacionActual con una cadena vacía
            operacionActual = ""
        }

        //Funcion para limpiar
        fun limpiar(b: View) {
            // Convierte la vista 'b' en un botón
            val boton = b as Button
            // Comprueba si el texto del boton es C
            if (boton.text.toString().trim() == "C") {
                // Comprueba si el tvTemp no esta vacio
                if (tvTemp.text.toString().isNotEmpty()) {
                    //obtenemos los datos del tvTemp
                    val datosActuales: CharSequence = tvTemp.text as CharSequence
                    //Eliminamos el ultimo caracter
                    tvTemp.text = datosActuales.subSequence(0, datosActuales.length - 1)
                } else {
                    // si esta vacio se limpian los valores y los tv
                    primerNumero = Double.NaN
                    segundoNumero = Double.NaN
                    tvTemp.text = ""
                    tvResult.text = ""
                }
                // si el texto del boton es CA
            } else if (boton.text.toString().trim() == "CA") {
                //se limpian los valores y los tv
                primerNumero = Double.NaN
                segundoNumero = Double.NaN
                tvTemp.text = ""
                tvResult.text = ""
            }
    }
}
