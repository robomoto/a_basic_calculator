package org.robomoto.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import org.robomoto.calculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot: Boolean = false

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View){
        binding.tvInput.append((view as Button).text)
        Toast.makeText(this, "Button Works", Toast.LENGTH_SHORT).show()
        lastNumeric = true
    }

    fun onClear(view: View){
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false

        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    binding.tvInput.text = truncZeroes((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    binding.tvInput.text = truncZeroes((one.toDouble() + two.toDouble()).toString())
                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    binding.tvInput.text = truncZeroes((one.toDouble() * two.toDouble()).toString())
                } else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    binding.tvInput.text = truncZeroes((one.toDouble() / two.toDouble()).toString())
                } else {
                    binding.tvInput.text = truncZeroes(binding.tvInput.text.toString())
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    private fun truncZeroes(result: String) : String {
        var value = result
        if(result.endsWith(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}